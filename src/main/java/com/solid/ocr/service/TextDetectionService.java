package com.solid.ocr.service;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.textdetection.CloudVisionTextOCR;
import com.solid.ocr.textdetection.CloudinaryTextOCR;
import org.springframework.stereotype.Service;

import static java.nio.file.Files.createTempFile;

@Service
public class TextDetectionService {

    private final CloudinaryTextOCR cloudinaryTextOCR;
    private final CloudVisionTextOCR cloudVisionTextOCR;

    public TextDetectionService(CloudinaryTextOCR cloudinaryTextOCR,
                                CloudVisionTextOCR cloudVisionTextOCR) {
        this.cloudinaryTextOCR = cloudinaryTextOCR;
        this.cloudVisionTextOCR = cloudVisionTextOCR;
    }

    public String recognize(MultipartFileWrapper imageFile) {
        if (cloudinaryTextOCR.getAvailableCotes() > 0) {
            String text = cloudinaryTextOCR.retrieveTextFromImage(imageFile);
            if (text == null) {
                return extractFromCloudVisionOrNull(imageFile);
            }
            cloudinaryTextOCR.decrementLimit();
            return text;
        } else {
            return extractFromCloudVisionOrNull(imageFile);
        }
    }

    private String extractFromCloudVisionOrNull(MultipartFileWrapper imageFile) {
        if (cloudVisionTextOCR.getAvailableCotes() > 0) {
            String text = cloudVisionTextOCR.retrieveTextFromImage(imageFile);
            if (text != null) {
                cloudinaryTextOCR.decrementLimit();
                return text;
            }
        }
        return null;
    }

}
