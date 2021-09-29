package com.rozhkov.fitness.management.telegram.message;

import com.rozhkov.fitness.management.telegram.Action;
import com.rozhkov.fitness.management.telegram.callback.CallbackQueryDataProcessor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ShowMyTrainingRecordMessageHandler extends BaseMessageHandler {

    private final CallbackQueryDataProcessor callbackQueryDataProcessor;

    public ShowMyTrainingRecordMessageHandler(MessageHandler nextMessageHandler, CallbackQueryDataProcessor callbackQueryDataProcessor) {
        super(nextMessageHandler);
        this.callbackQueryDataProcessor = callbackQueryDataProcessor;
    }

    @Override
    protected boolean canHandle(Message message) {
        return Action.SHOW_MY_TRAINING_RECORDS.getCaption().equals(message.getText());
    }

    @Override
    protected SendMessage handle(Message message) {
        SendMessage replyMessage = new SendMessage();
        replyMessage.setChatId(message.getChatId().toString());
        replyMessage.setText(
                "Вы записаны на пн: \n" +
                        "18:00 - 18:50 hot-iron \n" +
                        "\nна вт: \n" +
                        "18:00 - 18:50 hot-iron \n" +
                        "\nна чт: \n" +
                        "19:00 - 20:00 йога" +
                        "\nна сб: \n" +
                        "18:00 - 18:50 hot-iron");
        replyMessage.setReplyMarkup(createInlineReplyDatesKeyboard());

        return replyMessage;
    }

    private InlineKeyboardMarkup createInlineReplyDatesKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();

        firstRow.add(createInlineButton(Action.REMOVE_TRAINING_RECORD, "Удалить запись"));
        rowsInline.add(firstRow);

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private InlineKeyboardButton createInlineButton(Action clientAction, String buttonText) {
        String callbackData = callbackQueryDataProcessor.createCallbackQueryData(clientAction);
        return InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(callbackData)
                .build();
    }
}
