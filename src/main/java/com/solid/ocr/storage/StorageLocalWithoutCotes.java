package com.solid.ocr.storage;

import com.solid.ocr.entity.TextDetection;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class StorageLocalWithoutCotes implements IStorage {

    private static final Map<String, TextDetection> OCR_STORAGE = new HashMap<>();

    public StorageLocalWithoutCotes() {
    }

    @Override
    public void saveOCR(String hashImage, String text) {
        if (StringUtils.isNotEmpty(text)) {
            if (OCR_STORAGE.containsKey(hashImage)) {
                TextDetection textDetection = OCR_STORAGE.get(hashImage);
                if (isExpired(textDetection) && !Objects.equals(text, textDetection.getText())) {
                    clearFromStorage(hashImage);
                }
            }
            OCR_STORAGE.put(hashImage, new TextDetection(hashImage, text, LocalDateTime.now()));
        }
    }


    private boolean isExpired(TextDetection textDetection) {
        return textDetection.isExpired();
    }

    @Override
    public Optional<String> getOCRFromStorage(String hashImage) {
        if (OCR_STORAGE.containsKey(hashImage)) {
            TextDetection textDetection = OCR_STORAGE.get(hashImage);
            if (isExpired(textDetection)) {
                clearFromStorage(hashImage);
                return Optional.empty();
            }
            return Optional.ofNullable(textDetection.getText());
        }
        return Optional.empty();
    }

    private void clearFromStorage(String hashImage) {
        OCR_STORAGE.remove(hashImage);
    }

    @Override
    public void decrementLimit() {
        // N/A
    }

    @Override
    public long getAvailableCotes() {
        return 1L;
    }

}
