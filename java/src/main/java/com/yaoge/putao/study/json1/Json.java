package com.yaoge.putao.study.json1;


import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Json {
    private static ObjectMapper mapper = new ObjectMapper();
    private static JsonFactory jsonFactory=new JsonFactory();

    @Test
    public void json_1() throws IOException {
        People p = new People();
        p.setName("luyao");
        p.setAge(23);
        p.setAddr("吉林省榆树市");
        //json数据反序列化到对象
        String jsonString = "{\"name\":\"luyao2\", \"age\":23, \"addr\":\"吉林长春\"}";
        People p2 = mapper.readValue(jsonString, People.class);
        //System.out.println(p2);

        //mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        //序列化对象到json
        String jsonString2 = mapper.writeValueAsString(p2);
        //System.out.println(jsonString2);
        mapper.writeValue(new File("/Users/luyao/codePlay/day_day_up/java/src/main/java/com/yaoge/putao/study/json1/data.json"), p2);
        People p3 = mapper.readValue(new File("/Users/luyao/codePlay/day_day_up/java/src/main/java/com/yaoge/putao/study/json1/data.json"), People.class);
        System.out.println(p3);

    }

    @Test
    public void json_2() throws IOException {
        Map<String, Object> map = new HashMap<>();
        int[] marks = {1, 2, 3};
        String alias = "yaoge";
        People p = new People();
        p.setName("luyao");
        p.setAge(23);
        p.setAddr("吉林省榆树市");
        map.put("student", p);

        map.put("mark", marks);
        map.put("alias", alias);

//        map.put("verified", Boolean.FALSE);
//        map.put("marks", marks);
        mapper.writeValue(new File("/Users/luyao/codePlay/day_day_up/java/src/main/java/com/yaoge/putao/study/json1/data.json"), map);
        map = mapper.readValue(new File("/Users/luyao/codePlay/day_day_up/java/src/main/java/com/yaoge/putao/study/json1/data.json"), Map.class);
        System.out.println(mapper.writeValueAsString(map));
    }

    @Test
    public void json_3() throws IOException{
        String jsonString = "{\"name\":\"Mahesh Kumar\", \"age\":21,\"verified\":false,\"marks\": [100,90,85]}";
        JsonNode rootNode = mapper.readTree(jsonString);
        System.out.println(rootNode.get("name").asText());
//        System.out.println(rootNode.get("age").intValue());
        rootNode.get("marks").forEach(x ->System.out.println(x.asInt()));

    }

    @Test
    public void json_4() throws IOException{
        JsonNodeFactory jsonNodeFactory= JsonNodeFactory.instance;
        ObjectNode rootNode=jsonNodeFactory.objectNode();
        rootNode.put("name","luyao");
        rootNode.put("age",32);
        ArrayNode arrayNode = jsonNodeFactory.arrayNode();
        arrayNode.add(10);
        arrayNode.add(90);
        arrayNode.add(85);
        rootNode.put("mark",arrayNode);
        System.out.println(mapper.writeValueAsString(rootNode));

    }

    @Test
    public void json_5() throws IOException{
        JsonGenerator jsonGenerator=jsonFactory.createGenerator(new File("/Users/luyao/codePlay/day_day_up/java/src/main/java/com/yaoge/putao/study/json1/data.json"), JsonEncoding.UTF8);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", "Mahesh Kumar");
        jsonGenerator.writeNumberField("age", 21);
        jsonGenerator.writeBooleanField("verified", false);
        jsonGenerator.writeFieldName("marks");
        jsonGenerator.writeStartArray();
        jsonGenerator.writeNumber(100);
        jsonGenerator.writeNumber(90);
        jsonGenerator.writeNumber(85);
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
        jsonGenerator.close();

    }

    @Test
    public void json_6() throws IOException{
        JsonParser jsonParser = jsonFactory.createParser(new File("/Users/luyao/codePlay/day_day_up/java/src/main/java/com/yaoge/putao/study/json1/data.json"));
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            //get the current token
            String fieldname = jsonParser.getCurrentName();
            if ("name".equals(fieldname)) {
                jsonParser.nextToken();
                System.out.println(jsonParser.getText());
            }
        }
    }
}
