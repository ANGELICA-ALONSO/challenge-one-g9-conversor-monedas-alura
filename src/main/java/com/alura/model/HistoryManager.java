package com.alura.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private final List<HistoryEntry> entries = new ArrayList<>();

    public void add(HistoryEntry e) {
        entries.add(e);
    }

    public List<HistoryEntry> all() {
        return new ArrayList<>(entries);
    }

    public void saveToFile(String path) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fw = new FileWriter(path)) {
            gson.toJson(entries, fw);
            fw.flush();
        }
    }
}
