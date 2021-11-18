package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.service.FitnessService;
import com.rozhkov.fitness.management.service.Training;
import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.action.RemovedTrainingRecordData;
import com.rozhkov.fitness.management.telegram.i18n.TextBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RemoveTrainingRecordCallbackHandler extends BaseCallbackHandler {

    public RemoveTrainingRecordCallbackHandler(CallbackHelper callbackHelper,
                                               TextBuilder textBuilder,
                                               FitnessService fitnessService,
                                               CallbackHandler nextCallbackHandler) {
        super(Action.REMOVE_TRAINING_RECORD, callbackHelper, textBuilder, fitnessService, nextCallbackHandler);
    }

    @Override
    protected SendMessage handle(Callback callback) {
        return SendMessage.builder()
                .chatId(callback.getChatId())
                .text(Action.REMOVED_TRAINING_RECORD.getCaption())
                .replyMarkup(createInlineReplyTrainingList(callback.getUser().getId()))
                .build();
    }

    private InlineKeyboardMarkup createInlineReplyTrainingList(Long userId) {
        Map<LocalDate, List<Training>> trainings = fitnessService.getUserTrainings(userId);

        List<List<InlineKeyboardButton>> trainingButtons = trainings.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .flatMap(this::toDayAndTrainingStream)
                .map(this::createTrainingButton)
                .map(List::of)
                .collect(Collectors.toList());

        return InlineKeyboardMarkup.builder()
                .keyboard(trainingButtons)
                .build();
    }

    private Stream<AbstractMap.SimpleEntry<LocalDate, Training>> toDayAndTrainingStream(
            Map.Entry<LocalDate, List<Training>> dayAndTrainings) {
        return dayAndTrainings.getValue()
                .stream()
                .map(training -> new AbstractMap.SimpleEntry<>(dayAndTrainings.getKey(), training));
    }

    private InlineKeyboardButton createTrainingButton(AbstractMap.SimpleEntry<LocalDate, Training> dayAndTraining) {
        LocalDate trainingDate = dayAndTraining.getKey();
        Training training = dayAndTraining.getValue();

        RemovedTrainingRecordData data = new RemovedTrainingRecordData()
                .setTimetableId(training.getTimetableId())
                .setDate(trainingDate);

       return callbackHelper.createInlineButton(
                textBuilder.trainingToString(training, trainingDate), data);
    }

}
