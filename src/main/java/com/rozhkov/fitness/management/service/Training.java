package com.rozhkov.fitness.management.service;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Range;

import java.time.LocalTime;

@Value
@Builder
public class Training {

    int timetableId;
    String title;
    Range<LocalTime> trainingRange;
}
