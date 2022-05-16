package com.solid.ocr.service;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.textdetection.AbstractTextOCR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TextDetectionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextDetectionService.class);

    private final List<AbstractTextOCR> textOCRService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public TextDetectionService(List<AbstractTextOCR> textOCRService) {
        this.textOCRService = textOCRService;
    }

    public String recognize(MultipartFileWrapper imageFile) {
        for (AbstractTextOCR abstractTextOCR : textOCRService) {
            String ocrText = getOcrText(imageFile, abstractTextOCR);
            if (ocrText != null) {
                return ocrText;
            }
        }
        return null;
    }

    @Nullable
    private String getOcrText(MultipartFileWrapper imageFile, AbstractTextOCR abstractTextOCR) {
        try {
            return abstractTextOCR.retrieveTextFromImage(imageFile);
        } catch (IOException e) {
            LOGGER.error("Error extracting Text from image: " + imageFile.getOriginalFilename(), e);
        }
        return null;
    }


}
