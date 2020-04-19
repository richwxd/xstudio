package com.xstudio.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 手机号加星序列化，对手机号中间4位进行加星操作
 *
 * @author xiaobiao
 * @version 2020/2/2
 */
public class PhoneStarSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        String value = (String) object;
        String text = value.replaceAll("(\\d{3})\\d+(\\d{4})", "$1****$2");
        serializer.write(text);
    }
}
