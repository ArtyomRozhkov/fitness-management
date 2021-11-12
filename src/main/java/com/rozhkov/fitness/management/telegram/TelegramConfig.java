package com.rozhkov.fitness.management.telegram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rozhkov.fitness.management.service.FitnessService;
import com.rozhkov.fitness.management.telegram.callback.*;
import com.rozhkov.fitness.management.telegram.i18n.TextBuilder;
import com.rozhkov.fitness.management.telegram.message.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

@Configuration
public class TelegramConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FitnessService fitnessService;

    @Autowired
    private MessageSource messageSource;

    @Bean
    @Validated
    @ConfigurationProperties(prefix = "telegram")
    public TelegramProperties telegramProperties() {
        return new TelegramProperties();
    }

    @Bean
    public TextBuilder textBuilder() {
        return new TextBuilder(messageSource);
    }

    @Bean
    public MessageHandler messageHandler() {
        return new StartMessageHandler(textBuilder(),
                new ShowTodayTimetableMessageHandler(fitnessService, textBuilder(),
                        new SignUpForTrainingMessageHandler(callbackHelper(), textBuilder(),
                                new ShowWeekTimetableMessageHandler(fitnessService, textBuilder(),
                                        new ShowMyTrainingRecordMessageHandler(callbackHelper(), textBuilder(), fitnessService,
                                                new UnsupportedMessageHandler(textBuilder()))
                                )
                        ))
        );
    }

    @Bean
    public CallbackHandler callbackHandler() {
        return new ChosenDateForTrainingCallbackHandler(fitnessService, textBuilder(), callbackHelper(),
                new ChosenTrainingCallbackHandler(callbackHelper(), textBuilder(), fitnessService,
                        new RemoveTrainingRecordCallbackHandler(callbackHelper(), textBuilder(), fitnessService,
                                new RemovedTrainingRecordCallbackHandler(callbackHelper(), textBuilder(), fitnessService, null))
                )
        );
    }

    @Bean
    public CallbackHelper callbackHelper() {
        return new CallbackHelper(objectMapper);
    }

    @Bean
    public LongPollingBot telegramBot(TelegramProperties telegramProperties, CallbackHelper callbackHelper) {
        return new TelegramBotHandler(
                telegramProperties.getBotName(), telegramProperties.getBotToken(),
                messageHandler(), callbackHandler(), callbackHelper);
    }

}
