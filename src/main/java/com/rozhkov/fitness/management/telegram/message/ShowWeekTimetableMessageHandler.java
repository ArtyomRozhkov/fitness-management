package com.rozhkov.fitness.management.telegram.message;

import com.rozhkov.fitness.management.service.FitnessService;
import com.rozhkov.fitness.management.service.Training;
import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.i18n.TextBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public class ShowWeekTimetableMessageHandler extends BaseMessageHandler {

    private final FitnessService fitnessService;

    public ShowWeekTimetableMessageHandler(FitnessService fitnessService,
                                           TextBuilder textBuilder,
                                           MessageHandler nextMessageHandler) {
        super(textBuilder, nextMessageHandler);
        this.fitnessService = fitnessService;
    }

    @Override
    protected boolean canHandle(Message message) {
        return Action.SHOW_WEEK_TIMETABLE.getCaption().equals(message.getText());
    }

    @Override
    protected SendMessage handle(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(getWeekTimetable())
                .build();
    }

    private String getWeekTimetable() {
        final Map<DayOfWeek, List<Training>> trainingTimetableOnWeek = fitnessService.getTrainingTimetableOnWeek();
        return textBuilder.createTrainingTimetableOnWeekText(trainingTimetableOnWeek);
    }
}
