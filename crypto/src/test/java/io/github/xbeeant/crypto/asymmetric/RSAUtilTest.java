package io.github.xbeeant.crypto.asymmetric;

import io.github.xbeeant.crypto.Base64Util;
import io.github.xbeeant.crypto.KeyString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.SecureRandom;

/**
 * @author xiaobiao
 * @version 2020/10/7
 */
class RSAUtilTest {
    @Test
    void test() throws Exception {
        // generate public and private keys
        SecureRandom sr = new SecureRandom();
        KeyPair keyPair = RSAUtil.buildKeyPair(2048, sr);

        KeyString keyString = RSAUtil.toKeyString(keyPair);

        // encrypt the message
        byte[] encrypted = RSAUtil.encrypt(keyString.getPublicKey(), "123456");

        // decrypt the message
//        byte[] secret = RSAUtil.decrypt(privateKey, encrypted);
        byte[] secret = RSAUtil.decrypt(keyString.getPrivateKey(), Base64Util.encodeToString(encrypted));
        Assertions.assertEquals("123456", new String(secret));
    }
}