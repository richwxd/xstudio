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

    @Test
    void testJs() throws Exception {
        String data = "f4qukiE3OlUJSMZNEjZzuYGvTX+FolgK+aIM9E2ZjhtJb5Hm0f801bkfkPUrBUEc5EyyW3SFWJ1uTxj6fZLliwkuBvbdCByyUxDJ2yss6gom0vU3n+upW3FUHmC4R14TC5LyGJCKhqrvuo+rVGHTaNQ0zoSgSq1DPRjsLxUZ6Ql6wyAg1oP08JjghlJqmBjSt/snH5a0cg68l+1H6M8WVylhCZ1YeFNHCfTxtUw9THE80bJQg06zzn4g51OgVOMH3RPhk9kRZbKjn5ogNRD6b4Zi8NIcABEx5s+ZFW+JBfL8T9L79kKb1LOyY0bp3QiRwwreOaz2s2ppwJp0wGsf3g==";
        String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCK013NFF13NmCGroVyyfyZ6anHfLXJ+8FeBSwdCHHrS+bbgRRENVJ2YlVD7TVbiuTZNrfbU6XpfpD8sZLl59SJRc+UoFzOnct0ZbTsJLKNrO8gFZc4SdUqDxtJVTMZ5ztU/PZVNHxvu8GcKNtC6tl1PLFe6kjDsy8VrtE+e6+snar9/MZWqvHI7NR4mRPLSRXakFhEdZ6cTomLinbBKJ2VIdCJ9JKy8W/BUBOzrYXodFVaZoTSHCVRkYVsLwEnn6Q2pFd/jT3P/4z1XN/IB5/0VXyYiG7OF00gp1yPrpjJjJ8ScyzjkrvLd5hGsfDqBV2FLLUACz/THHjsf9mAEVq/AgMBAAECggEAcSkb4+g+y8byZbZqpwgItLkoIKkCUfO6SlZy//N7UK5dLv4HtBW28zLyh90aylTseDbHJawIqMz3iFODB46yy3axX1hcbjtntaiWEotTVHLrtL6EtmhQrukDeAJQFkRLM9rDPO78o+NrTUxsdh6TRaJEfrcAuy0bK1SF0aXmrA/dp2lCYSTSF46x4+eJGgoW3mwL+u74tlufeX68rBzpGAHBKHjmIXHKmeXQB+XkBbuU08NQWz+06Vq4CJuX18PWuqufJw42VIvvwavs5NbrKhuNsbKrKNvGCRU3NwH5ZnSqR5vjPSTU6f2yNK0LIz9Q5jf80X9P3/n/N6eK9lLBaQKBgQDfV3H/DV3x0CYM2Y0inzDmR7fqkzVqUUkoDeWq0sUpUq3dxJO/x3OjKs8/xwq5UPbKZQxmpZl6OtYprlIyhv5IiK1ERe4Etjl8PPMS4WnLA+oCjLWY742WqVmLiXz65qml1DEHIymJFClfl2xXBwtCR5K7W6glzfFM95uvUuaoUwKBgQCfICgyJ03zz/aGvh3iPfVmdwXTvtsaDMAlIO8vFfMQQM9ZAoaC1RCMUn3PI28qGPOVoPn32AF9t3iVQ8VGkVrDU3C0bwOY1YXjQ6BAJdIb6kcWGH4BheOG+NQ9KYQBeV+TKjUUf/OlpXickePixf8wrZMi+gbKCJJYXevfUIUGZQKBgGU7FH9/hopZRzXh0bfUoexuq20WhkqHR3pxOh1AZX9Ca24ucK2ncMGPYNrOlAzFJuc7/+/ytWsSigngan9ecplb4fUlISpfkhS+54oyQ6pyProjHxiP1ARrBBxcGBr9O/3e/M34YRYx3kLmtoOHvNGw8VAczk08WFgDjzxbfhLjAoGAUyZ0mrCbNVpokkRqTTzfLSno65sB1vzusvggxFQN7WJQd0ywiVFZElpQz5rOjgr3ziqONIq1TMJAyo7dWWC/Mu45KLASCx0Yl4dRB/Q7dMbZWpx1y8nw+SixjzxQdIy9+qLFle6+nMJ44igCExAvp3osNsy8G3qbLn31sX3b5VkCgYEAy+n1BvMJmS0f6xjEScasWcMZ+nwmUo6oyXM4YTj+/CHrML4g7h+P1Zw5Z4d5yeo6mLfzYjAcIBucMZlLYvicmaxRJxbh6GaqTmmHxMVhgXTRJSFXwOTjsP2ieIxnTLjl1dvStNNn381rRjLdNLgTtI0GS30Wz8+7HYL1DJ3q2Fo=";
        byte[] decrypt = RSAUtil.decrypt(privateKey, data);
        Assertions.assertNotNull(decrypt);
    }
}