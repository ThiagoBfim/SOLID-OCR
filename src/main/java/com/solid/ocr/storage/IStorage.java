package com.solid.ocr.storage;

import java.util.Optional;

public interface IStorage {

    void decrementLimit();

    long getAvailableCotes();

    void saveOCR(String hashImage, String text);

    Optional<String> getOCRFromStorage(String hashImage);

}
