package com.ShortLinker.utility;

import java.util.UUID;
import java.math.BigInteger;

public class Base62Encoder {
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String encodeToBase62(UUID uuid, long timestamp) {
        String value = uuid.toString().replace("-", "") + Long.toHexString(timestamp);
        return encodeBase62(new BigInteger(value, 16));
    }

    private static String encodeBase62(BigInteger number) {
        StringBuilder result = new StringBuilder();
        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divmod = number.divideAndRemainder(BigInteger.valueOf(62));
            result.insert(0, BASE62.charAt(divmod[1].intValue()));
            number = divmod[0];
        }
        return result.toString();
    }
}