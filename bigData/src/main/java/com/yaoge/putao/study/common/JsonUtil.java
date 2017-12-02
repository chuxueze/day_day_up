package com.yaoge.putao.study.common;

public class JsonUtil {

    public  static JsonMapper jsonMapper = new JsonMapper();

    public static String toJson(Object object) {
        return null == object ? null : jsonMapper.writeValueAsString(object);
    }
}
