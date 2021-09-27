package com.rozhkov.fitness.management.message.telegram;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CallbackData {

    Action clientAction;
    String data;
}
