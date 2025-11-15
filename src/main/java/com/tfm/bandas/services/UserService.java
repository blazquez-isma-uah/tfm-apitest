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

    public String getUserByUsername(String username) throws Exception {
        return client.getUserByUsername(username);
    }

    public String getUserByIamId(String iamId) throws Exception {
        return client.getUserByIamId(iamId);
    }

    public String createUser(String email, String username, String password, String firstName, String lastName,
                             String secondLastName, String birthDate, String bandJoinDate, String systemSignupDate,
                             String phone, String notes, String profilePictureUrl, List<String> instrumentIds, List<String> rolesNames) throws Exception {

        String instrumentsJson = toJsonArray(instrumentIds, false);
        String rolesJson = toJsonArray(rolesNames, true);

        String jsonBody = String.format("""
        {
          "email": "%s",
          "username": "%s",
          "password": "%s",
          "firstName": "%s",
          "lastName": "%s",
          "secondLastName": "%s",
          "birthDate": "%s",
          "bandJoinDate": "%s",
          "systemSignupDate": "%s",
          "phone": "%s",
          "notes": "%s",
          "profilePictureUrl": "%s",
          "instrumentIds": %s,
          "roles": %s
        }
        """, email, username, password, firstName, lastName, secondLastName,
                requireIsoDate(birthDate, "birthDate"), requireIsoDate(bandJoinDate, "bandJoinDate"),
                requireIsoDate(systemSignupDate, "systemSignupDate"), phone, notes, profilePictureUrl, instrumentsJson, rolesJson);
        return client.createUser(jsonBody);
    }

    public String updateUser(String id, String firstName, String lastName,
                             String secondLastName, String email, String birthDate,
                             String bandJoinDate, String phone, String notes, String profilePictureUrl, int headerVersion) throws Exception {

        String jsonBody = String.format("""
        {
          "email": "%s",
          "firstName": "%s",
          "lastName": "%s",
          "secondLastName": "%s",
          "birthDate": "%s",
          "bandJoinDate": "%s",
          "phone": "%s",
          "notes": "%s",
          "profilePictureUrl": "%s"        }
        """, email, firstName, lastName, secondLastName, birthDate, bandJoinDate,
                phone, notes, profilePictureUrl);
        return client.updateUser(id, jsonBody, headerVersion);
    }

    public String enableUser(String id, int headerVersion) throws Exception {
        return client.enableUser(id, headerVersion);
    }

    public String disableUser(String id, int headerVersion) throws Exception {
        return client.disableUser(id, headerVersion);
    }

    public String deleteUser(String id, int headerVersion) throws Exception {
        return client.deleteUser(id, headerVersion);
    }

    public String searchUsers(String firstName, String lastName, String secondLastName, String email, Boolean active, Long instrumentId,
                              Integer page, Integer size, String sort) throws Exception {
        Map<String, String> params = new LinkedHashMap<>();
        putIfNotBlank(params, "firstName", firstName);
        putIfNotBlank(params, "lastName", lastName);
        putIfNotBlank(params, "secondLastName", secondLastName);
        putIfNotBlank(params, "email", email);
        putIfNotBlank(params, "active", String.valueOf(active));
        putIfNotBlank(params, "instrumentId", String.valueOf(instrumentId));
        putIfNotBlank(params, "page", String.valueOf(page));
        putIfNotBlank(params, "size", String.valueOf(size));
        putIfNotBlank(params, "sort", sort);

        return client.searchUsers(buildQuery(params));
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

    public String updateInstrument(String id, String instrumentName, String voice, int headerVersion) throws Exception {
        String jsonBody = String.format("""
        {
          "instrumentName": "%s",
          "voice": "%s"
        }
        """, instrumentName, voice);
        return client.updateInstrument(id, jsonBody, headerVersion);
    }

    public String deleteInstrument(String id, int headerVersion) throws Exception {
        return client.deleteInstrument(id, headerVersion);
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

    public String updateUserInstruments(String id, List<String> instruments, int headerVersion) throws Exception {
        String instrumentsJson = toJsonArray(instruments, false);
        return client.updateUserInstruments(id, instrumentsJson, headerVersion);
    }

    public String assignInstrumentToUser(String userId, String instrumentId, int headerVersion) throws Exception {
        return client.assignInstrumentToUser(userId, instrumentId, headerVersion);
    }

    public String removeInstrumentFromUser(String userId, String instrumentId, int headerVersion) throws Exception {
        return client.removeInstrumentFromUser(userId, instrumentId, headerVersion);
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

    public String deleteRole(String roleName) throws Exception {
        return client.deleteRole(roleName);
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

    public String listUserRolesByUsername(String username) throws Exception {
        return client.listUserRolesByUsername(username);
    }

    public String updateUserRoles(String userId, List<String> roleNames, int headerVersion) throws Exception {
        String rolesJson = toJsonArray(roleNames, true);
        return client.updateUserRoles(userId, rolesJson, headerVersion);
    }

    public String assignRoleToUser(String userId, String roleName, int headerVersion) throws Exception {
        return client.assignRoleToUser(userId, roleName, headerVersion);
    }

    public String removeRoleFromUser(String userId, String roleName, int headerVersion) throws Exception {
        return client.removeRoleFromUser(userId, roleName, headerVersion);
    }

}
