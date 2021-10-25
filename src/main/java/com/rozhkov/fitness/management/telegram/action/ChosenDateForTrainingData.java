package com.rozhkov.fitness.management.telegram.action;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class ChosenDateForTrainingData {

    LocalDate date;
}
