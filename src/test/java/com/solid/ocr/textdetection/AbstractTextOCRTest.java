package com.solid.ocr.textdetection;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.storage.CotesStorageLocal;
import com.solid.ocr.storage.ICotesStorage;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractTextOCRTest {

    @Test
    void shouldRetriveTextFromImageWithoutCotesStorage() {
        AbstractTextOCRMock abstractTextOCRMock = new AbstractTextOCRMock(null);
        String text = abstractTextOCRMock.retrieveTextFromImage(null);
        assertThat(text).isEqualTo("empty");
    }

    @Test
    void shouldRetriveTextFromImageWithCotesStorage() {
        AbstractTextOCRMock abstractTextOCRMock = new AbstractTextOCRMock(new CotesStorageLocal(1L));
        String text = abstractTextOCRMock.retrieveTextFromImage(null);
        assertThat(text).isEqualTo("empty"); //1 Cotes Available
        text = abstractTextOCRMock.retrieveTextFromImage(null);
        assertThat(text).isNull(); //0 Cotes Available
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
