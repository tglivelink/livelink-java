package com.tencent.livelink.demo.encrypt;

import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

@Slf4j
public class AESPlain {


    /**
     * 加密json字符串
     *
     * @param sSrc 输入json
     * @param sKey 密钥
     * @return 加密json串
     */
    public static String encryptJson(
            String sSrc,
            String sKey
    ) {
        try {
            if (sKey == null) {
                return null;
            }
            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(
                    raw,
                    "AES"
            );
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); //"算法/模式/补码方式"

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    keySpec
            );
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));

            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            log.error(
                    "encryptJson e:",
                    e
            );
            return null;
        }
    }
}
