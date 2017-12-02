package com.yaoge.putao.study.common;

import com.yaoge.putao.study.exception.JsonException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonMapper {
    public final ObjectMapper objectMapper;

    public JsonMapper() {
        this.objectMapper=new ObjectMapper();
    }

    public String writeValueAsString(Object value) {
        try {
            return this.objectMapper.writeValueAsString(value);
        } catch (Exception var3) {
            throw new JsonException(var3.getMessage(), var3);
        }
    }

}
