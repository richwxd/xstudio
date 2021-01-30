package com.xstudio.crypto.asymmetric;

import com.xstudio.crypto.Base64Util;
import com.xstudio.crypto.KeyString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;

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
    void testKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IOException, InvalidAlgorithmParameterException {
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDwRRrRsdfk5EXl+s67Ome0OrLJrEchjC6b2FGl8iRm6STD4lu/Q/HxgkpFt8c6/eFWZXlQOM5wi5h7U1cRdgaIriSuvjqCnOB7NWm7+gDwTbra/4NUHwX7+eV97b45vv/NqzkG4bkoW2Ywr+yJVJp/Wgs5YM9/Atne2OuQUv9Ebnfaf6/+crSjBkHydEOCRfkvxYEJuOKG1VYVGszl/IuiqZE4VVlG/yHoYb1F1RNr20QKqbw5pJKrzUU/8TZT3tAO+rABAmxgbWvMwqr2/ED25fPwUd2+BZ3H1iOWH0E3U+h0onqX6056iDdroQR+hclMDGjm4ZCR1YoYTNBsSlvLAgMBAAECggEBAN7pHgKiHoxrBDZjxFyLyR66vpjATpXfOna38LgmuIMJlmRto3UFknffywx3fk1KsT64kh5NNanQuTzhiwvWFiXEep/sONhotxNX8KrHPQu08cCwAaWPyruFktaMNUnZ2/Vx/0xVKrU4ZsyQysB2T4gLgBJRsPBDMuK/GX0io1VV44dv9oFO3Nq5Z7ceGItUStZP/J/fEuGqHfVZO4b7qHWWRvgj2q7VFYIVvChfWQnmWjZwOtY2jhi77nhTAvQ39KLSymAJOFfYQu94mt7UysbaArgAsOnnAB7YrpjlcEOH5Due+9k/RYCEXPxnPfdAklI8XpnnJddtFVpN5S6awfECgYEA+EA11PODGoclQnyukSrK8e19o16EEyfHV0o9UQJO5PXQIFMGdtZHYv0oaaEQD0EWTWbQr7sn9ktgD8o9ZFXxpDF71IhPI56oA9AEdrdvaMxJWQOdAbDB5/d9UVkXTzfZBT2X3sqVj5OFFHRXjaJmSukZCFuu/1wSBXGcZy2admUCgYEA98UeY0kXpGAlIOe5FXCYopYDcXIQbdsJuBgHNIfjCogsfiX1mqACgy1L6Q2Rd34lY6btkJsT7k4tvILXTM/pWD9yyzDOMgZWxVDcLBUcYve0cBI9iIg1SzL1k4oBVIIvyJ2m4jLkqPNIKe09ckBphcSlR4Hz29lvORGeiTVBjm8CgYEArXBW68eRsgf3fSMU5zEJhsqQu4G+Lo/dElw4yI8fRZGDbgHzgzUjvMH36Qxw5udnSH5rkErmmYmBvKZkC+SLpVY5GHx/Jfijk75SUArKWGJZYEpI+DRg1MqxEBN5WIxBIAsvPqU0ppG/KVI7FSX/55hBN/iuI5RUtt7wEZnnz40CgYA3Qt7BNDcysUL2sNgmAQP6F01TSyaA6f6j8bABtmEcIz0RMbQytYd57Gtm4mDUbu016bBItV3fo84tol0Uw7Z+uUAIM615mNJXle5VYqv/ItI6wMa6oe6JBCejrX8YcyTnMMpc3w8C46fvufsfdWBTtoVKiqByI2/cIaLjZV5mvwKBgE1afGbIZbJ35jwJrLnGnjLYID6+IuDx1klFkMhM+eLZpJYbi75CAMaAV7SaF41Lw7VsadWTRAh4WIw69tO0QvzKq3v8ywy8ktQB32GiXatcjrZg6xr5mhsTgLCbn+Plc+i4VVnN3TUEucCdDAg+LZyuAKeDOcQ+RxpTyElpyQDu";
        byte[] decrypt = RSAUtil.decrypt(privateKey, "eSlcGPr/cB493SXxG6BM4Z2hhCo1Q4cFcVoUl2SkuKa0/PgpXb3ul/54cMZ0YNZwcyQnIDIPWQBcGzea0SUJgHc/3BgbPmKzre+dMFmKHLXwFeemjbignB1NW8FebKZJGlBKqRZCydz2oF0hj/q73Rlv2zTt5xemU82SCCkmTCM6NTRTDW9a6948/RCWv8VfvQ7Shp1S2PnFoRZN8cggZiStkaZ6T3vNaBUB6cd2+kvJfFyBLFBqbL4qnQokKnOYNFvrqFk0oQs9S87Jqk0XwLIYTCa2N/rFW9m4qjnSvxtfqHcYXAjo0pMuHsOG74m9A0v2ZGBI+JBlFggMHXHjww==");
        Assertions.assertEquals("123456", new String(decrypt));
    }
}