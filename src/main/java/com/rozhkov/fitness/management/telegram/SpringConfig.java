package com.rozhkov.fitness.management.telegram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rozhkov.fitness.management.telegram.callback.*;
import com.rozhkov.fitness.management.telegram.message.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

@Configuration
public class SpringConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    @Validated
    @ConfigurationProperties(prefix = "telegram")
    public TelegramProperties telegramProperties() {
        return new TelegramProperties();
    }

    @Bean
    public MessageHandler messageHandler() {
        return new StartMessageHandler(
                new ShowTodayTimetableMessageHandler(
                        new SignUpForTrainingMessageHandler(callbackHelper(),
                                new ShowWeekTimetableMessageHandler(
                                        new ShowMyTrainingRecordMessageHandler(callbackHelper(),
                                                new UnsupportedMessageHandler())
                                )
                        )
                )
        );
    }

    @Bean
    public CallbackHandler callbackHandler() {
        return new ChosenDateForTrainingCallbackHandler(callbackHelper(),
                new ChosenTrainingCallbackHandler(callbackHelper(),
                        new RemoveTrainingRecordCallbackHandler(callbackHelper(),
                                new RemovedTrainingRecordCallbackHandler(callbackHelper(), null))
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
