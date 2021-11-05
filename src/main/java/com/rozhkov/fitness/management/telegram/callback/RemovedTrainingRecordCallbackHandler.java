package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.service.FitnessService;
import com.rozhkov.fitness.management.service.Training;
import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.action.RemovedTrainingRecordData;
import com.rozhkov.fitness.management.telegram.i18n.TextBuilder;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class RemovedTrainingRecordCallbackHandler extends BaseCallbackHandler {

    public RemovedTrainingRecordCallbackHandler(CallbackHelper callbackHelper,
                                                TextBuilder textBuilder,
                                                FitnessService fitnessService,
                                                CallbackHandler nextCallbackHandler) {
        super(callbackHelper, textBuilder, fitnessService, nextCallbackHandler);
    }

    @Override
    protected boolean canHandle(Callback callback) {
        return callback.getCallbackData().getClientAction() == Action.REMOVED_TRAINING_RECORD;
    }

    @Override
    protected EditMessageText handle(Callback callback) {
        RemovedTrainingRecordData data = callbackHelper.retrieveCallbackData(callback.getCallbackData(), RemovedTrainingRecordData.class);
        Training training = fitnessService.getTraining(data.getTimetableId());
        String answer = textBuilder.createRemoveTrainingRecordText(training, data.getDate());

        return EditMessageText.builder()
                .chatId(callback.getChatId())
                .messageId(callback.getMessageId())
                .text(answer)
                .build();
    }
}
