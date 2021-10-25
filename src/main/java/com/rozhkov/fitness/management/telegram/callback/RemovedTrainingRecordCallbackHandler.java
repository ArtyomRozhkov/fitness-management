package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.action.RemovedTrainingRecordData;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class RemovedTrainingRecordCallbackHandler extends BaseCallbackHandler {

    public RemovedTrainingRecordCallbackHandler(
            CallbackHelper callbackHelper, CallbackHandler nextCallbackHandler) {
        super(callbackHelper, nextCallbackHandler);
    }

    @Override
    protected boolean canHandle(Callback callback) {
        return callback.getCallbackData().getClientAction() == Action.REMOVED_TRAINING_RECORD;
    }

    @Override
    protected EditMessageText handle(Callback callback) {
        RemovedTrainingRecordData data = callbackHelper.retrieveCallbackData(callback.getCallbackData(), RemovedTrainingRecordData.class);
        String training = data.getTraining();
        String answer = String.format("Удалена запись на тренировку %s", training);

        return EditMessageText.builder()
                .chatId(callback.getChatId())
                .messageId(callback.getMessageId())
                .text(answer)
                .build();
    }
}
