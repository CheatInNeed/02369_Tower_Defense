package io.github.cheatinneed.tower_defense.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cheatinneed.tower_defense.controller.JsonLineController;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonDataLoader {

    private final JsonLineController handler;

    public JsonDataLoader(JsonLineController handler) {
        this.handler = handler;
    }

    public void load(Path filePath) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip empty/comment lines if you want
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // Parse JSON from the line
                JsonObject json = JsonParser.parseString(line).getAsJsonObject();

                // Call your handler
                handler.handle(json);
            }
        }
    }
}

