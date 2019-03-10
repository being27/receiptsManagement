package com.tt.oa.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonStringDeal {
    public static String toJsonString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static String toAppendDepartment(String jsonString, Object object) {
        StringBuilder stringBuilder = new StringBuilder(jsonString.substring(0, jsonString.length() - 1));
        stringBuilder.append(",");
        stringBuilder.append(toJsonString(object));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
