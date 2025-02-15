package com.solid.ocr.resources;

import com.solid.ocr.exception.BadRequestException;
import com.solid.ocr.exception.InternalException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class MultipartFileWrapper {

    private final MultipartFile multipartFile;

    public MultipartFileWrapper(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
        validateImage();
    }

    private void validateImage() {
        validFile(multipartFile);
        String extension = getFileExtension(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        if (isNotImageFile(extension)) {
            throw new BadRequestException("File with wrong content-type : " + multipartFile.getContentType() + " content-type accepted: " + " PNG, JPEG, JFIF");
        }
    }

    private void validFile(MultipartFile file) {
        if (Objects.isNull(file) || Objects.isNull(file.getOriginalFilename())) {
            throw new BadRequestException("File must not be empty");
        }
    }

    private boolean isNotImageFile(String extension) {
        return !(extension.contains("PNG")
                || extension.contains("JPEG")
                || extension.contains("JFIF"));
    }

    private String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i + 1).toUpperCase();
        }
        throw new BadRequestException("File extension is incorrect.");
    }

    public String getHash() throws IOException {
        return DigestUtils.md5Hex(multipartFile.getBytes()).toUpperCase();
    }

    public Resource getResource() {
        return multipartFile.getResource();
    }

    public String getOriginalFilename() {
        return multipartFile.getOriginalFilename();
    }

    public void copyToFile(File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (Exception ex) {
            throw new InternalException("Error creating the file", ex);
        }
    }
}
