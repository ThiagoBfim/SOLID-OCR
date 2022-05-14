package com.solid.ocr.textdetection;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.storage.ICotesStorage;
import org.springframework.lang.Nullable;

import java.util.Optional;


public abstract class AbstractTextOCR {

    @Nullable
    public String retrieveTextFromImage(MultipartFileWrapper imageFile) {
        long availableCotes = getAvailableCotes();
        if (availableCotes > 0) {
            getCotesStorage().ifPresent(ICotesStorage::decrementLimit);
            return extractText(imageFile);
        }
        return null;
    }

    private long getAvailableCotes() {
        return getCotesStorage().map(ICotesStorage::getAvailableCotes).orElse(1L);
    }

    protected abstract String extractText(MultipartFileWrapper imageFile);

    protected abstract Optional<ICotesStorage> getCotesStorage();

}
