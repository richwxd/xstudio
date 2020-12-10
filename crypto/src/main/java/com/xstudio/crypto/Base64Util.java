package com.xstudio.crypto;

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
        return Base64.getDecoder().decode(src);
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
        return Base64.getEncoder().encode(src);
    }
}
