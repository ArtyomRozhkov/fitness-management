package com.rozhkov.fitness.management.telegram.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rozhkov.fitness.management.telegram.action.Action;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Map;

@RequiredArgsConstructor
public class CallbackHelper {

    private final ObjectMapper objectMapper;

    public CallbackData parseCallbackData(String callbackData) {
        try {
            return objectMapper.readValue(callbackData, CallbackData.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Ошибка при десериализации callback", e);
        }
    }

    public InlineKeyboardButton createInlineButton(String buttonCaption, Action action, Object data) {
        try {
            CallbackData callbackData = CallbackData.builder()
                    .clientAction(action)
                    .params(objectMapper.convertValue(data, Map.class))
                    .build();
            String callbackDataStr = objectMapper.writeValueAsString(callbackData);
            if (callbackDataStr.length() > 64) {
                throw new IllegalArgumentException("Превышена максимально возможная длина данных для кнопки");
            }
            return InlineKeyboardButton.builder()
                    .text(buttonCaption)
                    .callbackData(callbackDataStr)
                    .build();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException();
        }
    }

    public InlineKeyboardButton createInlineButton(String buttonCaption, Action action) {
        try {
            CallbackData callbackData = CallbackData.builder()
                    .clientAction(action)
                    .build();
            return InlineKeyboardButton.builder()
                    .text(buttonCaption)
                    .callbackData(objectMapper.writeValueAsString(callbackData))
                    .build();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException();
        }
    }

    public <T> T retrieveCallbackData(CallbackData callbackData, Class<T> clazz) {
        return objectMapper.convertValue(callbackData.getParams(), clazz);
    }
}
