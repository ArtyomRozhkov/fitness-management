package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.service.FitnessService;
import com.rozhkov.fitness.management.service.Training;
import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.action.ChosenTrainingData;
import com.rozhkov.fitness.management.telegram.i18n.TextBuilder;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class ChosenTrainingCallbackHandler extends BaseCallbackHandler {

    public ChosenTrainingCallbackHandler(CallbackHelper callbackHelper,
                                         TextBuilder textBuilder,
                                         FitnessService fitnessService,
                                         CallbackHandler nextCallbackHandler) {
        super(callbackHelper, textBuilder, fitnessService, nextCallbackHandler);
    }

    @Override
    protected boolean canHandle(Callback callback) {
        return callback.getCallbackData().getClientAction() == Action.CHOSEN_TRAINING;
    }

    @Override
    protected EditMessageText handle(Callback callback) {
        ChosenTrainingData trainingData = callbackHelper.retrieveCallbackData(callback.getCallbackData(), ChosenTrainingData.class);
        Training training = fitnessService.getTraining(trainingData.getTimetableId());
        String answer = textBuilder.createSignedUpForTrainingText(trainingData.getDate(), training);

        return EditMessageText.builder()
                .chatId(callback.getChatId())
                .messageId(callback.getMessageId())
                .text(answer)
                .build();
    }
}
