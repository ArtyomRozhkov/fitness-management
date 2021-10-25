package com.rozhkov.fitness.management.telegram.action;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RemovedTrainingRecordData {

    String training;
}
