package com.tfm.bandas.services;

import com.tfm.bandas.apis.UserApiClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserService {

    private final UserApiClient client;

    public UserService(UserApiClient client) {
        this.client = client;
    }

    // ==================== USERS ====================

    public String getAllUsers(int page, int size, String sort) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        putIfNotBlank(params, "page", String.valueOf(page));
        putIfNotBlank(params, "size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);
        return client.getAllUsers(buildQuery(params));
    }

    public String getUserById(String id) throws Exception {
        return client.getUserById(id);
    }

    public String getUserByEmail(String email) throws Exception {
        return client.getUserByEmail(email);
    }

    public String getUserByIamId(String iamId) throws Exception {
        return client.getUserByIamId(iamId);
    }

    public String createUser(String iamId, String username, String firstName, String lastName, String secondLastName,
                             String email, String birthDate, String bandJoinDate, String systemSignupDate,
                             String phone, String notes, String profilePictureUrl, List<String> instrumentIds) throws Exception {
        StringBuilder instrumentsJson = new StringBuilder("[");
        for (int i = 0; i < instrumentIds.size(); i++) {
            instrumentsJson.append(instrumentIds.get(i));
            if (i < instrumentIds.size() - 1) instrumentsJson.append(",");
        }
        instrumentsJson.append("]");

        String jsonBody = String.format("""
        {
          "iamId": "%s",
          "username": "%s",
          "firstName": "%s",
          "lastName": "%s",
          "secondLastName": "%s",
          "email": "%s",
          "birthDate": "%s",
          "bandJoinDate": "%s",
          "systemSignupDate": "%s",
          "phone": "%s",
          "notes": "%s",
          "profilePictureUrl": "%s",
          "instrumentIds": %s
        }
        """, iamId, username, firstName, lastName, secondLastName, email, birthDate, bandJoinDate,
                systemSignupDate, phone, notes, profilePictureUrl, instrumentsJson);
        return client.createUser(jsonBody);
    }

    public String updateUser(String id, String iamId, String username, String firstName, String lastName, String secondLastName,
                             String email, String birthDate, String bandJoinDate, String systemSignupDate,
                             String phone, String notes, String profilePictureUrl, List<String> instrumentIds) throws Exception {
        StringBuilder instrumentsJson = new StringBuilder("[");
        for (int i = 0; i < instrumentIds.size(); i++) {
            instrumentsJson.append("\"").append(instrumentIds.get(i)).append("\"");
            if (i < instrumentIds.size() - 1) instrumentsJson.append(",");
        }
        instrumentsJson.append("]");

        String jsonBody = String.format("""
        {
          "iamId": "%s",
          "username": "%s",
          "firstName": "%s",
          "lastName": "%s",
          "secondLastName": "%s",
          "email": "%s",
          "birthDate": "%s",
          "bandJoinDate": "%s",
          "systemSignupDate": "%s",
          "phone": "%s",
          "notes": "%s",
          "profilePictureUrl": "%s",
          "instrumentIds": %s
        }
        """, iamId, username, firstName, lastName, secondLastName, email, birthDate, bandJoinDate,
                systemSignupDate, phone, notes, profilePictureUrl, instrumentsJson.toString());
        return client.updateUser(id, jsonBody);
    }

    public String enableUser(String id) throws Exception {
        return client.enableUser(id);
    }

    public String disableUser(String id) throws Exception {
        return client.disableUser(id);
    }

    public String deleteUser(String id) throws Exception {
        return client.deleteUser(id);
    }

    public String assignInstruments(String id, List<String> instruments) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < instruments.size(); i++) {
            sb.append("\"").append(instruments.get(i)).append("\"");
            if (i < instruments.size() - 1) sb.append(",");
        }
        sb.append("]");
        return client.assignInstruments(id, sb.toString());
    }

    public String searchUsers(String firstName, String lastName, String email, boolean active, String instrumentId,
                              int page, int size, String sort) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        putIfNotBlank(params, "firstName", firstName);
        putIfNotBlank(params, "lastName", lastName);
        putIfNotBlank(params, "email", email);
        putIfNotBlank(params, "active", String.valueOf(active));
        putIfNotBlank(params, "instrumentId", instrumentId);
        putIfNotBlank(params, "page", String.valueOf(page));
        putIfNotBlank(params, "size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);

        return client.searchUsers(buildQuery(params));
    }

    public String getMyProfile() throws Exception {
        return client.getMyProfile();
    }

    // ==================== INSTRUMENTS ====================

    public String getAllInstruments(int page, int size, String sort) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        putIfNotBlank(params, "page", String.valueOf(page));
        putIfNotBlank(params, "size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);
        return client.getAllInstruments(buildQuery(params));
    }

    public String getInstrumentById(String id) throws Exception {
        return client.getInstrumentById(id);
    }

    public String createInstrument(String instrumentName, String voice) throws Exception {
        String jsonBody = String.format("""
        {
          "instrumentName": "%s",
          "voice": "%s"
        }
        """, instrumentName, voice);
        return client.createInstrument(jsonBody);
    }

    public String deleteInstrument(String id) throws Exception {
        return client.deleteInstrument(id);
    }

    public String searchInstruments(String instrumentName, String voice, int page, int size, String sort) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        putIfNotBlank(params, "firstName", instrumentName);
        putIfNotBlank(params, "lastName", voice);
        putIfNotBlank(params, "page", String.valueOf(page));
        putIfNotBlank(params, "size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);
        return client.searchInstruments(buildQuery(params));
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
