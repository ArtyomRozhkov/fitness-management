package com.rozhkov.fitness.management.message.telegram;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Action {

    SHOW_TODAY_TIMETABLE("Покажи расписание на сегодня"),
    SHOW_TIMETABLE_ON_DATE("Покажи расписание на другую дату"),
    SING_UP_FOR_TRAINING("Хочу записаться на тренировку"),
    REMOVE_TRAINING_RECORD("Хочу удалить мою запись на тренировку"),
    CHOOSE_DATE_FOR_TRAINING("На какой день хочешь записаться?"),
    CHOOSE_DATE_FOR_TIMETABLE("Выбери день для просмотра расписания"),
    CHOOSE_TRAINING("Выбирете тренирувку"),
    CHOOSE_DATE_FOR_REMOVE("На какую дату удалить запись?"),
    REMOVE_TRAINING("Выбирете тренировку на которую хотите удалить запись");

    private final String caption;
}
