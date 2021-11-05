package com.rozhkov.fitness.management.telegram.callback;

import lombok.Builder;
import lombok.Value;
import org.telegram.telegrambots.meta.api.objects.User;

@Value
@Builder
public class Callback {

    Integer messageId;
    String chatId;
    User user;

    CallbackData callbackData;
}
