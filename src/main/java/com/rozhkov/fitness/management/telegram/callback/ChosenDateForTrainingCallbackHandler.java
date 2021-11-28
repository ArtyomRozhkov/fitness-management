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
import java.util.List;
import java.util.stream.Collectors;

public class ChosenDateForTrainingCallbackHandler extends BaseCallbackHandler {

    public ChosenDateForTrainingCallbackHandler(FitnessService fitnessService,
                                                TextBuilder textBuilder,
                                                CallbackHelper callbackHelper,
                                                CallbackHandler nextCallbackHandler) {
        super(Action.CHOSEN_DATE_FOR_TRAINING, callbackHelper, textBuilder, fitnessService, nextCallbackHandler);
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
        ChosenDateForTrainingData callbackData = getActionData(callback, ChosenDateForTrainingData.class);

        LocalDate trainingDate = callbackData.getDate();
        List<Training> trainings = fitnessService.getTrainingTimetableOnDate(trainingDate);

        List<List<InlineKeyboardButton>> trainingButtons = trainings.stream()
                .map(training -> createTrainingButton(trainingDate, training))
                .map(List::of)
                .collect(Collectors.toList());

        return InlineKeyboardMarkup.builder()
                .keyboard(trainingButtons)
                .build();
    }

    private InlineKeyboardButton createTrainingButton(LocalDate trainingDate, Training training) {
        ChosenTrainingData data = new ChosenTrainingData()
                .setDate(trainingDate)
                .setTimetableId(training.getTimetableId());

        return callbackHelper.createInlineButton(textBuilder.trainingToString(training), data);
    }
}
