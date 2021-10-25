package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.action.ChosenTrainingData;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.time.LocalDate;

public class ChosenTrainingCallbackHandler extends BaseCallbackHandler {

    public ChosenTrainingCallbackHandler(CallbackHelper callbackHelper, CallbackHandler nextCallbackHandler) {
        super(callbackHelper, nextCallbackHandler);
    }

    @Override
    protected boolean canHandle(Callback callback) {
        return callback.getCallbackData().getClientAction() == Action.CHOSEN_TRAINING;
    }

    @Override
    protected EditMessageText handle(Callback callback) {
        ChosenTrainingData trainingData = callbackHelper.retrieveCallbackData(callback.getCallbackData(), ChosenTrainingData.class);
        String trainingDate = trainingData.getTraining();
        LocalDate training = trainingData.getDate();
        String answer = String.format("Вы записаны %s на %s", trainingDate, training);

        return EditMessageText.builder()
                .chatId(callback.getChatId())
                .messageId(callback.getMessageId())
                .text(answer)
                .build();
    }
}
