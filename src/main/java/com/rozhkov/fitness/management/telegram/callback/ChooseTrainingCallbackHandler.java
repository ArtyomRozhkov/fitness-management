package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.telegram.Action;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class ChooseTrainingCallbackHandler extends BaseCallbackHandler {

    public ChooseTrainingCallbackHandler(CallbackHandler nextCallbackHandler) {
        super(nextCallbackHandler);
    }

    @Override
    protected boolean canHandle(Callback callback) {
        return callback.getClientAction() == Action.CHOOSE_TRAINING;
    }

    @Override
    protected EditMessageText handle(Callback callback) {
        String trainingDate = callback.getDataParams()[0];
        String training = callback.getDataParams()[1];
        String answer = String.format("Вы записаны %s на %s", trainingDate, training);

        return EditMessageText.builder()
                .chatId(callback.getChatId())
                .messageId(callback.getMessageId())
                .text(answer)
                .build();
    }
}
