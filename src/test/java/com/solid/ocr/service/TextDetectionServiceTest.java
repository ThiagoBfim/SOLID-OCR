package com.solid.ocr.service;

import com.solid.ocr.resources.MultipartFileWrapper;
import com.solid.ocr.textdetection.CloudVisionTextOCR;
import com.solid.ocr.textdetection.CloudinaryTextOCR;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TextDetectionServiceTest {

    private TextDetectionService textDetectionService;

    @Mock
    private CloudinaryTextOCR cloudinaryTextOCR;

    @Mock
    private CloudVisionTextOCR cloudVisionTextOCR;

    @BeforeEach
    public void setUp() {
        textDetectionService = new TextDetectionService(cloudinaryTextOCR, cloudVisionTextOCR);
    }

    @Test
    void shouldRecognizeWithCloudVision() {
        when(cloudVisionTextOCR.getAvailableCotes()).thenReturn(1L);
        when(cloudVisionTextOCR.retrieveTextFromImage(any())).thenReturn("CloudVision");
        String recognize = textDetectionService.recognize(mock(MultipartFileWrapper.class));
        Assertions.assertThat(recognize).isEqualTo("CloudVision");
    }

    @Test
    void shouldRecognizeWithCloudinary() {
        when(cloudinaryTextOCR.getAvailableCotes()).thenReturn(1L);
        when(cloudinaryTextOCR.retrieveTextFromImage(any())).thenReturn("Cloudinary");
        String recognize = textDetectionService.recognize(mock(MultipartFileWrapper.class));
        Assertions.assertThat(recognize).isEqualTo("Cloudinary");
        verify(cloudVisionTextOCR, never()).retrieveTextFromImage(any());
    }
}
