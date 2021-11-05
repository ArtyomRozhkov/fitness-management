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
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.Locale;

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

    @Override
    public void onUpdateReceived(Update update) {
        if (isMessage(update)) {

            Message receiveMessage = update.getMessage();
            SendMessage replyMessage = messageHandler.handleMessage(receiveMessage);
            sendReply(replyMessage);

        } else if (update.hasCallbackQuery()) {

            Callback callback = createCallback(update.getCallbackQuery());
            BotApiMethod<?> editedMessage = callbackHandler.handleCallback(callback);
            sendReply(editedMessage);
        }
    }

    private boolean isMessage(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    private Callback createCallback(CallbackQuery callbackQuery) {
        Integer messageId = callbackQuery.getMessage().getMessageId();
        String chatId = callbackQuery.getMessage().getChatId().toString();
        CallbackData callbackData = callbackHelper.parseCallbackData(callbackQuery.getData());

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
