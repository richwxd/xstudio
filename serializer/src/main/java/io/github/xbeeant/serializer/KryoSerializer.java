package io.github.xbeeant.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;

import java.io.ByteArrayOutputStream;

/**
 * kryo serializer
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/02
 */
public class KryoSerializer {
    static Pool<Kryo> pool = initialKryoPool();

    /**
     * 反序列化
     *
     * @param dataStream 数据流
     * @param <T>        泛型
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(final byte[] dataStream) {
        if (null == dataStream) {
            return null;
        }
        Kryo kryoPoolElement = pool.obtain();
        T deserializedObject = (T) kryoPoolElement.readClassAndObject(new Input(dataStream));
        pool.free(kryoPoolElement);
        return deserializedObject;
    }

    private static Pool<Kryo> initialKryoPool() {
        return new Pool<Kryo>(true, false, 8) {
            @Override
            protected Kryo create() {
                return new Kryo();
            }
        };
    }

    /**
     * 序列化
     *
     * @param object 对象
     * @return {@link byte[]}
     */
    public static byte[] serialize(final Object object) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Output output = new Output(stream);
        Kryo kryoPoolElement = pool.obtain();
        kryoPoolElement.register(object.getClass());
        kryoPoolElement.writeClassAndObject(output, object);
        output.close();
        pool.free(kryoPoolElement);
        return stream.toByteArray();
    }
}
