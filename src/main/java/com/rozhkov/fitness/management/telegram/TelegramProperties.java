package com.rozhkov.fitness.management.telegram;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public final class TelegramProperties {

    @NotBlank
    String botName;
    @NotBlank
    String botToken;
}
