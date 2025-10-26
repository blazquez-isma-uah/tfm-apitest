package com.tfm.bandas.services;

import com.tfm.bandas.apis.UserApiClient;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.tfm.bandas.Utils.*;

public class UserService {

    private final UserApiClient client;

    public UserService(UserApiClient client) {
        this.client = client;
    }

    // ==================== USERS ====================

    public String getAllUsers(Integer page, Integer size, String sort) throws Exception {
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

    public String createUser(String email, String username, String password, String firstName, String lastName, String role,
                             String secondLastName, String birthDate, String bandJoinDate, String systemSignupDate,
                             String phone, String notes, String profilePictureUrl, List<String> instrumentIds) throws Exception {

        StringBuilder instrumentsJson = new StringBuilder("[");
        for (int i = 0; i < instrumentIds.size(); i++) {
            instrumentsJson.append(instrumentIds.get(i));
            if (i < instrumentIds.size() - 1) instrumentsJson.append(",");
        }
        instrumentsJson.append("]");

        String jsonBody = String.format("""
        {
          "email": "%s",
          "username": "%s",
          "password": "%s",
          "firstName": "%s",
          "lastName": "%s",
          "role": "%s",
          "secondLastName": "%s",
          "birthDate": "%s",
          "bandJoinDate": "%s",
          "systemSignupDate": "%s",
          "phone": "%s",
          "notes": "%s",
          "profilePictureUrl": "%s",
          "instrumentIds": %s
        }
        """, email, username, password, firstName, lastName, role, secondLastName,
                requireIsoDate(birthDate, "birthDate"), requireIsoDate(bandJoinDate, "bandJoinDate"),
                requireIsoDate(systemSignupDate, "systemSignupDate"), phone, notes, profilePictureUrl, instrumentsJson);
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

    public String searchUsers(String firstName, String lastName, String email, Boolean active, Long instrumentId,
                              Integer page, Integer size, String sort) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        putIfNotBlank(params, "firstName", firstName);
        putIfNotBlank(params, "lastName", lastName);
        putIfNotBlank(params, "email", email);
        putIfNotBlank(params, "active", String.valueOf(active));
        putIfNotBlank(params, "instrumentId", String.valueOf(instrumentId));
        putIfNotBlank(params, "page", String.valueOf(page));
        putIfNotBlank(params, "size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);

        return client.searchUsers(buildQuery(params));
    }

    public String testAuth() throws Exception {
        return client.testAuth();
    }

    public String getMyProfile() throws Exception {
        return client.getMyProfile();
    }

    // ==================== INSTRUMENTS ====================

    public String getAllInstruments(Integer page, Integer size, String sort) throws Exception {
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

    public String searchInstruments(String instrumentName, String voice, Integer page, Integer size, String sort) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        putIfNotBlank(params, "instrumentName", instrumentName);
        putIfNotBlank(params, "voice", voice);
        putIfNotBlank(params, "page", String.valueOf(page));
        putIfNotBlank(params, "size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);
        return client.searchInstruments(buildQuery(params));
    }

    // ==================== ROLES ====================
    public String getAllRoles() throws Exception {
        return client.getAllRoles();
    }

    public String createRole(String name, String description) throws Exception {
        String jsonBody = String.format("""
        {
          "name": "%s",
          "description": "%s"
        }
        """, name, description == null ? "" : description);
        return client.createRole(jsonBody);
    }

    public String deleteRole(String id) throws Exception {
        return client.deleteRole(id);
    }

    public String getRoleById(String id) throws Exception {
        return client.getRoleById(id);
    }

    public String getRoleByName(String name) throws Exception {
        return client.getRoleByName(name);
    }

    public String listUserRoles(String userId) throws Exception {
        return client.listUserRoles(userId);
    }

    public String assignRoleToUser(String userId, String roleName) throws Exception {
        return client.assignRoleToUser(userId, roleName);
    }

    public String removeRoleFromUser(String userId, String roleName) throws Exception {
        return client.removeRoleFromUser(userId, roleName);
    }

}
