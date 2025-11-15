package com.alura.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryEntry {
    private final String base;
    private final String target;
    private final double amount;
    private final double result;
    private final String timestamp; // ISO string for easy Gson serialization

    public HistoryEntry(String base, String target, double amount, double result) {
        this.base = base;
        this.target = target;
        this.amount = amount;
        this.result = result;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    // Getters (Gson can serialize fields directly, but getters are useful for tests)
    public String getBase() { return base; }
    public String getTarget() { return target; }
    public double getAmount() { return amount; }
    public double getResult() { return result; }
    public String getTimestamp() { return timestamp; }
}
