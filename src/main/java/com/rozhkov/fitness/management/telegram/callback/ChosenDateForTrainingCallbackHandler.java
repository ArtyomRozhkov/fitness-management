package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.action.ChosenDateForTrainingData;
import com.rozhkov.fitness.management.telegram.action.ChosenTrainingData;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ChosenDateForTrainingCallbackHandler extends BaseCallbackHandler {

    public ChosenDateForTrainingCallbackHandler(
            CallbackHelper callbackHelper, CallbackHandler nextCallbackHandler) {
        super(callbackHelper, nextCallbackHandler);
    }

    @Override
    protected boolean canHandle(Callback callback) {
        return callback.getCallbackData().getClientAction() == Action.CHOSEN_DATE_FOR_TRAINING;
    }

    @Override
    protected EditMessageText handle(Callback callback) {
        return EditMessageText.builder()
                .chatId(callback.getChatId())
                .messageId(callback.getMessageId())
                .text(Action.CHOOSE_TRAINING.getCaption())
                .replyMarkup(createInlineReplyTrainingList(callback))
                .build();
    }

    private InlineKeyboardMarkup createInlineReplyTrainingList(Callback callback) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        String[] trainingList = {"13:00 - 14:00 йога", "17:00 - 17:50 cycle", "18:00 - 18:50 hot-iron"};
        ChosenDateForTrainingData callbackData = callbackHelper.retrieveCallbackData(callback.getCallbackData(), ChosenDateForTrainingData.class);

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (String training : trainingList) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            ChosenTrainingData data = new ChosenTrainingData()
                    .setDate(callbackData.getDate())
                    .setTraining(training);
            row.add(callbackHelper.createInlineButton(training, Action.CHOSEN_TRAINING, data));
            rowsInline.add(row);
        }

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}
