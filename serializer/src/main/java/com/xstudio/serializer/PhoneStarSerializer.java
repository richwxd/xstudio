package com.xstudio.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 手机号加星序列化，对手机号中间4位进行加星操作<br>
 * 例如： 12345678901 加星后 123****8901<br>
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
public class PhoneStarSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String text = value.replaceAll("(\\d{3})\\d+(\\d{4})", "$1****$2");
        gen.writeString(text);
    }
}
