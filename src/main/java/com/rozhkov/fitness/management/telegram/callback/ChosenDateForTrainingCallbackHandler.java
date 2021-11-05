package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.service.FitnessService;
import com.rozhkov.fitness.management.service.Training;
import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.action.ChosenDateForTrainingData;
import com.rozhkov.fitness.management.telegram.action.ChosenTrainingData;
import com.rozhkov.fitness.management.telegram.i18n.TextBuilder;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChosenDateForTrainingCallbackHandler extends BaseCallbackHandler {

    public ChosenDateForTrainingCallbackHandler(FitnessService fitnessService,
                                                TextBuilder textBuilder,
                                                CallbackHelper callbackHelper,
                                                CallbackHandler nextCallbackHandler) {
        super(callbackHelper, textBuilder, fitnessService, nextCallbackHandler);
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
        ChosenDateForTrainingData callbackData =
                callbackHelper.retrieveCallbackData(callback.getCallbackData(), ChosenDateForTrainingData.class);

        LocalDate trainingDate = callbackData.getDate();
        List<Training> trainings = fitnessService.getTrainingTimetableOnDate(trainingDate);

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (Training training : trainings) {
            ChosenTrainingData data = new ChosenTrainingData()
                    .setDate(trainingDate)
                    .setTimetableId(training.getTimetableId());

            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(callbackHelper.createInlineButton(textBuilder.trainingToString(training), Action.CHOSEN_TRAINING, data));
            rowsInline.add(row);
        }

        return InlineKeyboardMarkup.builder()
                .keyboard(rowsInline)
                .build();
    }
}
