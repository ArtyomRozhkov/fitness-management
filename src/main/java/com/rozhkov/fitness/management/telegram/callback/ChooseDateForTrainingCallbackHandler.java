package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.telegram.Action;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChooseDateForTrainingCallbackHandler extends BaseCallbackHandler {

    private final CallbackQueryDataProcessor callbackQueryDataProcessor;

    public ChooseDateForTrainingCallbackHandler(
            CallbackQueryDataProcessor callbackQueryDataProcessor, CallbackHandler nextCallbackHandler) {
        super(nextCallbackHandler);
        this.callbackQueryDataProcessor = callbackQueryDataProcessor;
    }

    @Override
    protected boolean canHandle(Callback callback) {
        return callback.getClientAction() == Action.CHOOSE_DATE_FOR_TRAINING;
    }

    @Override
    protected BotApiMethod<Serializable> handle(Callback callback) {
        return EditMessageText.builder()
                .chatId(callback.getChatId())
                .messageId(callback.getMessageId())
                .text(Action.CHOOSE_TRAINING.getCaption())
                .replyMarkup(createInlineReplyTrainingList(callback.getDataParams()[0]))
                .build();
    }

    private InlineKeyboardMarkup createInlineReplyTrainingList(String date) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        String[] trainingList = {"йога", "cycle", "hot-iron"};

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (String training : trainingList) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            String callbackQueryData = callbackQueryDataProcessor.createCallbackQueryData(Action.CHOOSE_TRAINING, date, training);
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
