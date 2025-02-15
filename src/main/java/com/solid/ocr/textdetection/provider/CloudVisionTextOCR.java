package com.solid.ocr.textdetection.provider;

import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.storage.provider.CotesStorageLocal;
import com.solid.ocr.storage.ICotesStorage;
import com.solid.ocr.textdetection.AbstractTextOCR;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Order(1)
public class CloudVisionTextOCR extends AbstractTextOCR {

    private final CloudVisionTemplate cloudVisionTemplate;

    public CloudVisionTextOCR(CloudVisionTemplate cloudVisionTemplate) {
        this.cloudVisionTemplate = cloudVisionTemplate;
    }

    @Nullable
    @Override
    public String extractText(MultipartFileWrapper imageFile) {
        return this.cloudVisionTemplate.extractTextFromImage(imageFile.getResource());
    }

    @Override
    protected Optional<ICotesStorage> getCotesStorage() {
        return Optional.of(new CotesStorageLocal(10L));
    }

}
