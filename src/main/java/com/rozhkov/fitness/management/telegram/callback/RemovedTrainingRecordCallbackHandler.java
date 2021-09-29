package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.telegram.Action;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class RemovedTrainingRecordCallbackHandler extends BaseCallbackHandler {

    public RemovedTrainingRecordCallbackHandler(CallbackHandler nextCallbackHandler) {
        super(nextCallbackHandler);
    }

    @Override
    protected boolean canHandle(Callback callback) {
        return callback.getClientAction() == Action.REMOVED_TRAINING_RECORD;
    }

    @Override
    protected EditMessageText handle(Callback callback) {
        String training = callback.getDataParams()[0];
        String answer = String.format("Удалена запись на тренировку %s", training);

        return EditMessageText.builder()
                .chatId(callback.getChatId())
                .messageId(callback.getMessageId())
                .text(answer)
                .build();
    }
}
