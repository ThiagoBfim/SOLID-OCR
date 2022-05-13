package com.solid.ocr.textdetection;

import com.solid.ocr.resources.MultipartFileWrapper;
import org.springframework.lang.Nullable;


public abstract class AbstractTextOCR {

    @Nullable
    public abstract String retrieveTextFromImage(MultipartFileWrapper imageFile);

}
