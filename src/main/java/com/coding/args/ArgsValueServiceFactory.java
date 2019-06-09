package com.coding.args;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArgsValueServiceFactory {

    public static Map<String, ArgsValueService> argsValueServieMap = new HashMap<>();

    public static final String REGEX_ARRAY = ",";

    static {
        argsValueServieMap.put("boolean", (flagValue) -> Boolean.valueOf(String.valueOf(flagValue)));
        argsValueServieMap.put("integer", (flagValue) -> Integer.valueOf(String.valueOf(flagValue)));
        argsValueServieMap.put("string", (flagValue) -> String.valueOf(flagValue));
        argsValueServieMap.put("string[]", (flagValue) -> {
            String[] strings =  getStringArrayBySplit(String.valueOf(flagValue), REGEX_ARRAY);
            return strings;
        });

        argsValueServieMap.put("integer[]", (flagValue) -> {
            String[] strings = getStringArrayBySplit(String.valueOf(flagValue), REGEX_ARRAY);
            int[] ints = Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();
            Integer[] integers = ArrayUtils.toObject(ints);
            return integers;
        });

    }

    public static ArgsValueService getArgsValueServiceStrategy(String flagName) {
        return argsValueServieMap.get(flagName);
    }

    private static String[] getStringArrayBySplit(String s, String regexArray) {
        return s.split(regexArray);
    }
}
