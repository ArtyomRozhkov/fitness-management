package com.rozhkov.fitness.management.telegram.action;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class ChosenDateForTrainingData implements ActionData {

    private final Action action = Action.CHOSEN_DATE_FOR_TRAINING;
    private LocalDate date;
}
