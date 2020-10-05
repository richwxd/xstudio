package com.xstudio.core;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * json工具类
 *
 * @author beeant
 * @version 2020/09/14
 */
public class JsonUtil {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // serialize config
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        // deserialize config
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 属性为 空（“”） 或者为 NULL 都不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

    }

    private JsonUtil() {
    }

    /**
     * 对象转字符串
     *
     * @param obj 对象
     * @return JSON字符串
     */
    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("encode(Object)", e);
        }
        return null;
    }


    public static <T> T toObject(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            logger.error("encode(Object)", e);
        }
        return null;
    }


    public static <T> T toObject(String json, TypeReference<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            logger.error("encode(Object)", e);
        }
        return null;
    }

    /**
     * json转Map
     *
     * @param json json
     * @return Map&lt;String, Object&gt;
     */
    public static Map<String, Object> toMap(String json) {
        return toMap(json, false);
    }

    /**
     * json转Map
     *
     * @param json      json
     * @param recursion 是否开启递归解析，true: 如果map内部的元素存在jsonString，继续解析
     * @return Map&lt;String, Object&gt;
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String json, Boolean recursion) {
        try {
            Map<String, Object> map = objectMapper.readValue(json, Map.class);
            if (recursion) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    Object obj = entry.getValue();
                    if (obj instanceof String) {
                        String str = ((String) obj);
                        if (str.startsWith("[")) {
                            List<?> list = toList(str);
                            map.put(entry.getKey(), list);
                        } else if (str.startsWith("{")) {
                            Map<String, Object> mapRecursion = toMap(str);
                            map.put(entry.getKey(), mapRecursion);
                        }
                    }
                }
            }
            return map;
        } catch (JsonProcessingException e) {
            logger.error("encode(Object)", e);
        }
        return null;
    }

    /**
     * 把json解析成list
     *
     * @param json json
     * @return List&lt;Object&gt;
     */
    public static List<Object> toList(String json) {
        return toList(json, false);
    }

    /**
     * 把json解析成list
     *
     * @param json      json
     * @param recursion 是否开启递归解析，true: 如果list内部的元素存在jsonString，继续解析
     * @return List&lt;Object&gt;
     */
    @SuppressWarnings("unchecked")
    public static List<Object> toList(String json, Boolean recursion) {
        try {
            List<Object> list = objectMapper.readValue(json, List.class);
            if (recursion) {
                for (Object obj : list) {
                    if (obj instanceof String) {
                        String str = (String) obj;
                        if (str.startsWith("[")) {
                            obj = toList(str);
                        } else if (obj.toString().startsWith("{")) {
                            obj = toList(str);
                        }
                    }
                }
            }
            return list;
        } catch (JsonProcessingException e) {
            logger.error("encode(Object)", e);
        }
        return null;
    }
}
