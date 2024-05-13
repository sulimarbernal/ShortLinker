package com.ShortLinker.utility;

import java.util.UUID;
import com.ShortLinker.utility.Base62Encoder;

public class UrlGenerator {

    public static String generateUniqueKey() {
        UUID uuid = UUID.randomUUID();
        long timestamp = System.currentTimeMillis();
        String shortUrl = Base62Encoder.encodeToBase62(uuid, timestamp);
        System.out.println("Short URL: " + shortUrl);
        return shortUrl;
    }
}