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

// TODO добавить локализацию
@RequiredArgsConstructor
public class TextBuilder {

    private final MessageSource messageSource;
    private final Locale locale = new Locale("ru");

    public String createTodayTrainingsText(List<Training> trainings) {
        String trainingList = createTrainingList(trainings);
        return messageSource.getMessage("today.timetable", List.of(System.lineSeparator(), trainingList).toArray(), locale);
    }

    public String createTrainingTimetableOnWeekText(Map<DayOfWeek, List<Training>> trainingTimetableOnWeek) {
        return Arrays.stream(DayOfWeek.values())
                .map(dayOfWeek -> createOnDayTrainingRecordsText(dayOfWeek, trainingTimetableOnWeek.get(dayOfWeek)))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String createUserTrainingsText(Map<LocalDate, List<Training>> trainings) {
        String trainingList = trainings.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(dateAndTrainings -> createOnDayTrainingRecordsText(dateAndTrainings.getKey(), dateAndTrainings.getValue()))
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));

        return messageSource.getMessage(
                "recorded.for.trainings",
                List.of(System.lineSeparator(), trainingList).toArray(),
                locale);
    }

    public String trainingToString(Training training) {
        return String.format("%s - %s %s",
                training.getTrainingRange().getLowerBound(), training.getTrainingRange().getUpperBound(), training.getTitle());
    }

    public String createRecordedForTrainingText(LocalDate trainingDate, Training training) {
        return messageSource.getMessage("recorded.for.training", List.of(createDateText(trainingDate), trainingToString(training)).toArray(), locale);
    }

    public String createWelcomeText(User user) {
        return messageSource.getMessage("welcome", List.of(user.getFirstName()).toArray(), locale);
    }

    public String createUnsupportedCommandText() {
        return messageSource.getMessage("unsupported.command", null, locale);
    }

    public String createTodayText() {
        return messageSource.getMessage("today", null, locale);
    }

    public String createTomorrowText() {
        return messageSource.getMessage("tomorrow", null, locale);
    }

    public String createDateText(LocalDate day) {
        return day.format(DateTimeFormatter.ISO_DATE);
    }

    public String trainingToString(Training training, LocalDate trainingDate) {
        return String.format("%s : %s", createDateText(trainingDate), trainingToString(training));
    }

    public String createRemoveTrainingRecordText(Training training, LocalDate trainingDate) {
        return messageSource.getMessage("removed.training.record", List.of(trainingToString(training, trainingDate)).toArray(), locale);
    }

    private String createOnDayTrainingRecordsText(DayOfWeek dayOfWeek, List<Training> trainings) {
        String trainingList = createTrainingList(trainings);
        return String.format("%s: %s%s",
                dayOfWeek.getDisplayName(TextStyle.FULL, locale), System.lineSeparator(), trainingList);
    }

    private String createOnDayTrainingRecordsText(LocalDate date, List<Training> trainings) {
        String trainingList = createTrainingList(trainings);
        return String.format("%s: %s%s", date, System.lineSeparator(), trainingList);
    }

    private String createTrainingList(List<Training> trainings) {
        return trainings.stream()
                .map(this::trainingToString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
