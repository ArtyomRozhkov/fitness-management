package com.rozhkov.fitness.management.telegram.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rozhkov.fitness.management.telegram.action.ActionData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@RequiredArgsConstructor
public class CallbackHelper {

    /**
     * Размер метаданных для кнопок в telegram ограничен.
     */
    private static final int MAX_LENGTH_OF_CALLBACK_DATA = 64;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T extends ActionData> T parseCallbackData(String callbackData, Class<T> clazz) {
        return objectMapper.readValue(callbackData, clazz);
    }

    @SneakyThrows
    public InlineKeyboardButton createInlineButton(String buttonCaption, ActionData actionData) {
        String callbackData = objectMapper.writeValueAsString(actionData);
        if (callbackData.length() > MAX_LENGTH_OF_CALLBACK_DATA) {
            throw new IllegalArgumentException("Превышена максимально возможная длина данных для кнопки");
        }
        return InlineKeyboardButton.builder()
                .text(buttonCaption)
                .callbackData(callbackData)
                .build();
    }
}
