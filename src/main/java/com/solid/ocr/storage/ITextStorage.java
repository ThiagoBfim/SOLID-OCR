package com.solid.ocr.storage;

import java.util.Optional;

public interface ITextStorage {

    void saveOCR(String hashImage, String text);

    Optional<String> getOCRFromStorage(String hashImage);

}
