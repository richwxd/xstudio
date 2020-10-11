package com.xstudio.crypto.asymmetric;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;

/**
 * @author xiaobiao
 * @version 2020/10/7
 */
class RSAUtilTest {
    @Test
    public void test() throws Exception {


        // generate public and private keys
        KeyPair keyPair = RSAUtil.buildKeyPair(1024);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // encrypt the message
        byte[] encrypted = RSAUtil.encrypt(privateKey, "This is a secret message");
        // decrypt the message
        byte[] secret = RSAUtil.decrypt(publicKey, encrypted);
    }

    private void mybatis(String var0, String var1, String var2) throws Exception {
        String template = "{\"paidKey\":\"%s\",\"valid\":true,\"userMac\":\"%s\",\"validTo\":%d}";
        String json = String.format(template, new Object[]{var0, var1, var2});
        RSAPrivateKey privateKey = RSAUtil.loadPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALEwBB6zCkP1QNEDiJLrvn6JSoCJ8fiOmtyjBixo0ynpQsaFE7ufx1z3f+WBMqC8FRPQa04pJuwTjaRz6enWoIA/vzPMPHrF+8c+GT5u1kdRWL1A8ELENMFaNWUjn+lL14wCCt5Xl2MA1YFebI397I78LQNfuPHXJvmRkNS7y0IPAgMBAAECgYAx0dlmtFqvjvbhjgfJn6VZOMsM5zx08cvTO7dRp0uwXhjbbrGEsMFCf4ijhDczAI9AmfqB6Io8GKdb1QexKxrHW0QHfUeJgrn7NEamp/XbVx8I6jcc1c+4ajNlaX/DyFAvDui9h0YOvGTA/TpUC0GeDZMgJ1Xo85hJCiuujhVFqQJBAPrlyqdL+rz9e2fxW4j6Zwsa2KtacdkbUJooOa1Bo4E9cep9lScnlU+7A6PkGq5UGoE7g8h/FacJ8WqCuMkmrQ0CQQC0ynrBgmPNNNb7WTTJXy9vX2pW35XcmgOKhpG2uwCQLw3IJO6b39j+ga4zTCcLxromNaVJ+2F+r95QwDeDnXyLAkAsXdgxL0FejCB3Z/m8xjLUhuHLo0nAj5D4Qaa5WYMVRo1PXveyHdDMcZCaOaryKtMpCSIroTVt/Vcgc49DmsA9AkB1B0jVMIlYEWdERueDnsj+6B3W5F6G8W9vYVK7nWts6fia4UVPw2Qcw2c3L4lq9xYSm9FGBaKDtmZLkVGeMfinAkEArtq3DkA/4M15a82AYNWwreq2bh2sqnZePHzxDg5AHNHN92Y7maVbUr7Ub5b5+0V7yja8ZqYX7VAgcsUR45B/eQ==");
        try {
            byte[] encrypted = RSAUtil.encrypt(privateKey, json);
            String result = Base64.getEncoder().encodeToString(encrypted);
            (new Thread(() -> {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                File licenseFile = new File(System.getProperty("user.home"), ".config/mybatisCodeHelperPro.data");
                if (!licenseFile.getParentFile().exists())
                    licenseFile.getParentFile().mkdirs();
                try (OutputStream os = new FileOutputStream(licenseFile)) {
                    os.write(result.getBytes(StandardCharsets.UTF_8));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            })).start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}