package com.rozhkov.fitness.management.service;

import org.springframework.data.domain.Range;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FitnessService {

    public List<Training> getTrainingTimetableOnDate(LocalDate date) {
        return getTestTrainingList();
    }

    public Map<DayOfWeek, List<Training>> getTrainingTimetableOnWeek() {
        return Arrays.stream(DayOfWeek.values())
                .collect(Collectors.toMap(dayOfWeek -> dayOfWeek, dayOfWeek -> this.getTrainingTimetableOnDate(LocalDate.now())));
    }

    private List<Training> getTestTrainingList() {
        Training training1 = Training.builder()
                .timetableId(1)
                .title("hot-iron")
                .trainingRange(Range
                        .from(Range.Bound.inclusive(LocalTime.of(18,0)))
                        .to(Range.Bound.inclusive(LocalTime.of(18, 50))))
                .build();

        Training training2 = Training.builder()
                .timetableId(2)
                .title("йога")
                .trainingRange(Range
                        .from(Range.Bound.inclusive(LocalTime.of(19,0)))
                        .to(Range.Bound.inclusive(LocalTime.of(20, 0))))
                .build();

        Training training3 = Training.builder()
                .timetableId(3)
                .title("cycle")
                .trainingRange(Range
                        .from(Range.Bound.inclusive(LocalTime.of(20,0)))
                        .to(Range.Bound.inclusive(LocalTime.of(21, 0))))
                .build();

        return List.of(training1, training2, training3);
    }

    public Training getTraining(int timetableId) {
        return getTestTrainingList().stream()
                .filter(training -> training.getTimetableId() == timetableId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Training not found"));
    }

    public Map<LocalDate, List<Training>> getUserTrainings(Long userId) {
        return Stream.of(LocalDate.now(), LocalDate.now().plusDays(1), LocalDate.now().plusDays(10))
                .collect(Collectors.toMap(Function.identity(), localDate -> getTestTrainingList()));
    }
}
