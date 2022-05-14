package com.solid.ocr.storage;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

public class CotesStorageLocal implements ICotesStorage {

    private final long limit;
    private AtomicLong usage;
    private LocalDate monthUsage;

    public CotesStorageLocal(long limit) {
        this.limit = limit;
        initialize();
    }

    private void initialize() {
        usage = new AtomicLong(limit);
        monthUsage = LocalDate.now();
    }

    @Override
    public void decrementLimit() {
        usage.decrementAndGet();
    }

    @Override
    public long getAvailableCotes() {
        restartEachMonth();
        return usage.get();
    }

    private void restartEachMonth() {
        if (monthUsage.getMonth().getValue() < LocalDate.now().getMonth().getValue()) {
            initialize();
        }
    }
}
