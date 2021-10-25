package com.rozhkov.fitness.management.telegram.message;

import com.rozhkov.fitness.management.telegram.action.Action;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class StartMessageHandler extends BaseMessageHandler {

    public StartMessageHandler(MessageHandler nextMessageHandler) {
        super(nextMessageHandler);
    }

    @Override
    protected boolean canHandle(Message message) {
        return Action.START.getCaption().equals(message.getText());
    }

    @Override
    protected SendMessage handle(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text("Привет " + getClientName(message))
                .replyMarkup(createMainReplyKeyboard())
                .build();
    }

    private String getClientName(Message message) {
        return message.getFrom().getFirstName();
    }

    public ReplyKeyboardMarkup createMainReplyKeyboard() {
        return ReplyKeyboardMarkup.builder()
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .keyboard(createKeyboardRows())
                .build();
    }

    private ArrayList<KeyboardRow> createKeyboardRows() {
        List<List<Action>> keyboardButtons = List.of(
                List.of(Action.SHOW_TODAY_TIMETABLE, Action.SHOW_WEEK_TIMETABLE),
                List.of(Action.SING_UP_FOR_TRAINING, Action.SHOW_MY_TRAINING_RECORDS));

        var keyboardRows = new ArrayList<KeyboardRow>();
        for (List<Action> keyboardRowButtons : keyboardButtons) {
            var keyboardRow = createKeyboardRow(keyboardRowButtons);
            keyboardRows.add(keyboardRow);
        }
        return keyboardRows;
    }

    private KeyboardRow createKeyboardRow(List<Action> rowButtons) {
        var keyboardRow = new KeyboardRow();
        for (Action button : rowButtons) {
            keyboardRow.add(createKeyboardButton(button));
        }
        return keyboardRow;
    }

    private KeyboardButton createKeyboardButton(Action action) {
        return new KeyboardButton(action.getCaption());
    }
}
