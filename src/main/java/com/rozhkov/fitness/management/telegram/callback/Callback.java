package com.rozhkov.fitness.management.telegram.callback;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Callback {

    Integer messageId;
    String chatId;

    CallbackData callbackData;
}
