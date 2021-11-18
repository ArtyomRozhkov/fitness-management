package com.rozhkov.fitness.management.telegram.message;

import com.rozhkov.fitness.management.service.FitnessService;
import com.rozhkov.fitness.management.service.Training;
import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.callback.CallbackHelper;
import com.rozhkov.fitness.management.telegram.i18n.TextBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ShowMyTrainingRecordMessageHandler extends BaseMessageHandler {

    private final CallbackHelper callbackHelper;
    private final FitnessService fitnessService;

    public ShowMyTrainingRecordMessageHandler(CallbackHelper callbackHelper,
                                              TextBuilder textBuilder,
                                              FitnessService fitnessService,
                                              MessageHandler nextMessageHandler) {
        super(textBuilder, nextMessageHandler);
        this.callbackHelper = callbackHelper;
        this.fitnessService = fitnessService;
    }

    @Override
    protected boolean canHandle(Message message) {
        return Action.SHOW_MY_TRAINING_RECORDS.getCaption().equals(message.getText());
    }

    @Override
    protected SendMessage handle(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(getClientTraining(message.getFrom().getId()))
                .replyMarkup(createInlineReplyDatesKeyboard())
                .build();
    }

    private String getClientTraining(Long userId) {
        final Map<LocalDate, List<Training>> trainings = fitnessService.getUserTrainings(userId);
        return textBuilder.createUserTrainingsText(trainings);
    }

    private InlineKeyboardMarkup createInlineReplyDatesKeyboard() {
        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(List.of(
                        callbackHelper.createInlineButton(Action.REMOVE_TRAINING_RECORD.getCaption(), Action.REMOVE_TRAINING_RECORD))))
                .build();
    }
}
