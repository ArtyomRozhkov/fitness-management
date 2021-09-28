package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.telegram.Action;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Callback {

    Integer messageId;
    String chatId;

    Action clientAction;
    String[] dataParams;
}
