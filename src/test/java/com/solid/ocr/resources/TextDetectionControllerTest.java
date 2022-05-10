package com.solid.ocr.resources;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;
import java.util.Objects;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TextDetectionController.class)
class TextDetectionControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void textRecognizeGoodMorningImage() throws Exception {
        byte[] image = Objects.requireNonNull(this.getClass().getResourceAsStream("/assets/good-morning.png")).readAllBytes();

        mvc.perform(MockMvcRequestBuilders.multipart("/recognize")
                .file("file", image)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(containsString("GOOD")))
                .andExpect(content().string(containsString("MORNING")));
    }
}
