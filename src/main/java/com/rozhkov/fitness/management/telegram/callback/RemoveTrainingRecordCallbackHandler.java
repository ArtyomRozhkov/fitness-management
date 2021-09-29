package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.telegram.Action;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class RemoveTrainingRecordCallbackHandler extends BaseCallbackHandler {

    private final CallbackQueryDataProcessor callbackQueryDataProcessor;

    public RemoveTrainingRecordCallbackHandler(CallbackQueryDataProcessor callbackQueryDataProcessor, CallbackHandler nextCallbackHandler) {
        super(nextCallbackHandler);
        this.callbackQueryDataProcessor = callbackQueryDataProcessor;
    }

    @Override
    protected boolean canHandle(Callback callback) {
        return callback.getClientAction() == Action.REMOVE_TRAINING_RECORD;
    }

    @Override
    protected SendMessage handle(Callback callback) {
        return SendMessage.builder()
                .chatId(callback.getChatId())
                .text(Action.REMOVE_TRAINING_RECORD.getCaption())
                .replyMarkup(createInlineReplyTrainingList())
                .build();
    }

    private InlineKeyboardMarkup createInlineReplyTrainingList() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        String[] trainingList = {"13:00 - 14:00 йога", "17:00 - 17:50 cycle", "18:00 - 18:50 hot-iron"};

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (String training : trainingList) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            String callbackQueryData = callbackQueryDataProcessor.createCallbackQueryData(Action.REMOVED_TRAINING_RECORD, training);
            row.add(createInlineButton(training, callbackQueryData));
            rowsInline.add(row);
        }

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private InlineKeyboardButton createInlineButton(String buttonText, String callbackData) {
        return InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(callbackData)
                .build();
    }
}
