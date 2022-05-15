package com.solid.ocr.service;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.textdetection.CloudVisionTextOCR;
import com.solid.ocr.textdetection.CloudinaryTextOCR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TextDetectionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextDetectionService.class);

    private final CloudinaryTextOCR cloudinaryTextOCR;
    private final CloudVisionTextOCR cloudVisionTextOCR;

    public TextDetectionService(CloudinaryTextOCR cloudinaryTextOCR,
                                CloudVisionTextOCR cloudVisionTextOCR) {
        this.cloudinaryTextOCR = cloudinaryTextOCR;
        this.cloudVisionTextOCR = cloudVisionTextOCR;
    }

    public String recognize(MultipartFileWrapper imageFile) {
        try {
            String ocrText = cloudinaryTextOCR.retrieveTextFromImage(imageFile);
            if (ocrText == null) {
                return cloudVisionTextOCR.retrieveTextFromImage(imageFile);
            }
            return ocrText;
        } catch (IOException e) {
            LOGGER.error("Error extracting Text from image: " + imageFile.getOriginalFilename(), e);
            return null;
        }
    }

}
