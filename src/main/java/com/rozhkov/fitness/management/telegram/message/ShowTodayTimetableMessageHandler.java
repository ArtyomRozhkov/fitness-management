package com.rozhkov.fitness.management.telegram.message;

import com.rozhkov.fitness.management.telegram.action.Action;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ShowTodayTimetableMessageHandler extends BaseMessageHandler {

    public ShowTodayTimetableMessageHandler(MessageHandler nextMessageHandler) {
        super(nextMessageHandler);
    }

    @Override
    protected boolean canHandle(Message message) {
        return Action.SHOW_TODAY_TIMETABLE.getCaption().equals(message.getText());
    }

    @Override
    protected SendMessage handle(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(getTodayTimeTable())
                .build();
    }

    private String getTodayTimeTable() {
        return "Расписание занятий на сегодня: \n" +
                "18:00 - 18:50 hot-iron \n" +
                "19:00 - 20:00 йога";
    }
}
