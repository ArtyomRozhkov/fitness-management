package com.rozhkov.fitness.management.telegram.message;

import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.callback.CallbackData;
import com.rozhkov.fitness.management.telegram.callback.CallbackHelper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

public class ShowMyTrainingRecordMessageHandler extends BaseMessageHandler {

    private final CallbackHelper callbackHelper;

    public ShowMyTrainingRecordMessageHandler(CallbackHelper callbackHelper,
                                              MessageHandler nextMessageHandler) {
        super(nextMessageHandler);
        this.callbackHelper = callbackHelper;
    }

    @Override
    protected boolean canHandle(Message message) {
        return Action.SHOW_MY_TRAINING_RECORDS.getCaption().equals(message.getText());
    }

    @Override
    protected SendMessage handle(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(getClientTraining())
                .replyMarkup(createInlineReplyDatesKeyboard())
                .build();
    }

    private String getClientTraining() {
        return "Вы записаны на пн: \n" +
                "18:00 - 18:50 hot-iron \n" +
                "\nна вт: \n" +
                "18:00 - 18:50 hot-iron \n" +
                "\nна чт: \n" +
                "19:00 - 20:00 йога" +
                "\nна сб: \n" +
                "18:00 - 18:50 hot-iron";
    }

    private InlineKeyboardMarkup createInlineReplyDatesKeyboard() {
        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(List.of(callbackHelper.createInlineButton("Удалить запись", Action.REMOVE_TRAINING_RECORD))))
                .build();
    }
}
