package com.solid.ocr.service;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.textdetection.CloudVisionTextOCR;
import com.solid.ocr.textdetection.CloudinaryTextOCR;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
        String ocrText = cloudinaryTextOCR.retrieveTextFromImage(imageFile);
        if (ocrText == null) {
            return cloudVisionTextOCR.retrieveTextFromImage(imageFile);
        }
        return ocrText;
    }

}
