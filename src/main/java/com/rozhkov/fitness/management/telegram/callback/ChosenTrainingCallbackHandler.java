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
        super(Action.CHOSEN_TRAINING, callbackHelper, textBuilder, fitnessService, nextCallbackHandler);
    }

    @Override
    protected EditMessageText handle(Callback callback) {
        ChosenTrainingData trainingData = getActionData(callback, ChosenTrainingData.class);
        Training training = fitnessService.getTraining(trainingData.getTimetableId());
        String answer = textBuilder.createRecordedForTrainingText(trainingData.getDate(), training);

        return EditMessageText.builder()
                .chatId(callback.getChatId())
                .messageId(callback.getMessageId())
                .text(answer)
                .build();
    }
}
