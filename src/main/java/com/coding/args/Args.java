package com.coding.args;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Args {

    public static final String REGEX_SPACE = " ";
    public static final String FLAG_TARGET = "-";
    public static final String REGEX_ARRAY = ",";
    private final SchemaFlag schemaFlag;
    private final String argsAsText;

    private HashMap<String, Object> flagMap = new HashMap<>();

    public Args(String argsAsText, SchemaFlag schemaFlag) {
        this.argsAsText = argsAsText;
        this.schemaFlag = schemaFlag;
        parse();
    }

    public Object getValue(String flagName) {
        Object tmpValue = flagMap.get(flagName);
        String type = schemaFlag.getType(flagName);
        if (("boolean").equals(type)) {
            return Boolean.valueOf(String.valueOf(tmpValue));
        }
        if (("integer").equals(type)) {
            return Integer.valueOf(String.valueOf(tmpValue));
        }
        if (("string").equals(type)) {
            return String.valueOf(tmpValue);
        }
        if (("string[]").equals(type)) {
            return getStringArrayBySplit(String.valueOf(tmpValue), REGEX_ARRAY);
        }
        if (("integer[]").equals(type)) {
            String[] strings = getStringArrayBySplit(String.valueOf(tmpValue), REGEX_ARRAY);
            int[] ints = Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();
            Integer[] integers = ArrayUtils.toObject(ints);
            return integers;
        }
        return tmpValue;
    }

    private String[] getStringArrayBySplit(String s, String regexArray) {
        return s.split(regexArray);
    }

    public void parse() {
        // TODO 根据 argsAsArray 和 schema 设置值
        String[] argsAsArray = getStringArrayBySplit(argsAsText, FLAG_TARGET);
        for (int i = 1; i < argsAsArray.length; i++) {
            String[] argsValue = getStringArrayBySplit(argsAsArray[i], REGEX_SPACE);
            String flagName = argsValue[0].replace(FLAG_TARGET, "");
            Object flagValue = argsValue.length > 1 ? argsValue[1] : schemaFlag.getDefaultValue(flagName);
            flagMap.put(flagName, flagValue);
        }
    }
}
