package com.first.family.rmq.utli;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/11/8 14:34
 */
public class JsonUtil {

    private JsonUtil() {
    }

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串
     *
     * @param data obj
     * @return json str
     */
    public static String toJson(Object data) throws JsonProcessingException {
        if (Objects.isNull(data)) {
            return Strings.EMPTY;
        }
        return JSON_MAPPER.writeValueAsString(data);
    }

    /**
     * 将json字符串转化为对象
     *
     * @param jsonData json数据
     * @param clazz    对象中的object类型
     * @return 对象
     */
    public static <T> T parseObject(String jsonData, Class<T> clazz) throws JsonProcessingException {
        return JSON_MAPPER.readValue(jsonData, clazz);
    }

    /**
     * 将json字符串转化为集合
     *
     * @param jsonData      json数据
     * @param typeReference 集合中的object类型
     * @return 集合
     */
    public static <T> T parseObject(String jsonData, TypeReference<T> typeReference) throws JsonProcessingException {
        if (jsonData == null) {
            return null;
        }
        return JSON_MAPPER.readValue(jsonData, typeReference);
    }

    public static JsonNode toJsonNode(String jsonData) throws JsonProcessingException {
        return JSON_MAPPER.readTree(jsonData);
    }

}
