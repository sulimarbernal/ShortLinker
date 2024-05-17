package com.ShortLinker.utility;

import java.util.UUID;

public class UrlGenerator {

    public static String generateUniqueKey() {
        UUID uuid = UUID.randomUUID();
        long timestamp = System.currentTimeMillis();
        String shortUrl = Base62Encoder.encodeToBase62(uuid, timestamp);
        System.out.println("Short URL: " + shortUrl);
        return shortUrl;
    }

    public static String buildCompleteUrl(String baseUrl, String shortUrlPath) {
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        if (shortUrlPath.endsWith("/")) {
            shortUrlPath = shortUrlPath.substring(0, shortUrlPath.length() - 1);
        }
        return baseUrl + "/" + shortUrlPath + "/";
    }
}