package com.rozhkov.fitness.management.telegram.callback;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rozhkov.fitness.management.telegram.action.Action;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class CallbackData {

    @JsonProperty("a")
    private Action clientAction;
    @JsonProperty("p")
    private Map<String, Object> params;
}
