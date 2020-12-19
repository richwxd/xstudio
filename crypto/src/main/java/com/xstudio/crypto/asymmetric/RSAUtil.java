package com.xstudio.crypto.asymmetric;

import com.xstudio.crypto.Base64Util;
import com.xstudio.crypto.KeyString;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 该算法于1977年由美国麻省理工学院MIT(Massachusetts Institute of Technology)的Ronal Rivest，Adi Shamir和Len Adleman三位年轻教授提出，并以三人的姓氏Rivest，Shamir和Adlernan命名为RSA算法，是一个支持变长密钥的公共密钥算法，需要加密的文件快的长度也是可变的!
 * 所谓RSA加密算法，是世界上第一个非对称加密算法，也是数论的第一个实际应用。它的算法如下：
 * 1.找两个非常大的质数p和q（通常p和q都有155十进制位或都有512十进制位）并计算n=pq，k=(p-1)(q-1)。
 * 2.将明文编码成整数M，保证M不小于0但是小于n。
 * 3.任取一个整数e，保证e和k互质，而且e不小于0但是小于k。加密钥匙（称作公钥）是(e, n)。
 * 4.找到一个整数d，使得ed除以k的余数是1（只要e和n满足上面条件，d肯定存在）。解密钥匙（称作密钥）是(d, n)。
 * 加密过程： 加密后的编码C等于M的e次方除以n所得的余数。
 * 解密过程： 解密后的编码N等于C的d次方除以n所得的余数。
 * 只要e、d和n满足上面给定的条件。M等于N。
 *
 * @author xiaobiao
 * @version 2020/10/7
 */
public class RSAUtil {
    public static final String RSA_ALGORITHM = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";

    public static final Charset UTF8 = StandardCharsets.UTF_8;

    private static final Base64.Decoder DECODER = Base64.getDecoder();

    private RSAUtil() {
    }

    /**
     * 建立密钥对
     *
     * @param keySize 关键尺寸
     * @return {@link KeyPair}
     * @throws NoSuchAlgorithmException 没有这样的算法异常
     */
    public static KeyPair buildKeyPair(int keySize) throws NoSuchAlgorithmException {
        /* RSA算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();
        /* 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(AsymmetricAlgorithm.RSA.getValue());
        /* 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        kpg.initialize(keySize, sr);
        /* 生成密匙对 */
        return kpg.generateKeyPair();
    }

    /**
     * 解密
     *
     * @param privateKey 私钥
     * @param encrypted  加密
     * @return {@link byte[]}
     * @throws NoSuchAlgorithmException  没有这样的算法异常
     * @throws InvalidKeyException       无效的关键例外
     * @throws BadPaddingException       坏填充例外
     * @throws IllegalBlockSizeException 非法的块大小异常
     * @throws InvalidKeySpecException   无效的关键规范异常
     * @throws NoSuchPaddingException    没有这样的填充例外
     */
    public static byte[] decrypt(String privateKey, String encrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        PrivateKey key = getPrivateKey(privateKey);
        return decrypt(key, Base64Util.decode(encrypted.getBytes()));
    }

    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @return {@link PrivateKey}
     * @throws NoSuchAlgorithmException 没有这样的算法异常
     * @throws InvalidKeySpecException  无效的关键规范异常
     */
    public static PrivateKey getPrivateKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64Util.decode(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance(AsymmetricAlgorithm.RSA.getValue());
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 解密
     *
     * @param privateKey 私钥
     * @param encrypted  加密
     * @return {@link byte[]}
     * @throws NoSuchAlgorithmException  没有这样的算法异常
     * @throws InvalidKeyException       无效的关键例外
     * @throws BadPaddingException       坏填充例外
     * @throws IllegalBlockSizeException 非法的块大小异常
     * @throws NoSuchPaddingException    没有这样的填充例外
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] encrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(encrypted);
    }

    public static byte[] encrypt(String publickKey, String message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        PublicKey key = getPublicKey(publickKey);
        return encrypt(key, message);
    }

    /**
     * 得到公钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @return {@link PublicKey}
     * @throws NoSuchAlgorithmException 没有这样的算法异常
     * @throws InvalidKeySpecException  无效的关键规范异常
     */
    public static PublicKey getPublicKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64Util.decode(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance(AsymmetricAlgorithm.RSA.getValue());
        return keyFactory.generatePublic(keySpec);
    }

    public static byte[] encrypt(PublicKey publickKey, String message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publickKey);

        return cipher.doFinal(message.getBytes(UTF8));
    }

    /**
     * 加载私钥
     *
     * @param privateKeyStr 私钥str
     * @return {@link RSAPrivateKey}
     * @throws NoSuchAlgorithmException 没有这样的算法异常
     * @throws InvalidKeySpecException  无效的关键规范异常
     */
    public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
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
     */
    public static byte[] base64Decode(String data) {
        return DECODER.decode(data);
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥str
     * @return {@link RSAPublicKey}
     * @throws NoSuchAlgorithmException 没有这样的算法异常
     * @throws InvalidKeySpecException  无效的关键规范异常
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] buffer = base64Decode(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public static KeyString toKeyString(KeyPair keyPair) {
        /* 得到公钥 */
        Key publicKey = keyPair.getPublic();
        byte[] publicKeyBytes = publicKey.getEncoded();
        String pub = new String(Base64Util.encode(publicKeyBytes), StandardCharsets.UTF_8);
        /* 得到私钥 */
        Key privateKey = keyPair.getPrivate();
        byte[] privateKeyBytes = privateKey.getEncoded();
        String pri = new String(Base64Util.encode(privateKeyBytes), StandardCharsets.UTF_8);

        RSAPublicKey rsp = (RSAPublicKey) keyPair.getPublic();
        BigInteger bint = rsp.getModulus();
        byte[] b = bint.toByteArray();
        byte[] deBase64Value = Base64.getEncoder().encode(b);
        String retValue = new String(deBase64Value);

        return new KeyString(pub, pri, retValue);
    }
}
