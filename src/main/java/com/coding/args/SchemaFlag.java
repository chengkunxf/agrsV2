package com.coding.args;

import java.util.Arrays;
import java.util.List;

public class SchemaFlag {
    public static final String REGEX_SPACE = " ";
    private String schemaDesc;

    public SchemaFlag(String schemaDesc) {
        this.schemaDesc = schemaDesc;
    }

    public Object getDefaultValue(String flagName) {
        String typeStr = getTypeString(flagName);
        if ("boolean".equals(typeStr.toLowerCase())) {
            return false;
        }
        if ("integer".equals(typeStr.toLowerCase())) {
            return 0;
        }
        if ("string".equals(typeStr.toLowerCase())) {
            return "";
        }
        return String.format("This %s type is not supported", typeStr);
    }

    private String getTypeString(String flagName) {
        List<String> schemaList = Arrays.asList(schemaDesc.split(REGEX_SPACE));
        String oneSchema = schemaList.stream().filter(e -> e.startsWith(flagName)).findAny().get();
        return oneSchema.split(":")[1];
    }

    public String getType(String flagName) {
        return getTypeString(flagName);
    }

    public int getSize() {
        List<String> schemaList = Arrays.asList(schemaDesc.split(REGEX_SPACE));
        return schemaList.size();
    }
}
