package com.solid.ocr.entity;

import java.time.LocalDateTime;

public class TextDetection {

    private final String hashFile;
    private LocalDateTime time;
    private String text;

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

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isNotExpired() {
        return !isExpired();
    }

    public boolean isExpired() {
        return time.plusDays(30).isBefore(LocalDateTime.now());
    }
}
