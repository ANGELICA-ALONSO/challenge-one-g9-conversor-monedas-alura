package com.alura.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryEntry {
    private final String base;
    private final String target;
    private final double amount;
    private final double result;
    private final LocalDateTime timestamp;

    public HistoryEntry(String base, String target, double amount, double result) {
        this.base = base;
        this.target = target;
        this.amount = amount;
        this.result = result;
        this.timestamp = LocalDateTime.now();
    }

    public String toJson() {
        DateTimeFormatter f = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"base\":\"").append(escape(base)).append("\",");
        sb.append("\"target\":\"").append(escape(target)).append("\",");
        sb.append("\"amount\":").append(amount).append(",");
        sb.append("\"result\":").append(result).append(",");
        sb.append("\"timestamp\":\"").append(timestamp.format(f)).append("\"");
        sb.append("}");
        return sb.toString();
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
