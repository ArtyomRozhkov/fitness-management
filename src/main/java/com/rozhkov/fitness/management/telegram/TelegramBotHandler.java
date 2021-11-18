package com.rozhkov.fitness.management.telegram;

import com.rozhkov.fitness.management.telegram.callback.Callback;
import com.rozhkov.fitness.management.telegram.callback.CallbackData;
import com.rozhkov.fitness.management.telegram.callback.CallbackHandler;
import com.rozhkov.fitness.management.telegram.callback.CallbackHelper;
import com.rozhkov.fitness.management.telegram.message.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

/**
 * Класс-обработчик сообщений от telegram. Входная точка обработки сообщений.
 *
 * Используется long polling стратегия опроса сервера telegram на наличие новых сообщений от пользоватлей.
 * Если сообщения появляются, то на каждое сообщение вызывается метод {@link #onUpdateReceived(Update)}
 */
@RequiredArgsConstructor
@Slf4j
public class TelegramBotHandler extends TelegramLongPollingBot {

    private final String botName;
    private final String botToken;

    private final MessageHandler messageHandler;

    private final CallbackHandler callbackHandler;
    private final CallbackHelper callbackHelper;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    /**
     * Обработка пользовательского сообщения
     *
     * Метод вызывается каждый раз когда пользователи посылают сообщения или нажимают на кастомные кнопки
     * @param update информация о введенном сообщении или нажатой кнопке
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (isMessage(update)) {
            processMessage(update);
        } else if (update.hasCallbackQuery()) {
            processCallback(update);
        }
    }

    private boolean isMessage(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    private void processMessage(Update update) {
        final Message receiveMessage = update.getMessage();
        final SendMessage replyMessage = messageHandler.handleMessage(receiveMessage);
        sendReply(replyMessage);
    }

    private void processCallback(Update update) {
        final Callback callback = createCallback(update.getCallbackQuery());
        final BotApiMethod<?> editedMessage = callbackHandler.handleCallback(callback);
        sendReply(editedMessage);
    }

    private Callback createCallback(CallbackQuery callbackQuery) {
        final Integer messageId = callbackQuery.getMessage().getMessageId();
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        final CallbackData callbackData = callbackHelper.parseCallbackData(callbackQuery.getData());

        return Callback.builder()
                .messageId(messageId)
                .chatId(chatId)
                .user(callbackQuery.getFrom())
                .callbackData(callbackData)
                .build();
    }

    private <T extends Serializable, Method extends BotApiMethod<T>> void sendReply(Method replyMessage) {
        try {
            execute(replyMessage);
        } catch (TelegramApiException e) {
            log.error("Error sending telegram message", e);
        }
    }
}


