package com.xstudio.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字替换为 <code>*</code> 号, 只保留前面一位和最后一位
 * 例如：
 * 123456 =&gt; 1****6
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
public class NumberToStarsSerializer extends JsonSerializer<String> {
    /**
     * 大部分敏感词汇在10个以内，直接返回缓存的字符串
     */
    private static final String[] STAR_ARR = {"*", "**", "***", "****", "*****", "******", "*******", "********", "*********", "**********"};

    /**
     * 提取文案中的数字正则表达式
     */
    private static final Pattern PATTERN = Pattern.compile(".*?(\\d+).*?");

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Matcher matcher = PATTERN.matcher(value);
        int length = value.length();
        if (matcher.find()) {
            length = matcher.group(1).length();
        }
        String text = value.replaceAll("(\\d{1})\\d+(\\d{1})", "$1" + getStarChar(length - 2) + "$2");

        gen.writeStartObject();
        gen.writeString(text);
        gen.writeEndObject();
    }

    /**
     * 生成n个星号的字符串
     *
     * @param length 字符串长度
     * @return 星星符号字符串
     */
    public static String getStarChar(int length) {
        if (length <= 0) {
            return "";
        }
        // 大部分敏感词汇在10个以内，直接返回缓存的字符串
        if (length <= 10) {
            return STAR_ARR[length - 1];
        }

        // 生成n个星号的字符串
        char[] arr = new char[length];
        for (int i = 0; i < length; i++) {
            arr[i] = '*';
        }
        return new String(arr);
    }
}
