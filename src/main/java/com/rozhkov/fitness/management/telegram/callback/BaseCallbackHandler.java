package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.service.FitnessService;
import com.rozhkov.fitness.management.telegram.i18n.TextBuilder;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;

/**
 * Базовый класс для обработчиков нажатия на кнопки
 *
 * Реализует паттерт "Цепочка обязанностей", соединяя всех обработчиков в цепочку. {@link Callback} идет по цепочке
 * до тех пор, пока метод {@link #canHandle(Callback)} очередного обратчика не возвратит {@code true}. После чего
 * происходит обработка {@link Callback} методом {@link #handle(Callback)} и процесс завершается.
 *
 */
@RequiredArgsConstructor
public abstract class BaseCallbackHandler implements CallbackHandler {

    protected final CallbackHelper callbackHelper;
    protected final TextBuilder textBuilder;
    protected final FitnessService fitnessService;

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
