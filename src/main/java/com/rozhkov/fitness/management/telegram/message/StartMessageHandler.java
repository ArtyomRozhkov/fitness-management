package com.rozhkov.fitness.management.telegram.message;

import com.rozhkov.fitness.management.telegram.Action;
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
        SendMessage replyMessage = new SendMessage();
        replyMessage.setChatId(message.getChatId().toString());
        replyMessage.setText("Привет " + message.getFrom().getFirstName());
        replyMessage.setReplyMarkup(createCommonReplyKeyboard());

        return replyMessage;
    }

    public ReplyKeyboardMarkup createCommonReplyKeyboard() {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setSelective(true);
        replyKeyboard.setResizeKeyboard(true);
        replyKeyboard.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(createKeyboardButton(Action.SHOW_TODAY_TIMETABLE));
        keyboardFirstRow.add(createKeyboardButton(Action.SHOW_WEEK_TIMETABLE));
        keyboard.add(keyboardFirstRow);

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(createKeyboardButton(Action.SING_UP_FOR_TRAINING));
        keyboardSecondRow.add(createKeyboardButton(Action.SHOW_MY_TRAINING_RECORDS));
        keyboard.add(keyboardSecondRow);

        replyKeyboard.setKeyboard(keyboard);

        return replyKeyboard;
    }

    private KeyboardButton createKeyboardButton(Action showTodayTimetable) {
        return new KeyboardButton(showTodayTimetable.getCaption());
    }
}
