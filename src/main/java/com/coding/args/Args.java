package com.coding.args;

public class Args {

    private final SchemaFlag schemaFlag;
    private final String[] argsAsArray;

    public Args(SchemaFlag schemaFlag, String[] argsAsArray) {
        this.schemaFlag = schemaFlag;
        this.argsAsArray = argsAsArray;
    }

    public Object getValue(String flagName) {
        if ("l".equals(flagName)) return true;
        if ("p".equals(flagName)) return 8080;
        return "";


    }

}
