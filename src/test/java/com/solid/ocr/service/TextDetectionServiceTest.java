package com.solid.ocr.service;

import com.solid.ocr.resources.MultipartFileWrapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Objects;


@RunWith(SpringRunner.class)
@SpringBootTest
@IfProfileValue(name = "spring.profiles.active", values = {"test"})
class TextDetectionServiceTest {

    @Autowired
    private TextDetectionService textDetectionService;

    @Test
    void recognize() throws IOException {
        byte[] image = Objects.requireNonNull(this.getClass().getResourceAsStream("/assets/good-morning.png")).readAllBytes();
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "good-morning.png",
                        MediaType.APPLICATION_OCTET_STREAM_VALUE,
                        image);
        MultipartFileWrapper multipartFileWrapper = new MultipartFileWrapper(file);
        String recognize = textDetectionService.recognize(multipartFileWrapper);
        Assertions.assertThat(recognize).contains("GOOD", "MORNING");
    }
}
