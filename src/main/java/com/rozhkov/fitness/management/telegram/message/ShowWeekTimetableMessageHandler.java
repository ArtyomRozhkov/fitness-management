package com.rozhkov.fitness.management.telegram.message;

import com.rozhkov.fitness.management.telegram.Action;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ShowWeekTimetableMessageHandler extends BaseMessageHandler {

    public ShowWeekTimetableMessageHandler(MessageHandler nextMessageHandler) {
        super(nextMessageHandler);
    }

    @Override
    protected boolean canHandle(Message message) {
        return Action.SHOW_WEEK_TIMETABLE.getCaption().equals(message.getText());
    }

    @Override
    protected SendMessage handle(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(getWeekTimeTable())
                .build();
    }

    private String getWeekTimeTable() {
        return "Расписание занятий на пн: \n" +
                "18:00 - 18:50 hot-iron \n" +
                "19:00 - 20:00 йога" +
                "\n\nРасписание занятий на вт: \n" +
                "18:00 - 18:50 hot-iron \n" +
                "19:00 - 20:00 йога" +
                "\n\nРасписание занятий на ср: \n" +
                "18:00 - 18:50 hot-iron \n" +
                "19:00 - 20:00 йога" +
                "\n\nРасписание занятий на чт: \n" +
                "18:00 - 18:50 hot-iron \n" +
                "19:00 - 20:00 йога" +
                "\n\nРасписание занятий на пн: \n" +
                "18:00 - 18:50 hot-iron \n" +
                "19:00 - 20:00 йога" +
                "\n\nРасписание занятий на сб: \n" +
                "18:00 - 18:50 hot-iron \n" +
                "19:00 - 20:00 йога" +
                "\n\nРасписание занятий на вс: \n" +
                "18:00 - 18:50 hot-iron \n" +
                "19:00 - 20:00 йога";
    }
}
