package com.solid.ocr.textdetection;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.storage.CotesStorageLocal;
import com.solid.ocr.storage.ICotesStorage;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MicrosoftTextOCR extends AbstractTextOCR {


    @Nullable
    @Override
    public String extractText(MultipartFileWrapper imageFile) {
        return "EMPTY"; //TODO include other implementation of OCR
    }

    @Override
    protected Optional<ICotesStorage> getCotesStorage() {
        return Optional.of(new CotesStorageLocal(10L));
    }

}
