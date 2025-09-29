package com.tfm.bandas.services;

import com.tfm.bandas.apis.EventApiClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class EventService {
    private final EventApiClient client;

    public EventService(EventApiClient client) {
        this.client = client;
    }

    public String createEvent(String title, String description, String location,
                              String localStart, String localEnd, String timeZone,
                              String type, String status, String visibility) throws Exception {
        String jsonBody = String.format("""
        {
          "title": "%s",
          "description": "%s",
          "location": "%s",
          "localStart": "%s",
          "localEnd": "%s",
          "timeZone": "%s",
          "type": "%s",
          "status": "%s",
          "visibility": "%s"
        }
        """, title, description, location, localStart, localEnd, timeZone, type, status, visibility);
        return client.createEvent(jsonBody);
    }

    public String updateEvent(String id, String title, String description, String location,
                              String localStart, String localEnd, String timeZone,
                              String type, String status, String visibility) throws Exception {
        String jsonBody = String.format("""
        {
          "title": "%s",
          "description": "%s",
          "location": "%s",
          "localStart": "%s",
          "localEnd": "%s",
          "timeZone": "%s",
          "type": "%s",
          "status": "%s",
          "visibility": "%s"
        }
        """, title, description, location, localStart, localEnd, timeZone, type, status, visibility);
        return client.updateEvent(id, jsonBody);
    }

    public String deleteEvent(String id) throws Exception {
        return client.deleteEvent(id);
    }

    public String getEventById(String id) throws Exception {
        return client.getEventById(id);
    }

    public String listEvents(String from, String to, Integer page, Integer size, String sort) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        putIfNotBlank(params, "from", from);
        putIfNotBlank(params, "to", to);
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);
        return client.listEvents(buildQuery(params));
    }

    public String listPastEvents(String before, Integer page, Integer size, String sort) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        putIfNotBlank(params, "before", before);
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);
        return client.listPastEvents(buildQuery(params));
    }

    public String privateCalendar(String from, String to, String tz, Integer page, Integer size, String sort) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        putIfNotBlank(params, "from", from);
        putIfNotBlank(params, "to", to);
        putIfNotBlank(params, "tz", tz);
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);
        return client.privateCalendar(buildQuery(params));
    }

    public String searchEvents(String qText, String title, String description, String location,
                               String timeZone, String type, String status, String visibility,
                               Integer page, Integer size, String sort) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        putIfNotBlank(params, "q", qText);
        putIfNotBlank(params, "title", title);
        putIfNotBlank(params, "description", description);
        putIfNotBlank(params, "locations", location);
        putIfNotBlank(params, "timeZone", timeZone);
        putIfNotBlank(params, "type", type);
        putIfNotBlank(params, "status", status);
        putIfNotBlank(params, "visibility", visibility);
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);
        return client.searchEvents(buildQuery(params));
    }

    public String getScores(String eventId) throws Exception {
        return client.getScores(eventId);
    }

    public String publicCalendar(String from, String to, String tz, Integer page, Integer size, String sort) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        putIfNotBlank(params, "from", from);
        putIfNotBlank(params, "to", to);
        putIfNotBlank(params, "tz", tz);
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);
        return client.publicCalendar(buildQuery(params));
    }

    // ==================== Helpers de query ====================
    private static void putIfNotBlank(Map<String, String> params, String key, String value) {
        if (value != null && !value.isBlank()) {
            params.put(key, value);
        }
    }

    private static String buildQuery(Map<String, String> params) {
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

    private static String encode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}