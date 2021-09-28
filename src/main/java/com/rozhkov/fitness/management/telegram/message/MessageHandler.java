package com.rozhkov.fitness.management.telegram.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {

    SendMessage handleMessage(Message message);
}
