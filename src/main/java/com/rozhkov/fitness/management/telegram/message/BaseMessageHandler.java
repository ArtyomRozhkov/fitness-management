package com.rozhkov.fitness.management.telegram.message;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@RequiredArgsConstructor
public abstract class BaseMessageHandler implements MessageHandler {

    private final MessageHandler nextMessageHandler;

    @Override
    public final SendMessage handleMessage(Message message) {
        if (canHandle(message)) {
            return handle(message);
        } else {
            if (nextMessageHandler != null) {
                return nextMessageHandler.handleMessage(message);
            } else {
                throw new IllegalArgumentException(String.format("Невозможно обработать сообщение: %s", message));
            }
        }
    }

    protected abstract boolean canHandle(Message message);

    protected abstract SendMessage handle(Message message);
}
