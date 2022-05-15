package com.solid.ocr.textdetection;

import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.storage.StorageLocal;
import com.solid.ocr.storage.IStorage;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
    protected Optional<IStorage> getStorage() {
        return Optional.of(new StorageLocal(10L));
    }

}
