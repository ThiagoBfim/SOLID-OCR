package com.solid.ocr.textdetection;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.storage.IStorage;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Optional;


public abstract class AbstractTextOCR {

    @Nullable
    public String retrieveTextFromImage(MultipartFileWrapper imageFile) throws IOException {
        final String imageHash = imageFile.getHash();
        if (getStorage().isPresent()) {
            IStorage storage = getStorage().get();
            return storage.getOCRFromStorage(imageHash)
                    .orElseGet(() -> tryToExtractText(storage, imageHash, imageFile));
        }
        return extractText(imageFile);
    }

    @Nullable
    private String tryToExtractText(IStorage storage, String imageHash, MultipartFileWrapper imageFile) {
        long availableCotes = getAvailableCotes();
        if (availableCotes > 0) {
            String ocrText = extractText(imageFile);
            storage.saveOCR(imageHash, ocrText);
            storage.decrementLimit();
            return ocrText;
        }
        return null;
    }

    private long getAvailableCotes() {
        return getStorage().map(IStorage::getAvailableCotes).orElse(1L);
    }

    protected abstract String extractText(MultipartFileWrapper imageFile);

    protected abstract Optional<IStorage> getStorage();

}
