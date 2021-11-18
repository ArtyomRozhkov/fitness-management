package com.rozhkov.fitness.management.telegram.message;

import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.action.ChosenDateForTrainingData;
import com.rozhkov.fitness.management.telegram.callback.CallbackHelper;
import com.rozhkov.fitness.management.telegram.i18n.TextBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SignUpForTrainingMessageHandler extends BaseMessageHandler {

    private static final int ROW_NUMBER = 4;
    private static final int COLUMN_NUMBER = 2;

    private final CallbackHelper callbackHelper;

    public SignUpForTrainingMessageHandler(CallbackHelper callbackHelper,
                                           TextBuilder textBuilder,
                                           MessageHandler nextMessageHandler) {
        super(textBuilder, nextMessageHandler);
        this.callbackHelper = callbackHelper;
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
                .replyMarkup(createInlineReplyDatesKeyboard())
                .build();
    }

    private InlineKeyboardMarkup createInlineReplyDatesKeyboard() {
        var keyboardRows = new ArrayList<List<InlineKeyboardButton>>();
        var today = LocalDate.now();

        for (int rowCounter = 0; rowCounter < ROW_NUMBER; rowCounter++) {
            if (rowCounter == 0) {
                keyboardRows.add(createFirstRow(today));
            } else {
                keyboardRows.add(createRow(today, rowCounter));
            }
        }

        return InlineKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .build();
    }

    private List<InlineKeyboardButton> createFirstRow(LocalDate today) {
        var row = new ArrayList<InlineKeyboardButton>();
        row.add(createDateButton(textBuilder.createTodayText(), today));

        LocalDate tomorrow = today.plusDays(1);
        row.add(createDateButton(textBuilder.createTomorrowText(), tomorrow));

        return row;
    }

    private List<InlineKeyboardButton> createRow(LocalDate today, int rowCounter) {
        var row = new ArrayList<InlineKeyboardButton>();

        for (int columnCounter = 0; columnCounter < COLUMN_NUMBER; columnCounter++) {
            LocalDate day = today.plusDays((long) rowCounter * COLUMN_NUMBER + columnCounter);
            row.add(createDateButton(textBuilder.createDateText(day), day));
        }

        return row;
    }

    private InlineKeyboardButton createDateButton(String buttonText, LocalDate date) {
        var data = new ChosenDateForTrainingData()
                .setDate(date);
        return callbackHelper.createInlineButton(buttonText, data);
    }
}
