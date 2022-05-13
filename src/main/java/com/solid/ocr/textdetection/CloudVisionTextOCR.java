package com.solid.ocr.textdetection;

import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.solid.ocr.resources.MultipartFileWrapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class CloudVisionTextOCR extends AbstractTextOCR {

    private final CloudVisionTemplate cloudVisionTemplate;

    public CloudVisionTextOCR(CloudVisionTemplate cloudVisionTemplate) {
        this.cloudVisionTemplate = cloudVisionTemplate;
    }

    @Nullable
    @Override
    public String retrieveTextFromImage(MultipartFileWrapper imageFile) {
        return this.cloudVisionTemplate.extractTextFromImage(imageFile.getResource());
    }

}
