package com.solid.ocr.textdetection;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.storage.CotesStorageLocal;
import com.solid.ocr.storage.ICotesStorage;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractTextOCRTest {

    @Test
    void shouldRetriveTextFromImage() {
        AbstractTextOCRMock abstractTextOCRMock = new AbstractTextOCRMock(null);
        String text = abstractTextOCRMock.retrieveTextFromImage(null);
        assertThat(text).isEqualTo("empty");
    }

    @Test
    void shouldNotRetriveTextFromImage() {
        AbstractTextOCRMock abstractTextOCRMock = new AbstractTextOCRMock(new CotesStorageLocal(0L));
        String text = abstractTextOCRMock.retrieveTextFromImage(null);
        assertThat(text).isNull();
    }

    static class AbstractTextOCRMock extends AbstractTextOCR {

        private final ICotesStorage storage;

        AbstractTextOCRMock(ICotesStorage storage) {
            this.storage = storage;
        }

        @Override
        protected String extractText(MultipartFileWrapper imageFile) {
            return "empty";
        }

        @Override
        protected Optional<ICotesStorage> getCotesStorage() {
            return Optional.ofNullable(storage);
        }
    }

}
