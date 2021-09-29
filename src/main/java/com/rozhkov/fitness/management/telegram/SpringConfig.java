package com.rozhkov.fitness.management.telegram;

import com.rozhkov.fitness.management.telegram.callback.*;
import com.rozhkov.fitness.management.telegram.message.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

@Configuration
public class SpringConfig {

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
                        new SignUpForTrainingMessageHandler(callbackQueryDataProcessor(),
                                new ShowWeekTimetableMessageHandler(
                                        new ShowMyTrainingRecordMessageHandler(callbackQueryDataProcessor(),
                                                new UnsupportedMessageHandler())
                                )
                        )
                )
        );
    }

    @Bean
    public CallbackHandler callbackHandler() {
        return new ChosenDateForTrainingCallbackHandler(callbackQueryDataProcessor(),
                new ChosenTrainingCallbackHandler(
                        new RemoveTrainingRecordCallbackHandler(callbackQueryDataProcessor(),
                                new RemovedTrainingRecordCallbackHandler(null))
                )
        );
    }

    @Bean
    public CallbackQueryDataProcessor callbackQueryDataProcessor() {
        return new CallbackQueryDataProcessor();
    }

    @Bean
    public LongPollingBot telegramBot(TelegramProperties telegramProperties) {
        return new TelegramBotHandler(
                telegramProperties.getBotName(), telegramProperties.getBotToken(),
                messageHandler(), callbackHandler(), callbackQueryDataProcessor());
    }

}
