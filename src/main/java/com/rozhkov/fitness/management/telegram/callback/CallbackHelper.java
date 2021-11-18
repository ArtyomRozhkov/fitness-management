package com.rozhkov.fitness.management.telegram.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.action.ActionData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@RequiredArgsConstructor
public class CallbackHelper {

    private static final int MAX_LENGTH_OF_CALLBACK_DATA = 64;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public CallbackData parseCallbackData(String callbackData) {
        return objectMapper.readValue(callbackData, CallbackData.class);
    }

    public <T> T retrieveCallbackData(CallbackData callbackData, Class<T> clazz) {
        return objectMapper.convertValue(callbackData.getParams(), clazz);
    }

    public InlineKeyboardButton createInlineButton(String buttonCaption, ActionData actionData) {
        CallbackData callbackData = objectMapper.convertValue(actionData, CallbackData.class);
        return createInlineButton(buttonCaption, callbackData);
    }

    public InlineKeyboardButton createInlineButton(String buttonCaption, Action action) {
        CallbackData callbackData = new CallbackData()
                .setAction(action);
        return createInlineButton(buttonCaption, callbackData);
    }

    @SneakyThrows
    private InlineKeyboardButton createInlineButton(String buttonCaption, CallbackData callbackData) {
        String callbackDataStr = objectMapper.writeValueAsString(callbackData);
        if (callbackDataStr.length() > MAX_LENGTH_OF_CALLBACK_DATA) {
            throw new IllegalArgumentException("Превышена максимально возможная длина данных для кнопки");
        }
        return InlineKeyboardButton.builder()
                .text(buttonCaption)
                .callbackData(callbackDataStr)
                .build();
    }
}
