package com.rozhkov.fitness.management.telegram.callback;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;

@RequiredArgsConstructor
public abstract class BaseCallbackHandler implements CallbackHandler {

    private final CallbackHandler nextCallbackHandler;

    @Override
    public final <T extends Serializable, Method extends BotApiMethod<T>> Method handleCallback(Callback callbackQuery) {
        if (canHandle(callbackQuery)) {
            return handle(callbackQuery);
        } else {
            if (nextCallbackHandler != null) {
                return nextCallbackHandler.handleCallback(callbackQuery);
            } else {
                throw new IllegalArgumentException(String.format("Невозможно обработать ответ: %s", callbackQuery));
            }
        }
    }

    protected abstract boolean canHandle(Callback callback);

    protected abstract <T extends Serializable, Method extends BotApiMethod<T>> Method handle(Callback callback);
}
