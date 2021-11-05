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
    private final TextBuilder textBuilder;

    public SignUpForTrainingMessageHandler(CallbackHelper callbackHelper,
                                           TextBuilder textBuilder,
                                           MessageHandler nextMessageHandler) {
        super(nextMessageHandler);
        this.callbackHelper = callbackHelper;
        this.textBuilder = textBuilder;
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
        Action action = Action.CHOSEN_DATE_FOR_TRAINING;
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int rowCounter = 0; rowCounter < ROW_NUMBER; rowCounter++) {
            if (rowCounter == 0) {
                rows.add(createFirstRow(action, today));
            } else {
                rows.add(createRow(action, today, rowCounter));
            }
        }

        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();
    }

    private List<InlineKeyboardButton> createFirstRow(Action action, LocalDate today) {
        var row = new ArrayList<InlineKeyboardButton>();
        row.add(createInlineButton(textBuilder.createTodayText(), action, today));

        LocalDate tomorrow = today.plusDays(1);
        row.add(createInlineButton(textBuilder.createTomorrowText(), action, tomorrow));

        return row;
    }

    private List<InlineKeyboardButton> createRow(Action action, LocalDate today, int rowCounter) {
        var row = new ArrayList<InlineKeyboardButton>();

        for (int columnCounter = 0; columnCounter < COLUMN_NUMBER; columnCounter++) {
            LocalDate day = today.plusDays(rowCounter * COLUMN_NUMBER + columnCounter);
            row.add(createInlineButton(textBuilder.createDateText(day), action, day));
        }

        return row;
    }

    private InlineKeyboardButton createInlineButton(String buttonText, Action clientAction, LocalDate date) {
        var data = new ChosenDateForTrainingData()
                .setDate(date);
        return callbackHelper.createInlineButton(buttonText, clientAction, data);
    }
}
