package com.solid.ocr.textdetection;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.storage.StorageLocal;
import com.solid.ocr.storage.IStorage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AbstractTextOCRTest {

    @Test
    void shouldRetriveTextFromImageWithoutCotesStorage() throws IOException {
        AbstractTextOCRMock abstractTextOCRMock = new AbstractTextOCRMock(null);
        MultipartFileWrapper mock = mock(MultipartFileWrapper.class);
        when(mock.getHash()).thenReturn(new Random().toString());
        String text = abstractTextOCRMock.retrieveTextFromImage(mock);
        assertThat(text).isEqualTo("empty");
    }

    @Test
    void shouldRetriveTextFromImageWithCotesStorage() throws IOException {
        AbstractTextOCRMock abstractTextOCRMock = new AbstractTextOCRMock(new StorageLocal(1L));
        MultipartFileWrapper mock = mock(MultipartFileWrapper.class);
        when(mock.getHash()).thenReturn(new Random().toString());

        String text = abstractTextOCRMock.retrieveTextFromImage(mock);
        assertThat(text).isEqualTo("empty"); //1 Cotes Available

        MultipartFileWrapper newMock = mock(MultipartFileWrapper.class);
        when(newMock.getHash()).thenReturn(new Random().toString());

        text = abstractTextOCRMock.retrieveTextFromImage(newMock);
        assertThat(text).isNull(); //0 Cotes Available
    }

    @Test
    void shouldNotRetriveTextFromImage() throws IOException {
        MultipartFileWrapper mock = mock(MultipartFileWrapper.class);
        when(mock.getHash()).thenReturn(new Random().toString());
        AbstractTextOCRMock abstractTextOCRMock = new AbstractTextOCRMock(new StorageLocal(0L));
        String text = abstractTextOCRMock.retrieveTextFromImage(mock);
        assertThat(text).isNull();
    }

    static class AbstractTextOCRMock extends AbstractTextOCR {

        private final IStorage storage;

        AbstractTextOCRMock(IStorage storage) {
            this.storage = storage;
        }

        @Override
        protected String extractText(MultipartFileWrapper imageFile) {
            return "empty";
        }

        @Override
        protected Optional<IStorage> getStorage() {
            return Optional.ofNullable(storage);
        }
    }

}
