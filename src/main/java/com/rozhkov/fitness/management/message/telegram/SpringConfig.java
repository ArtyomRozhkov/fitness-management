package com.rozhkov.fitness.management.message.telegram;

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
    public LongPollingBot telegramBot() {
        return new TelegramBot(telegramBotName, telegramBotToken);
    }

}
