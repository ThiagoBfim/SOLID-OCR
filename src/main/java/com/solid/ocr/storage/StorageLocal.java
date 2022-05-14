package com.solid.ocr.storage;

import java.util.HashMap;
import java.util.Map;

public class StorageLocal {

    private static final Map<String, Long> limits = new HashMap<>();
    public static final long CLOUDINARY_LIMIT = 10L;

    public static void restartLimit() {
        limits.put("cloudinary", CLOUDINARY_LIMIT);
    }

    public static void decrementLimit(String key) {
        Long limit = getLimit(key);
        limits.put(key, --limit);
    }

    public static Long getLimit(String key) {
        return StorageLocal.limits.get(key);
    }
}
