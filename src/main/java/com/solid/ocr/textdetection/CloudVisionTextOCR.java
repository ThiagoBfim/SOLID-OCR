package com.solid.ocr.textdetection;

import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.solid.ocr.resources.MultipartFileWrapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CloudVisionTextOCR extends AbstractTextOCR {

    private static AtomicLong limit = new AtomicLong(10L);
    private final CloudVisionTemplate cloudVisionTemplate;

    public CloudVisionTextOCR(CloudVisionTemplate cloudVisionTemplate) {
        this.cloudVisionTemplate = cloudVisionTemplate;
    }

    @Nullable
    @Override
    public String retrieveTextFromImage(MultipartFileWrapper imageFile) {
        return this.cloudVisionTemplate.extractTextFromImage(imageFile.getResource());
    }

    @Override
    public void decrementLimit() {
        limit.decrementAndGet();
    }

    @Override
    public long getAvailableCotes() {
        return limit.get();
    }

}
