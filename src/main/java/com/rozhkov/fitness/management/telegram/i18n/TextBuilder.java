package com.rozhkov.fitness.management.telegram.i18n;

import com.rozhkov.fitness.management.service.Training;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

// TODO вынести сообщения в MessageSource и добавить локализацию
@RequiredArgsConstructor
public class TextBuilder {

    private final MessageSource messageSource;

    public String createTodayTrainingsText(List<Training> trainings) {
        String trainingList = createTrainingList(trainings);
        return messageSource.getMessage("today.timetable", List.of(System.lineSeparator(), trainingList).toArray(), Locale.ENGLISH);
    }

    public String createTrainingTimetableOnWeekText(Map<DayOfWeek, List<Training>> trainingTimetableOnWeek) {
        return Arrays.stream(DayOfWeek.values())
                .map(dayOfWeek -> createOnDayTrainingRecordsText(dayOfWeek, trainingTimetableOnWeek.get(dayOfWeek)))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String createUserTrainingsText(Map<LocalDate, List<Training>> trainings) {
        return String.format("Вы записаны::%s%s",
                System.lineSeparator(),
                trainings.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .map(dateAndTrainings -> createOnDayTrainingRecordsText(dateAndTrainings.getKey(), dateAndTrainings.getValue()))
                        .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator())));
    }

    public String trainingToString(Training training) {
        return String.format("%s - %s %s",
                training.getTrainingRange().getLowerBound(), training.getTrainingRange().getUpperBound(), training.getTitle());
    }

    public String createSignedUpForTrainingText(LocalDate trainingDate, Training training) {
        return messageSource.getMessage("signed.up.for.training", List.of(dateToString(trainingDate), trainingToString(training)).toArray(), Locale.ENGLISH);
    }

    public String createWelcomeText(User user) {
        return "Привет " + user.getFirstName();
    }

    public String createUnsupportedCommandText() {
        return "Не знаю чем могу помочь. Выберете одно из доступных действий";
    }

    public String createTodayText() {
        return "Сегодня";
    }

    public String createTomorrowText() {
        return "Завтра";
    }

    public String createDateText(LocalDate day) {
        return day.format(DateTimeFormatter.ISO_DATE);
    }

    public String trainingToString(Training training, LocalDate trainingDate) {
        return String.format("%s : %s", createDateText(trainingDate), trainingToString(training));
    }

    public String dateToString(LocalDate date) {
        return date.toString();
    }

    public String createRemoveTrainingRecordText(Training training, LocalDate trainingDate) {
        return String.format("Удалена запись на тренировку %s", trainingToString(training, trainingDate));
    }

    private String createOnDayTrainingRecordsText(DayOfWeek dayOfWeek, List<Training> trainings) {
        String trainingList = createTrainingList(trainings);
        return String.format("Расписание занятий на %s: %s%s",
                dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH), System.lineSeparator(), trainingList);
    }

    private String createOnDayTrainingRecordsText(LocalDate date, List<Training> trainings) {
        String trainingList = createTrainingList(trainings);
        return String.format("%s на: %s%s", date, System.lineSeparator(), trainingList);
    }

    private String createTrainingList(List<Training> trainings) {
        return trainings.stream()
                .map(this::trainingToString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
