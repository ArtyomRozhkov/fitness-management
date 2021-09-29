package com.rozhkov.fitness.management.telegram;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Action {

    START("/start"),
    SHOW_TODAY_TIMETABLE("Покажи расписание на сегодня"),
    SHOW_WEEK_TIMETABLE("Покажи расписание на всю неделю"),
    SING_UP_FOR_TRAINING("Хочу записаться на тренировку"),
    SHOW_MY_TRAINING_RECORDS("Покажи мои тренировки"),
    CHOOSE_DATE_FOR_TRAINING("На какой день хочешь записаться?"),
    CHOSEN_DATE_FOR_TRAINING("На какой день хочешь записаться?"),
    CHOOSE_TRAINING("Выбирете тренирувку"),
    CHOSEN_TRAINING("Выбирете тренирувку"),
    REMOVE_TRAINING_RECORD("Выбирете тренировку, на которую не придете"),
    REMOVED_TRAINING_RECORD("Выбирете тренировку на которую хотите удалить запись");

    private final String caption;
}
