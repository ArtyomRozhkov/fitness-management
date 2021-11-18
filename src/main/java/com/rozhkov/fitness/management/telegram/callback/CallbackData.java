package com.rozhkov.fitness.management.telegram.callback;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rozhkov.fitness.management.telegram.action.Action;
import com.rozhkov.fitness.management.telegram.action.ActionData;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * Метаданные, сохраненные у кнопки
 *
 * Размер метаданных для кнопок в telegram ограничен 64 символами. Поэтому при сериализации/десериализации используются
 * сокращенные названия полей (см. {@link JsonProperty})
 */
@Data
@Accessors(chain = true)
public class CallbackData implements ActionData {

    private Action action;
    private Map<String, Object> params = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getParams() {
        return params;
    }

    @JsonAnySetter
    public void add(String key, String value) {
        params.put(key, value);
    }
}
