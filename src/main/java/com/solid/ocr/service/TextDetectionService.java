package com.solid.ocr.service;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.textdetection.CloudVisionTextOCR;
import com.solid.ocr.textdetection.CloudinaryTextOCR;
import com.solid.ocr.textdetection.MicrosoftTextOCR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TextDetectionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextDetectionService.class);

    private final CloudinaryTextOCR cloudinaryTextOCR;
    private final CloudVisionTextOCR cloudVisionTextOCR;
    private final MicrosoftTextOCR microsoftTextOCR;

    public TextDetectionService(CloudinaryTextOCR cloudinaryTextOCR,
                                CloudVisionTextOCR cloudVisionTextOCR,
                                MicrosoftTextOCR microsoftTextOCR) {
        this.cloudinaryTextOCR = cloudinaryTextOCR;
        this.cloudVisionTextOCR = cloudVisionTextOCR;
        this.microsoftTextOCR = microsoftTextOCR;
    }

    public String recognize(MultipartFileWrapper imageFile) {
        try {
            String ocrText = cloudinaryTextOCR.retrieveTextFromImage(imageFile);
            if (ocrText == null) {
                ocrText = cloudVisionTextOCR.retrieveTextFromImage(imageFile);
            }
            if (ocrText == null) {
                return microsoftTextOCR.retrieveTextFromImage(imageFile);
            }
            return ocrText;
        } catch (IOException e) {
            LOGGER.error("Error extracting Text from image: " + imageFile.getOriginalFilename(), e);
            return null;
        }
    }

}
