package com.xstudio.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiaobiao
 * @version 2020/2/2
 */
public class IdCardValueSerializerTest {

    @Test
    public void write() {
        IdCardValueSerializer idCardValueSerializer  = new IdCardValueSerializer();
        JSONSerializer jsonSerializer = new JSONSerializer();
        String idcard = "350948271636253716";
        idCardValueSerializer.write(jsonSerializer, idcard,null,null,0);

        Assert.assertEquals("\"35************3716\"", jsonSerializer.toString());
    }
}