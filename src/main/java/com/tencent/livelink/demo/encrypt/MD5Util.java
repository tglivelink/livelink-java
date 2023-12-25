package com.tencent.livelink.demo.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MD5Util {

    static final String[] hexArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String encryptByMD5(String originString) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] bytes = md.digest(originString.getBytes());

            String s = byteArrayToHex(bytes);

            return s.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String byteArrayToHex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte value : b) {
            sb.append(byteToHex(value));
        }
        return sb.toString();
    }

    public static String byteToHex(byte b) {
        int n = b;
        if (n < 0) {
            n = n + 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexArray[d1] + hexArray[d2];
    }

}
