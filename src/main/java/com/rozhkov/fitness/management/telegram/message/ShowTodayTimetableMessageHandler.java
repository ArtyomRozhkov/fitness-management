package com.rozhkov.fitness.management.telegram.message;

import com.rozhkov.fitness.management.service.FitnessService;
import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.i18n.TextBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;

public class ShowTodayTimetableMessageHandler extends BaseMessageHandler {

    private final FitnessService fitnessService;
    private final TextBuilder textBuilder;

    public ShowTodayTimetableMessageHandler(FitnessService fitnessService,
                                            TextBuilder textBuilder,
                                            MessageHandler nextMessageHandler) {
        super(nextMessageHandler);
        this.fitnessService = fitnessService;
        this.textBuilder = textBuilder;
    }

    @Override
    protected boolean canHandle(Message message) {
        return Action.SHOW_TODAY_TIMETABLE.getCaption().equals(message.getText());
    }

    @Override
    protected SendMessage handle(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(getTodayTimetable())
                .build();
    }

    private String getTodayTimetable() {
        return textBuilder.createTodayTrainingsText(fitnessService.getTrainingTimetableOnDate(LocalDate.now()));
    }
}
