package com.xstudio.crypto.asymmetric;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author xiaobiao
 * @version 2020/10/7
 */
class RSAUtilTest {
    @Test
    void test() throws Exception {
        // generate public and private keys
        KeyPair keyPair = RSAUtil.buildKeyPair(1024);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // encrypt the message
        byte[] encrypted = RSAUtil.encrypt(publicKey, "This is a secret message");
        // decrypt the message
        byte[] secret = RSAUtil.decrypt(privateKey, encrypted);
        Assertions.assertNotNull(publicKey);
    }
}