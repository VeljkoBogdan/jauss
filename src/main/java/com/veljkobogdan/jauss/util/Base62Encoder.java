package com.veljkobogdan.jauss.util;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;

@Service
public class Base62Encoder {
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateShortCode(String url) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(url.getBytes());
        BigInteger number = new BigInteger(1, digest);

        return toBase62(number.mod(BigInteger.valueOf(62L * 62 * 62 * 62 * 62 * 62))); // Use modulo to limit length
    }

    private static String toBase62(BigInteger number) {
        StringBuilder result = new StringBuilder();
        while (number.compareTo(BigInteger.ZERO) > 0) {
            result.append(BASE62.charAt(number.mod(BigInteger.valueOf(62)).intValue()));
            number = number.divide(BigInteger.valueOf(62));
        }
        return result.reverse().toString();
    }
}
