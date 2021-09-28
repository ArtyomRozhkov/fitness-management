package com.rozhkov.fitness.management.telegram.callback;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;

public interface CallbackHandler {

    <T extends Serializable, Method extends BotApiMethod<T>> Method handleCallback(Callback callbackQuery);
}
