package com.xstudio.crypto.asymmetric;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author xiaobiao
 * @version 2020/10/7
 */
public class RSAUtil {
    public static final String RSA_ALGORITHM = "RSA/None/OAEPWithSHA-1AndMGF1Padding";

    public static final Charset UTF8 = StandardCharsets.UTF_8;

    public static String base64Encode(byte[] data) {
        return new BASE64Encoder().encode(data);
    }

    public static KeyPair buildKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.genKeyPair();
    }

    public static byte[] decrypt(PublicKey publicKey, byte[] encrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(encrypted);
    }

    public static byte[] encrypt(PrivateKey privateKey, String message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(message.getBytes(UTF8));
    }

    /**
     * 加载私钥
     *
     * @param privateKeyStr 私钥str
     * @return {@link RSAPrivateKey}
     * @throws NoSuchAlgorithmException 没有这样的算法异常
     * @throws InvalidKeySpecException  无效的关键规范异常
     * @throws IOException              ioexception
     */
    public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] buffer = base64Decode(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    /**
     * base64解码
     *
     * @param data 数据
     * @return {@link byte[]}
     * @throws IOException ioexception
     */
    public static byte[] base64Decode(String data) throws IOException {
        return new BASE64Decoder().decodeBuffer(data);
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥str
     * @return {@link RSAPublicKey}
     * @throws IOException              ioexception
     * @throws NoSuchAlgorithmException 没有这样的算法异常
     * @throws InvalidKeySpecException  无效的关键规范异常
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] buffer = base64Decode(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    /**
     * 保存私钥
     *
     * @param privateKey 私钥
     * @param path       路径
     * @throws IOException ioexception
     */
    public void savePrivateKey(PrivateKey privateKey, String path) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(privateKey.getEncoded());
            fos.flush();
        }
    }

    /**
     * 保存公钥
     *
     * @param publicKey 公钥
     * @param path      路径
     * @throws IOException ioexception
     */
    public void savePublicKey(PublicKey publicKey, String path) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(publicKey.getEncoded());
            fos.flush();
        }
    }
}
