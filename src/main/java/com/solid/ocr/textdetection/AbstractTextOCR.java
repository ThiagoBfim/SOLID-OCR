package com.solid.ocr.textdetection;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.storage.ICotesStorage;
import com.solid.ocr.storage.LocalTextStorage;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Optional;


public abstract class AbstractTextOCR {

    @Nullable
    public String retrieveTextFromImage(MultipartFileWrapper imageFile) throws IOException {
        final String imageHash = imageFile.getHash();
        return getTextStorage().getOCRFromStorage(imageHash)
                .orElseGet(() -> tryToExtractText(imageHash, imageFile));
    }

    @Nullable
    private String tryToExtractText(String imageHash, MultipartFileWrapper imageFile) {
        long availableCotes = getAvailableCotes();
        if (availableCotes > 0) {
            String ocrText = extractText(imageFile);
            getTextStorage().saveOCR(imageHash, ocrText);
            getCotesStorage().ifPresent(ICotesStorage::decrementLimit);
            return ocrText;
        }
        return null;
    }

    private long getAvailableCotes() {
        return getCotesStorage().map(ICotesStorage::getAvailableCotes).orElse(1L);
    }

    protected abstract String extractText(MultipartFileWrapper imageFile);

    protected abstract Optional<ICotesStorage> getCotesStorage();

    protected LocalTextStorage getTextStorage() {
        return new LocalTextStorage();
    }

}
