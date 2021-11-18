package com.rozhkov.fitness.management.telegram.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * Данные о записи на тренировку, которую нужно удалить
 *
 * Размер метаданных для кнопок в telegram ограничен 64 символами. Поэтому при сериализации/десериализации используются
 * сокращенные названия полей (см. {@link JsonProperty})
 */
@Data
@Accessors(chain = true)
public class RemovedTrainingRecordData implements ActionData {

    private final Action action = Action.REMOVED_TRAINING_RECORD;

    @JsonProperty("d")
    private LocalDate date;

    @JsonProperty("t")
    private int timetableId;
}
