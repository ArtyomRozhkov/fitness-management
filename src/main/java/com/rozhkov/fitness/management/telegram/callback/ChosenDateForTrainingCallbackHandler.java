package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.telegram.Action;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ChosenDateForTrainingCallbackHandler extends BaseCallbackHandler {

    private final CallbackQueryDataProcessor callbackQueryDataProcessor;

    public ChosenDateForTrainingCallbackHandler(
            CallbackQueryDataProcessor callbackQueryDataProcessor, CallbackHandler nextCallbackHandler) {
        super(nextCallbackHandler);
        this.callbackQueryDataProcessor = callbackQueryDataProcessor;
    }

    @Override
    protected boolean canHandle(Callback callback) {
        return callback.getClientAction() == Action.CHOSEN_DATE_FOR_TRAINING;
    }

    @Override
    protected EditMessageText handle(Callback callback) {
        return EditMessageText.builder()
                .chatId(callback.getChatId())
                .messageId(callback.getMessageId())
                .text(Action.CHOOSE_TRAINING.getCaption())
                .replyMarkup(createInlineReplyTrainingList(callback.getDataParams()[0]))
                .build();
    }

    private InlineKeyboardMarkup createInlineReplyTrainingList(String date) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        String[] trainingList = {"13:00 - 14:00 йога", "17:00 - 17:50 cycle", "18:00 - 18:50 hot-iron"};

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (String training : trainingList) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            String callbackQueryData = callbackQueryDataProcessor.createCallbackQueryData(Action.CHOSEN_TRAINING, date, training);
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
