package com.solid.ocr.storage;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

public class CotesStorageLocal implements ICotesStorage {

    private final long limit;
    private AtomicLong usage;
    private LocalDate lastUsage;

    public CotesStorageLocal(long limit) {
        this.limit = limit;
        usage = new AtomicLong(limit);
    }

    private void restartLimit() {
        usage = new AtomicLong(limit);
    }

    @Override
    public void decrementLimit() {
        usage.decrementAndGet();
        lastUsage = LocalDate.now();
    }

    @Override
    public long getAvailableCotes() {
        restartEachMonth();
        return usage.get();
    }

    private void restartEachMonth() {
        if (lastUsage.getMonth().getValue() < LocalDate.now().getMonth().getValue()) {
            restartLimit();
        }
    }
}
