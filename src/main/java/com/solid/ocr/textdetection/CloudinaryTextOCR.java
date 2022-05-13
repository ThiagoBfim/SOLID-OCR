package com.solid.ocr.textdetection;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.solid.ocr.resources.MultipartFileWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.nio.file.Files.createTempFile;

@Service
public class CloudinaryTextOCR extends AbstractTextOCR {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloudinaryTextOCR.class);

    private final Environment environment;

    public CloudinaryTextOCR(Environment environment) {
        this.environment = environment;
    }

    @Nullable
    @Override
    public String retrieveTextFromImage(MultipartFileWrapper imageFile) {
        try {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "bomfim",
                    "api_key", environment.getProperty("cloudinary.api-key"),
                    "api_secret", environment.getProperty("cloudinary.api-secret")));
            File tempFile = createTmpFile(imageFile);
            Map upload = cloudinary.uploader().upload(tempFile, ObjectUtils.asMap("ocr", "adv_ocr"));

            Object text = getTextFromImage(tempFile, upload);
            return "Text from image: " + text;
        } catch (Exception ex) {
            LOGGER.error("Error extracting text from image with Cloudinary service", ex);
            return null;
        }
    }

    private Object getTextFromImage(File tempFile, Map upload) throws IOException {
        Files.deleteIfExists(tempFile.toPath());
        return ((HashMap) ((HashMap) ((ArrayList) ((HashMap) ((HashMap) ((HashMap) upload.get("info")).get("ocr"))
                .get("adv_ocr")).get("data")).get(0)).get("fullTextAnnotation")).get("text");
    }

    private File createTmpFile(MultipartFileWrapper file) throws IOException {
        File tempFile = createTempFile(String.valueOf(UUID.randomUUID()), file.getOriginalFilename()).toFile();
        file.copyToFile(tempFile);
        return tempFile;
    }

}
