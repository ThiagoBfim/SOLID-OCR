package com.solid.ocr.entity;

import java.time.LocalDateTime;

public class TextDetection {

    private final String hashFile;
    private final LocalDateTime time;
    private final String text;

    public TextDetection(String hashFile, String text, LocalDateTime time) {
        this.hashFile = hashFile;
        this.text = text;
        this.time = time;
    }

    public String getHashFile() {
        return hashFile;
    }

    public String getText() {
        return text;
    }

    public boolean isExpired() {
        return time.plusDays(30).isBefore(LocalDateTime.now());
    }
}
