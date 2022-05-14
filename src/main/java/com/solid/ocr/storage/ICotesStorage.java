package com.solid.ocr.storage;

public interface ICotesStorage {

    void decrementLimit();

    long getAvailableCotes();
}
