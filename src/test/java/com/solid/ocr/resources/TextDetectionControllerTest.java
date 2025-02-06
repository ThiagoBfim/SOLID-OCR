package com.solid.ocr.resources;

import com.solid.ocr.service.TextDetectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TextDetectionController.class)
class TextDetectionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private TextDetectionService textDetectionService;

    @Test
    public void textRecognizeGoodMorningImage() throws Exception {
        when(textDetectionService.recognize(any())).thenReturn("GOOD MORNING");
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "good-morning.png",
                        MediaType.APPLICATION_OCTET_STREAM_VALUE,
                        new byte[10]);

        mvc.perform(MockMvcRequestBuilders.multipart("/recognize")
                .file(file)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(containsString("GOOD")))
                .andExpect(content().string(containsString("MORNING")));
    }

    @Test
    public void textRecognizeWithWrongResource() throws Exception {
        when(textDetectionService.recognize(any())).thenReturn("GOOD MORNING");
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "good-morning",
                        MediaType.APPLICATION_OCTET_STREAM_VALUE,
                        new byte[10]);

        mvc.perform(MockMvcRequestBuilders.multipart("/recognize")
                .file(file)
                .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Wrong parameter")));
    }
}
