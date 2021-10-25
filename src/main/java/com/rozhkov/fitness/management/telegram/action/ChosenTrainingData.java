package com.rozhkov.fitness.management.telegram.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class ChosenTrainingData {

    @JsonProperty("d")
    LocalDate date;
    @JsonProperty("t")
    String training;
}
