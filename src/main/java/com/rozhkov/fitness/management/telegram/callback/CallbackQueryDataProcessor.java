package com.rozhkov.fitness.management.telegram.callback;

import com.rozhkov.fitness.management.telegram.Action;
import org.springframework.util.StringUtils;

public class CallbackQueryDataProcessor {

    private static final String ACTION_AND_DATA_PARAMS_SEPARATOR = "#&";
    private static final String DATA_PARAMS_SEPARATOR = "@&";

    public Action parseAction(String data) {
        int indexOfSeparator = data.indexOf(ACTION_AND_DATA_PARAMS_SEPARATOR);
        if (indexOfSeparator == -1) {
            return Action.valueOf(data);
        }
        return Action.valueOf(data.substring(0, indexOfSeparator));
    }

    public String[] parseDataParams(String data) {
        int indexOfSeparator = data.indexOf(ACTION_AND_DATA_PARAMS_SEPARATOR);
        if (indexOfSeparator == -1) {
            return new String[] {};
        }
        return data.substring(indexOfSeparator + ACTION_AND_DATA_PARAMS_SEPARATOR.length()).split(DATA_PARAMS_SEPARATOR);
    }

    public String createCallbackQueryData(Action action, String... dataParams) {
        if (dataParams.length == 0) {
            return action.toString();
        }
        return action.toString() + ACTION_AND_DATA_PARAMS_SEPARATOR +
                StringUtils.arrayToDelimitedString(dataParams, DATA_PARAMS_SEPARATOR);
    }
}
