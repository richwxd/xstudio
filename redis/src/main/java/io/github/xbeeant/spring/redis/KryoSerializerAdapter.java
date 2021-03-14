package io.github.xbeeant.spring.redis;

import io.github.xbeeant.serializer.KryoSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/02
 */
public class KryoSerializerAdapter<T> implements RedisSerializer<T> {
    @Override
    public byte[] serialize(T t) {
        return KryoSerializer.serialize(t);
    }

    @Override
    public T deserialize(byte[] bytes) {
        return KryoSerializer.deserialize(bytes);
    }
}
