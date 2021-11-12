package com.rozhkov.fitness.management.telegram.action;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// TODO сделать явную привязку Action и классов CallbackData
@RequiredArgsConstructor
@Getter
public enum Action {

    START("/start"),
    SHOW_TODAY_TIMETABLE("Покажи расписание на сегодня"),
    SHOW_WEEK_TIMETABLE("Покажи расписание на всю неделю"),
    SING_UP_FOR_TRAINING("Хочу записаться на тренировку"),
    SHOW_MY_TRAINING_RECORDS("Покажи мои тренировки"),
    CHOOSE_DATE_FOR_TRAINING("На какой день хочешь записаться?"),
    CHOSEN_DATE_FOR_TRAINING(""),
    CHOOSE_TRAINING("Выбирете тренирувку"),
    CHOSEN_TRAINING(""),
    REMOVE_TRAINING_RECORD("Удалить запись"),
    REMOVED_TRAINING_RECORD("Выбирете тренировку, на которую не придете");

    private final String caption;

    @JsonValue
    public int id() {
        return ordinal();
    }
}
