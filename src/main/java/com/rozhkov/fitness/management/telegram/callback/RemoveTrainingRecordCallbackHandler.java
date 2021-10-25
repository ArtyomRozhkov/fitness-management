package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.action.RemovedTrainingRecordData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RemoveTrainingRecordCallbackHandler extends BaseCallbackHandler {

    public RemoveTrainingRecordCallbackHandler(CallbackHelper callbackHelper, CallbackHandler nextCallbackHandler) {
        super(callbackHelper, nextCallbackHandler);
    }

    @Override
    protected boolean canHandle(Callback callback) {
        return callback.getCallbackData().getClientAction() == Action.REMOVE_TRAINING_RECORD;
    }

    @Override
    protected SendMessage handle(Callback callback) {
        return SendMessage.builder()
                .chatId(callback.getChatId())
                .text(Action.REMOVE_TRAINING_RECORD.getCaption())
                .replyMarkup(createInlineReplyTrainingList())
                .build();
    }

    private InlineKeyboardMarkup createInlineReplyTrainingList() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        String[] trainingList = {"13:00 - 14:00 йога", "17:00 - 17:50 cycle", "18:00 - 18:50 hot-iron"};

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (String training : trainingList) {
            RemovedTrainingRecordData data = new RemovedTrainingRecordData()
                    .setTraining(training);
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(callbackHelper.createInlineButton(training, Action.REMOVED_TRAINING_RECORD, data));
            rowsInline.add(row);
        }

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}
