package com.coding.args;

import java.util.HashMap;

public class Args {

    public static final String REGEX_SPACE = " ";
    public static final String FLAG_TARGET = "-";
    private final SchemaFlag schemaFlag;
    private final String[] argsAsArray;

    private HashMap<String, Object> flagMap = new HashMap<>();

    public Args(String[] argsAsArray, SchemaFlag schemaFlag) {
        this.argsAsArray = argsAsArray;
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
        return tmpValue;
    }

    public void parse() {
        // TODO 根据 argsAsArray 和 schema 设置值
        String[] argsValue = argsAsArray[schemaFlag.getSize() - 1].split(REGEX_SPACE);
        String flagName = argsValue[0].replace(FLAG_TARGET, "");
        Object flagValue = argsValue.length > 1 ? argsValue[1] : schemaFlag.getDefaultValue(flagName);
        flagMap.put(flagName, flagValue);
    }
}
