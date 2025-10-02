package com.tfm.bandas;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Utils {

    public static final String KEYCLOAK_HOST = "http://localhost:8080";
    public static final String USERS_HOST = "http://localhost:8081";
    public static final String EVENTS_HOST = "http://localhost:8083";
    public static final String REALM = "tfm-bandas";
    public static final String USERNAME = "adminbandas";
    public static final String PASSWORD = "admin123";
    public static final String CLIENT_ID = "realm-admin";
    public static final String CLIENT_SECRET = "8nWwBGuo0RxdRQfHUaQgcqv7Fibt8JYX";


    public static String prettyPrintJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object obj = mapper.readValue(json, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            return json;
        }
    }

    public static String extractFromResponse(String userResp, String field) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (userResp.trim().startsWith("[")) {
                List<?> list = mapper.readValue(userResp, List.class);
                if (list.isEmpty()) return null;
                Object firstElem = list.get(0);
                String json = mapper.writeValueAsString(firstElem);
                return mapper.readTree(json).path(field).asText();
            } else {
                return mapper.readTree(userResp).path(field).asText();
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String requireIsoDate(String input, String fieldName) {
        String iso = normalizeIsoDate(input);
        if (iso == null) {
            throw new IllegalArgumentException("Formato de fecha inválido para " + fieldName + ": " + input + " (use yyyy-MM-dd).");
        }
        return iso;
    }

    public static String normalizeIsoDate(String date) {
        if (date == null) return null;
        String s = date.trim();
        try {
            LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
            return s;
        } catch (Exception ignored) {}

        String[] patterns = {
                "dd-MM-uuuu", "dd/MM/uuuu", "dd.MM.uuuu",
                "uuuu/MM/dd", "uuuu.MM.dd", "ddMMyyyy", "uuuuMMdd"
        };
        for (String p : patterns) {
            try {
                DateTimeFormatter f = DateTimeFormatter.ofPattern(p).withResolverStyle(ResolverStyle.SMART);
                LocalDate ld = LocalDate.parse(s, f);
                return ld.toString();
            } catch (Exception ignored) {}
        }
        return null;
    }

    public static LocalDate stringTimestampToLocalDate(String createdTimestampStr) {
        if (createdTimestampStr != null && !createdTimestampStr.isEmpty()) {
            long createdTimestamp = Long.parseLong(createdTimestampStr);
            return Instant.ofEpochMilli(createdTimestamp).atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return LocalDate.now();
    }

    public static String normalize(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replace('ñ', 'n').replace('Ñ', 'N')
                .replaceAll("[^A-Za-z0-9]", "");
    }

    public static String pick(List<String> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public static String pickDistinct(List<String> list, String notThis) {
        String v;
        do {
            v = pick(list);
        } while (v.equals(notThis));
        return v;
    }

    // username: inicial nombre + apellido1 + inicial apellido2, normalizado y en minúsculas
    public static String buildUsernameBase(String firstName, String lastName, String secondLastName) {
        String base = firstName.charAt(0) + normalize(lastName) + (secondLastName.isEmpty() ? "" : String.valueOf(normalize(secondLastName).charAt(0)));
        return normalize(base).toLowerCase(Locale.ROOT);
    }

    public static LocalDate randomDateBetween(LocalDate start, LocalDate end) {
        Random rand = new Random();
        if (end.isBefore(start)) end = start;
        long days = ChronoUnit.DAYS.between(start, end);
        long add = days > 0 ? rand.nextLong(days + 1) : 0;
        return start.plusDays(add);
    }

    public static String randomPhoneEs() {
        Random rand = new Random();
        int first = rand.nextBoolean() ? 6 : 7;
        int rest = 10000000 + rand.nextInt(90000000);
        return first + String.valueOf(rest);
    }

    public static String ensureUnique(String base, Set<String> usados) {
        String u = base;
        int n = 1;
        while (!usados.add(u)) {
            n++;
            u = base + n;
        }
        return u;
    }

    public static List<String> pickRandomInstruments(int max, List<String> catalogInstruments) throws Exception {
        Random rand = new Random();
        int count = 1 + rand.nextInt(max);
        List<String> copy = new ArrayList<>(catalogInstruments);
        Collections.shuffle(copy, rand);
        return new ArrayList<>(copy.subList(0, Math.min(count, copy.size())));
    }

    // ==================== Helpers de query ====================
    public static void putIfNotBlank(Map<String, String> params, String key, String value) {
        if (value != null && !value.isBlank() && !value.equalsIgnoreCase("null")) {
            params.put(key, value);
        }
    }

    public static String buildQuery(Map<String, String> params) {
        if (params.isEmpty()) return "";
        StringBuilder sb = new StringBuilder("?");
        boolean first = true;
        for (Map.Entry<String, String> e : params.entrySet()) {
            if (!first) sb.append("&");
            sb.append(encode(e.getKey())).append("=").append(encode(e.getValue()));
            first = false;
        }
        return sb.toString();
    }

    public static String encode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
