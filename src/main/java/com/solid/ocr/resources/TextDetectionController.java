package com.solid.ocr.resources;

import com.solid.ocr.service.TextDetectionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/recognize")
public class TextDetectionController {

    private final TextDetectionService textDetectionService;

    public TextDetectionController(TextDetectionService textDetectionService) {
        this.textDetectionService = textDetectionService;
    }

    @PostMapping
    public String recognize(@RequestParam("file") MultipartFile file) {
        MultipartFileWrapper imageFile = new MultipartFileWrapper(file);
        return textDetectionService.recognize(imageFile);
    }
}
