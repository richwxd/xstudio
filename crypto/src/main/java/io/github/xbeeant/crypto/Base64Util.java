package io.github.xbeeant.crypto;

import java.util.Base64;

/**
 * base64工具类
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/11
 */
public class Base64Util {
    private Base64Util() {
        throw new IllegalStateException("Utility class");
    }

    private static final Base64.Encoder ENCODER = Base64.getEncoder();

    private static final Base64.Decoder DECODER = Base64.getDecoder();

    /**
     * 解密
     *
     * @param src src
     * @return {@link byte[]}
     */
    public static byte[] decode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return DECODER.decode(src);
    }

    /**
     * 编码
     *
     * @param src src
     * @return {@link byte[]}
     */
    public static byte[] encode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return ENCODER.encode(src);
    }

    /**
     * 编码
     *
     * @param src src
     * @return {@link byte[]}
     */
    public static String encodeToString(byte[] src) {
        if (src.length == 0) {
            return "";
        }
        return ENCODER.encodeToString(src);
    }

    /**
     * base64解码
     *
     * @param data 数据
     * @return {@link byte[]}
     */
    public static byte[] base64StringDecode(String data) {
        return DECODER.decode(data);
    }
}
