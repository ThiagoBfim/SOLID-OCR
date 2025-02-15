package com.solid.ocr.textdetection.provider;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.storage.provider.CotesStorageLocal;
import com.solid.ocr.storage.ICotesStorage;
import com.solid.ocr.textdetection.AbstractTextOCR;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Order(2)
public class MicrosoftTextOCR extends AbstractTextOCR {

    @Nullable
    @Override
    public String extractText(MultipartFileWrapper imageFile) {
        return "EMPTY"; //TODO include Microsoft implementation of OCR
    }

    @Override
    protected Optional<ICotesStorage> getCotesStorage() {
        return Optional.of(new CotesStorageLocal(10L));
    }

}
