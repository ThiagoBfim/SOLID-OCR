package com.solid.ocr.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.solid.ocr.exception.InternalException;
import com.solid.ocr.resources.MultipartFileWrapper;
import org.springframework.core.env.Environment;
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
public class TextDetectionService {

    private final Environment environment;

    public TextDetectionService(Environment environment) {
        this.environment = environment;
    }

    public String recognize(MultipartFileWrapper imageFile) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "bomfim",
                "api_key", environment.getProperty("cloudinary.api-key"),
                "api_secret", environment.getProperty("cloudinary.api-secret")));

        try {
            File tempFile = createTmpFile(imageFile);
            Map upload = cloudinary.uploader().upload(tempFile, ObjectUtils.asMap("ocr", "adv_ocr"));

            Object text = getTextFromImage(tempFile, upload);
            return "Text from image: " + text;
        } catch (IOException ex) {
            throw new InternalException("Error creating the file", ex);
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
