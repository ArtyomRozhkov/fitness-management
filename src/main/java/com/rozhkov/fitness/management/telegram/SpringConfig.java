package com.rozhkov.fitness.management.telegram;

import com.rozhkov.fitness.management.telegram.callback.*;
import com.rozhkov.fitness.management.telegram.message.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

@Configuration
public class SpringConfig {

    @Value("${telegram.bot.name}")
    private String telegramBotName;

    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    @Bean
    public MessageHandler messageHandler() {
        return new StartMessageHandler(
                new ShowTodayTimetableMessageHandler(
                        new SignUpForTrainingMessageHandler(
                                new ShowWeekTimetableMessageHandler(
                                        new ShowMyTrainingRecordMessageHandler(
                                                new UnsupportedMessageHandler(),
                                                callbackQueryDataProcessor())
                                ),
                                callbackQueryDataProcessor())
                )
        );
    }

    @Bean
    public CallbackHandler callbackHandler() {
        return new ChooseDateForTrainingCallbackHandler(
                callbackQueryDataProcessor(),
                new ChooseTrainingCallbackHandler(
                        new RemoveTrainingRecordCallbackHandler(callbackQueryDataProcessor(), null)
                )
        );
    }

    @Bean
    public CallbackQueryDataProcessor callbackQueryDataProcessor() {
        return new CallbackQueryDataProcessor();
    }

    @Bean
    public LongPollingBot telegramBot() {
        return new TelegramBotHandler(
                telegramBotName, telegramBotToken,
                messageHandler(), callbackHandler(), callbackQueryDataProcessor());
    }

}
