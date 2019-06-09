package com.coding.args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemaFlag {
    public static final String REGEX_SPACE = " ";
    private String schemaDesc;

    public static final Map<String, Object> TYPE_MAP = new HashMap() {
        {
            put("boolean", false);
            put("integer", 0);
            put("string", "");
            put("integer[]", new Integer[]{});
            put("string[]", new String[]{});
        }
    };

    public SchemaFlag(String schemaDesc) {
        this.schemaDesc = schemaDesc;
    }

    public Object getDefaultValue(String flagName) {
        String typeStr = getTypeString(flagName);
        if (TYPE_MAP.get(typeStr) == null)
            throw new IllegalArgumentException(String.format("This %s type is not supported", typeStr));
        return TYPE_MAP.get(typeStr);
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
