package com.solid.ocr.service;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.textdetection.CloudVisionTextOCR;
import com.solid.ocr.textdetection.CloudinaryTextOCR;
import com.solid.ocr.textdetection.MicrosoftTextOCR;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TextDetectionServiceTest {

    private TextDetectionService textDetectionService;

    @Mock
    private CloudinaryTextOCR cloudinaryTextOCR;

    @Mock
    private CloudVisionTextOCR cloudVisionTextOCR;

    @Mock
    private MicrosoftTextOCR microsoftTextOCR;

    @BeforeEach
    public void setUp() {
        textDetectionService = new TextDetectionService(Arrays.asList(cloudinaryTextOCR, cloudVisionTextOCR, microsoftTextOCR));
    }

    @Test
    void shouldRecognizeWithCloudVision() throws IOException {
        when(cloudinaryTextOCR.retrieveTextFromImage(any())).thenReturn(null);
        when(cloudVisionTextOCR.retrieveTextFromImage(any())).thenReturn("CloudVision");
        String recognize = textDetectionService.recognize(mock(MultipartFileWrapper.class));
        Assertions.assertThat(recognize).isEqualTo("CloudVision");
    }

    @Test
    void shouldRecognizeWithCloudinary() throws IOException {
        when(cloudinaryTextOCR.retrieveTextFromImage(any())).thenReturn("Cloudinary");
        String recognize = textDetectionService.recognize(mock(MultipartFileWrapper.class));
        Assertions.assertThat(recognize).isEqualTo("Cloudinary");
        verify(cloudVisionTextOCR, never()).retrieveTextFromImage(any());
    }

    @Test
    void shouldRecognizeWithMicrosoft() throws IOException {
        when(cloudinaryTextOCR.retrieveTextFromImage(any())).thenReturn(null);
        when(cloudVisionTextOCR.retrieveTextFromImage(any())).thenReturn(null);
        when(microsoftTextOCR.retrieveTextFromImage(any())).thenReturn("Microsoft");
        String recognize = textDetectionService.recognize(mock(MultipartFileWrapper.class));
        Assertions.assertThat(recognize).isEqualTo("Microsoft");
    }
}
