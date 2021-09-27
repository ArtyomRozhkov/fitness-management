package com.rozhkov.fitness.management.message.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class TelegramBotHandler extends TelegramLongPollingBot {

    public static final String ACTION_AND_DATA_SEPARATOR = ":";
    private final String botName;
    private final String botToken;

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            Message receiveMessage = update.getMessage();

            if (isStartCommand(receiveMessage)) {
                SendMessage replyMessage = new SendMessage(); // Create a SendMessage object with mandatory fields
                replyMessage.setChatId(receiveMessage.getChatId().toString());
                replyMessage.setText("Привет " + receiveMessage.getFrom().getFirstName());
                replyMessage.setReplyMarkup(createCommonReplyKeyboard());

                try {
                    execute(replyMessage); // Call method to send the message
                } catch (TelegramApiException e) {
                    log.error("Error sending telegram message", e);
                }
            } else {
                String receiveText = receiveMessage.getText();

                if (Action.SHOW_TODAY_TIMETABLE.getCaption().equals(receiveText)) {
                    SendMessage replyMessage = new SendMessage(); // Create a SendMessage object with mandatory fields
                    replyMessage.setChatId(receiveMessage.getChatId().toString());
                    replyMessage.setText(
                            "Расписание занятий на сегодня: \n" +
                            "hot-iron \n" +
                            "йога");

                    try {
                        execute(replyMessage); // Call method to send the message
                    } catch (TelegramApiException e) {
                        log.error("Error sending telegram message", e);
                    }
                } else if (Action.SING_UP_FOR_TRAINING.getCaption().equals(receiveText)) {
                    SendMessage replyMessage = new SendMessage(); // Create a SendMessage object with mandatory fields
                    replyMessage.setChatId(receiveMessage.getChatId().toString());
                    replyMessage.setText(Action.CHOOSE_DATE_FOR_TRAINING.getCaption());
                    replyMessage.setReplyMarkup(createInlineReplyDatesKeyboard(Action.CHOOSE_DATE_FOR_TRAINING));

                    try {
                        execute(replyMessage); // Call method to send the message
                    } catch (TelegramApiException e) {
                        log.error("Error sending telegram message", e);
                    }
                } else if (Action.SHOW_TIMETABLE_ON_DATE.getCaption().equals(receiveText)) {
                    SendMessage replyMessage = new SendMessage(); // Create a SendMessage object with mandatory fields
                    replyMessage.setChatId(receiveMessage.getChatId().toString());
                    replyMessage.setText(Action.CHOOSE_DATE_FOR_TIMETABLE.getCaption());
                    replyMessage.setReplyMarkup(createInlineReplyDatesKeyboard(Action.CHOOSE_DATE_FOR_TIMETABLE));

                    try {
                        execute(replyMessage); // Call method to send the message
                    } catch (TelegramApiException e) {
                        log.error("Error sending telegram message", e);
                    }
                }

            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Integer messageId = callbackQuery.getMessage().getMessageId();
            String chatId = callbackQuery.getMessage().getChatId().toString();
            CallbackData callbackData = parseCallbackData(callbackQuery);

            switch (callbackData.getClientAction()) {
                case CHOOSE_DATE_FOR_TRAINING: {
                    EditMessageText new_message = EditMessageText.builder()
                            .chatId(chatId)
                            .messageId(messageId)
                            .text(Action.CHOOSE_TRAINING.getCaption())
                            .replyMarkup(createInlineReplyTrainingList(callbackData.getData()))
                            .build();
                    try {
                        execute(new_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case CHOOSE_TRAINING: {
                    String answer =
                            "Вы записаны на " + callbackData.getData();

                    EditMessageText new_message = EditMessageText.builder()
                            .chatId(chatId)
                            .messageId(messageId)
                            .text(answer)
                            .build();
                    try {
                        execute(new_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case CHOOSE_DATE_FOR_TIMETABLE: {
                    String answer =
                            "Расписание занятий на " + callbackData.getData() + " \n" +
                                    "cycle \n" +
                                    "йога";

                    EditMessageText new_message = EditMessageText.builder()
                            .chatId(chatId)
                            .messageId(messageId)
                            .text(answer)
                            .build();
                    try {
                        execute(new_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

        }
    }

    private InlineKeyboardMarkup createInlineReplyTrainingList(String date) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        String[] trainingList = {"йога", "cycle", "hot-iron"};

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (String training : trainingList) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(createInlineButton(Action.CHOOSE_TRAINING, date + " " + training, training));
            rowsInline.add(row);
        }

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private CallbackData parseCallbackData(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        String[] strings = data.split(ACTION_AND_DATA_SEPARATOR);
        Action clientAction = Action.valueOf(strings[0]);

        return CallbackData.builder()
                .clientAction(clientAction)
                .data(strings[1])
                .build();
    }

    private boolean isStartCommand(Message message) {
        return message.getText().equals("/start");
    }

    private InlineKeyboardMarkup createInlineReplyDatesKeyboard(Action clientAction) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();

        LocalDate today = LocalDate.now();
        firstRow.add(createInlineButton(clientAction, today.toString(), "Сегодня"));

        LocalDate tomorrow = today.plusDays(1);
        firstRow.add(createInlineButton(clientAction, tomorrow.toString(), "Завтра"));
        rowsInline.add(firstRow);

        for (int i = 1; i < 4; i++) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int j = 1; j < 3; j++) {
                LocalDate day = today.plusDays(i + j);
                row.add(createInlineButton(clientAction, day.toString(), day.toString()));
            }
            rowsInline.add(row);
        }

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private InlineKeyboardButton createInlineButton(Action clientAction, String callbackData, String buttonText) {
        return InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(clientAction.toString() + ACTION_AND_DATA_SEPARATOR + callbackData)
                .build();
    }

    public ReplyKeyboardMarkup createCommonReplyKeyboard() {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setSelective(true);
        replyKeyboard.setResizeKeyboard(true);
        replyKeyboard.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(createKeyboardButton(Action.SHOW_TODAY_TIMETABLE));
        keyboardFirstRow.add(createKeyboardButton(Action.SHOW_TIMETABLE_ON_DATE));
        keyboard.add(keyboardFirstRow);

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(createKeyboardButton(Action.SING_UP_FOR_TRAINING));
        keyboardSecondRow.add(createKeyboardButton(Action.REMOVE_TRAINING_RECORD));
        keyboard.add(keyboardSecondRow);

        replyKeyboard.setKeyboard(keyboard);

        return replyKeyboard;
    }

    private KeyboardButton createKeyboardButton(Action showTodayTimetable) {
        return new KeyboardButton(showTodayTimetable.getCaption());
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
