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
        Object flagValue = flagMap.get(flagName);
        String type = schemaFlag.getType(flagName);
        
        Object realValue = ArgsValueServiceFactory.getArgsValueServiceStrategy(type).getArgsValue(flagValue.toString());

        return realValue;
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
