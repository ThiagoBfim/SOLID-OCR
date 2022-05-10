package com.solid.ocr.resources;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.solid.ocr.exception.BadRequestException;
import com.solid.ocr.exception.InternalException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static java.nio.file.Files.createTempFile;

@RestController
@RequestMapping("/recognize")
public class TextDetectionController {

    private final org.springframework.core.env.Environment environment;

    public TextDetectionController(org.springframework.core.env.Environment environment) {
        this.environment = environment;
    }

    @PostMapping
    public String recognize(@RequestParam("file") MultipartFile file) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "bomfim",
                "api_key", environment.getProperty("cloudinary.api-key"),
                "api_secret", environment.getProperty("cloudinary.api-secret")));

        isValidImage(file);

        try {
            java.io.File tempFile = getResource(file);
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

    private File getResource(MultipartFile file) throws IOException {
        return createTmpFile(file);
    }

    private void isValidImage(MultipartFile file) {
        if (Objects.isNull(file)) {
            throw new BadRequestException("File must not be empty");
        }
    }

    private File createTmpFile(MultipartFile file) throws IOException {
        File tempFile = createTempFile(String.valueOf(UUID.randomUUID()), file.getOriginalFilename()).toFile();
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        } catch (Exception ex) {
            Files.deleteIfExists(tempFile.toPath());
            throw new InternalException("Error creating the file", ex);
        }
        return tempFile;
    }
}
