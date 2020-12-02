package com.xstudio.spring.redis;

import com.xstudio.serializer.KryoSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/02
 */
public class KryoSerializerAdapter<T> implements RedisSerializer<T> {
    @Override
    public byte[] serialize(T t) throws SerializationException {
        return KryoSerializer.serialize(t);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return KryoSerializer.deserialize(bytes);
    }
}
