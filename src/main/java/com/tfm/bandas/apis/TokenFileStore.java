package com.tfm.bandas.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.*;

public class TokenFileStore {

    private final Path file;
    private final ObjectMapper mapper = new ObjectMapper();

    public TokenFileStore() {
        this(Paths.get("src", "main", "resources",
                ".bandas_token.json"));
    }

    public TokenFileStore(String fileName){
        this(Paths.get("src", "main", "resources",
                fileName));
    }

    public TokenFileStore(Path file) {
        this.file = file;
    }

    public TokenRecord read() {
        try {
            if (!Files.exists(file)) return null;
            byte[] bytes = Files.readAllBytes(file);
            if (bytes.length == 0) return null;
            return mapper.readValue(bytes, TokenRecord.class);
        } catch (IOException e) {
            return null;
        }
    }

    public void write(TokenRecord record) throws IOException {
        Path dir = file.getParent();
        if (dir != null && !Files.exists(dir)) {
            Files.createDirectories(dir);
        }
        byte[] bytes = mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(record);
        Files.write(file, bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    public static class TokenRecord {
        public String id;
        public String pass;
        public String token;

        public TokenRecord() {}

        public TokenRecord(String id, String pass, String token) {
            this.id = id;
            this.pass = pass;
            this.token = token;
        }
    }
}
