package com.rozhkov.fitness.management.telegram.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class UnsupportedMessageHandler extends BaseMessageHandler {

    public UnsupportedMessageHandler() {
        super(null);
    }

    @Override
    protected boolean canHandle(Message message) {
        return true;
    }

    @Override
    protected SendMessage handle(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text("Не знаю чем могу помочь. Выберете одно из доступных действий")
                .build();
    }
}
