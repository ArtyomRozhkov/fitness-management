package com.rozhkov.fitness.management.telegram.message;

import com.rozhkov.fitness.management.telegram.Action;
import com.rozhkov.fitness.management.telegram.callback.CallbackQueryDataProcessor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SignUpForTrainingMessageHandler extends BaseMessageHandler {

    private static final int COLUMN_NUMBER = 2;

    private final CallbackQueryDataProcessor callbackQueryDataProcessor;

    public SignUpForTrainingMessageHandler(CallbackQueryDataProcessor callbackQueryDataProcessor,
                                           MessageHandler nextMessageHandler) {
        super(nextMessageHandler);
        this.callbackQueryDataProcessor = callbackQueryDataProcessor;
    }

    @Override
    protected boolean canHandle(Message message) {
        return Action.SING_UP_FOR_TRAINING.getCaption().equals(message.getText());
    }

    @Override
    protected SendMessage handle(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(Action.CHOOSE_DATE_FOR_TRAINING.getCaption())
                .replyMarkup(createInlineReplyDatesKeyboard(Action.CHOSEN_DATE_FOR_TRAINING))
                .build();
    }

    private InlineKeyboardMarkup createInlineReplyDatesKeyboard(Action action) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();

        LocalDate today = LocalDate.now();
        firstRow.add(createInlineButton(action, today, "Сегодня"));

        LocalDate tomorrow = today.plusDays(1);
        firstRow.add(createInlineButton(action, tomorrow, "Завтра"));
        rowsInline.add(firstRow);

        for (int i = 1; i < 4; i++) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int j = 0; j < COLUMN_NUMBER; j++) {
                LocalDate day = today.plusDays(i * COLUMN_NUMBER + j);
                row.add(createInlineButton(action, day, day.toString()));
            }
            rowsInline.add(row);
        }

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private InlineKeyboardButton createInlineButton(Action clientAction, LocalDate date, String buttonText) {
        String callbackData = callbackQueryDataProcessor.createCallbackQueryData(clientAction, date.toString());
        return InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(callbackData)
                .build();
    }
}
