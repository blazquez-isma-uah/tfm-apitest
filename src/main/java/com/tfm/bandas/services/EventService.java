package com.tfm.bandas.services;

import com.tfm.bandas.apis.EventApiClient;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.tfm.bandas.Utils.*;

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
        putIfNotBlank(params,"page", String.valueOf(page));
        putIfNotBlank(params, "size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);
        return client.listEvents(buildQuery(params));
    }

    public String listPastEvents(String before, Integer page, Integer size, String sort) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        putIfNotBlank(params, "before", before);
        putIfNotBlank(params, "page", String.valueOf(page));
        putIfNotBlank(params, "size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);
        return client.listPastEvents(buildQuery(params));
    }

    public String privateCalendar(String from, String to, String tz, Integer page, Integer size, String sort) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        putIfNotBlank(params, "from", from);
        putIfNotBlank(params, "to", to);
        putIfNotBlank(params, "tz", tz);
        putIfNotBlank(params, "page", String.valueOf(page));
        putIfNotBlank(params, "size", String.valueOf(size));
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
        putIfNotBlank(params, "page", String.valueOf(page));
        putIfNotBlank(params, "size", String.valueOf(size));
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
        putIfNotBlank(params, "page", String.valueOf(page));
        putIfNotBlank(params, "size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);
        return client.publicCalendar(buildQuery(params));
    }
}