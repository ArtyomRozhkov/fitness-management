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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RemoveTrainingRecordCallbackHandler extends BaseCallbackHandler {

    public RemoveTrainingRecordCallbackHandler(CallbackHelper callbackHelper,
                                               TextBuilder textBuilder,
                                               FitnessService fitnessService,
                                               CallbackHandler nextCallbackHandler) {
        super(callbackHelper, textBuilder, fitnessService, nextCallbackHandler);
    }

    @Override
    protected boolean canHandle(Callback callback) {
        return callback.getCallbackData().getClientAction() == Action.REMOVE_TRAINING_RECORD;
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
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        trainings.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(localDateListEntry -> localDateListEntry.getValue()
                        .forEach(training -> {
                            LocalDate trainingDate = localDateListEntry.getKey();
                            RemovedTrainingRecordData data = new RemovedTrainingRecordData()
                                    .setTimetableId(training.getTimetableId())
                                    .setDate(trainingDate);

                            List<InlineKeyboardButton> row = new ArrayList<>();
                            row.add(callbackHelper.createInlineButton(textBuilder.trainingToString(training, trainingDate), Action.REMOVED_TRAINING_RECORD, data));
                            rowsInline.add(row);
                        })
                );

        return InlineKeyboardMarkup.builder()
                .keyboard(rowsInline)
                .build();
    }
}
