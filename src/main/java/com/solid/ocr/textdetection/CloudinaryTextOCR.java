package com.solid.ocr.textdetection;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.storage.ICotesStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static java.nio.file.Files.createTempFile;

@Service
@Order(0)
public class CloudinaryTextOCR extends AbstractTextOCR {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloudinaryTextOCR.class);

    private final Environment environment;

    public CloudinaryTextOCR(Environment environment) {
        this.environment = environment;
    }

    @Nullable
    @Override
    public String extractText(MultipartFileWrapper imageFile) {
        try {
            var cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "bomfim",
                    "api_key", environment.getProperty("cloudinary.api-key"),
                    "api_secret", environment.getProperty("cloudinary.api-secret")));
            var tempFile = createTmpFile(imageFile);
            var upload = cloudinary.uploader().upload(tempFile, ObjectUtils.asMap("ocr", "adv_ocr"));

            Object text = getTextFromImage(tempFile, upload);
            return "Text from image: " + text;
        } catch (Exception ex) {
            LOGGER.error("Error extracting text from image with Cloudinary service", ex);
            return null;
        }
    }

    @Override
    protected Optional<ICotesStorage> getCotesStorage() {
        return Optional.empty();
    }

    private Object getTextFromImage(File tempFile, Map upload) throws IOException {
        Files.deleteIfExists(tempFile.toPath());
        return ((HashMap) ((HashMap) ((ArrayList) ((HashMap) ((HashMap) ((HashMap) upload.get("info")).get("ocr"))
                .get("adv_ocr")).get("data")).get(0)).get("fullTextAnnotation")).get("text");
    }

    private File createTmpFile(MultipartFileWrapper file) throws IOException {
        var tempFile = createTempFile(String.valueOf(UUID.randomUUID()), file.getOriginalFilename()).toFile();
        file.copyToFile(tempFile);
        return tempFile;
    }

}
