package com.rozhkov.fitness.management.telegram.message;

import com.rozhkov.fitness.management.telegram.i18n.TextBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class UnsupportedMessageHandler extends BaseMessageHandler {

    public UnsupportedMessageHandler(TextBuilder textBuilder) {
        super(textBuilder, null);
    }

    @Override
    protected boolean canHandle(Message message) {
        return true;
    }

    @Override
    protected SendMessage handle(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(textBuilder.createUnsupportedCommandText())
                .build();
    }
}
