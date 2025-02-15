package com.solid.ocr.storage.provider;

import com.solid.ocr.entity.TextDetection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

class LocalTextStorageTest {

    @Test
    void shouldNotFoundTextFromStorage() {
        Optional<String> textFromStorage = new LocalTextStorage().getOCRFromStorage("0123");
        Assertions.assertThat(textFromStorage).isEmpty();
    }

    @Test
    void shouldFoundTextFromStorage() {
        LocalTextStorage localTextStorage = new LocalTextStorage();
        localTextStorage.saveOCR("0123", "text");
        Optional<String> textFromStorage = localTextStorage.getOCRFromStorage("0123");
        Assertions.assertThat(textFromStorage).contains("text");
    }

    @Test
    void shouldNotFoundTextFromStorageIfExpired() {
        LocalTextStorage localTextStorage = new LocalTextStorage();
        localTextStorage.getOcrStorage().put("0123", new TextDetection("0123", "text", LocalDateTime.now().minusMonths(1).minusSeconds(1L)));
        Optional<String> textFromStorage = localTextStorage.getOCRFromStorage("0123");
        Assertions.assertThat(textFromStorage).isEmpty();
    }

    @Test
    void shouldSaveAgainIfExpired() {
        LocalTextStorage localTextStorage = new LocalTextStorage();
        localTextStorage.getOcrStorage().put("0123", new TextDetection("0123", "text", LocalDateTime.now().minusMonths(1)));
        localTextStorage.saveOCR("0123", "text");
        Assertions.assertThat(localTextStorage.getOcrStorage().get("0123").isExpired()).isFalse();
    }
}
