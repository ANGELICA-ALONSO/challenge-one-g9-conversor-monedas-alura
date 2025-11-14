package com.alura.model;

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
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("[");
            for (int i = 0; i < entries.size(); i++) {
                fw.write(entries.get(i).toJson());
                if (i < entries.size() - 1) fw.write(",\n");
            }
            fw.write("]");
            fw.flush();
        }
    }
}
