package com.tfm.bandas.services;

import com.tfm.bandas.apis.KeycloakApiClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class KeycloakService {

    private final KeycloakApiClient client;

    public KeycloakService(KeycloakApiClient client) {
        this.client = Objects.requireNonNull(client);
    }


    // ==================== ROLES ====================
    public String createRole(String name, String description) throws Exception {
        String jsonBody = String.format("""
        {
          "name": "%s",
          "description": "%s"
        }
        """, name, description == null ? "" : description);
        return client.createRole(jsonBody);
    }

    /** Obtiene información de un rol por su nombre. */
    public String getRoleInfoByName(String roleName) throws Exception {
        return client.getRoleInfo(roleName);
    }

    // ==================== USERS ====================

    /**
     * Crea un usuario con email verificado, enabled y credencial de password (no temporal).
     * createdTimestamp = ahora (ms).
     */
    public String createUser(String username,
                             String email,
                             String firstName,
                             String lastName,
                             String password) throws Exception {

        long nowMs = System.currentTimeMillis();
        String jsonBody = String.format("""
        {
          "createdTimestamp": %d,
          "username": "%s",
          "email": "%s",
          "firstName": "%s",
          "lastName": "%s",
          "emailVerified": true,
          "enabled": true,
          "credentials": [
            {
              "type": "password",
              "value": "%s",
              "temporary": false
            }
          ]
        }
        """, nowMs, username, email, firstName, lastName, password);

        return client.createUser(jsonBody);
    }

    /**
     * Actualiza campos básicos del usuario (email / firstName / lastName).
     * Los que vengan null no se incluyen en el body.
     */
    public String updateUser(String userId,
                             String newEmail,
                             String newFirstName,
                             String newLastName) throws Exception {

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        boolean wrote = false;

        if (newEmail != null) {
            sb.append("  \"email\": \"").append(newEmail).append("\"");
            wrote = true;
        }
        if (newFirstName != null) {
            if (wrote) sb.append(",\n");
            sb.append("  \"firstName\": \"").append(newFirstName).append("\"");
            wrote = true;
        }
        if (newLastName != null) {
            if (wrote) sb.append(",\n");
            sb.append("  \"lastName\": \"").append(newLastName).append("\"");
            wrote = true;
        }
        sb.append(wrote ? "\n}" : "}");

        return client.updateUser(userId, sb.toString());
    }

    /** Resetea/establece password del usuario (no temporal). */
    public String updateUserPassword(String userId, String newPassword, boolean temporary) throws Exception {
        String jsonBody = String.format("""
        {
          "type": "password",
          "value": "%s",
          "temporary": %s
        }
        """, newPassword, String.valueOf(temporary));
        return client.updateUserPassword(userId, jsonBody);
    }

    /** Borra usuario por ID. */
    public String deleteUser(String userId) throws Exception {
        return client.deleteUser(userId);
    }

    /** Obtiene información completa de usuario por ID. */
    public String getUserInfo(String userId) throws Exception {
        return client.getUserInfo(userId);
    }

    /** Busca usuario(s) por username (Keycloak devuelve array). */
    public String getUserInfoByUsername(String username) throws Exception {
        return client.getUserInfoByUsername(username);
    }

    // ==================== ROLE MAPPINGS (asignación de roles) ====================
    /**
     * Asigna roles de realm a un usuario.
     * Recibe listas paralelas de ids y names de rol (tal y como requiere la Admin API).
     */
    public String assignRealmRolesToUser(String userId, List<String> roleIds, List<String> roleNames) throws Exception {
        if (roleIds == null || roleNames == null || roleIds.size() != roleNames.size() || roleIds.isEmpty()) {
            throw new IllegalArgumentException("roleIds y roleNames deben tener el mismo tamaño y no estar vacíos");
        }

        StringBuilder json = new StringBuilder();
        json.append("[\n");
        for (int i = 0; i < roleIds.size(); i++) {
            json.append("  { \"id\": \"").append(roleIds.get(i)).append("\", \"name\": \"").append(roleNames.get(i)).append("\" }");
            if (i < roleIds.size() - 1) json.append(",\n"); else json.append("\n");
        }
        json.append("]");

        return client.assignRoleToUser(userId, json.toString());
    }

    /** Azúcar sintáctico para asignar un único rol de realm. */
    public String assignRealmRoleToUser(String userId, String roleId, String roleName) throws Exception {
        String jsonBody = String.format("""
        [
          { "id": "%s", "name": "%s" }
        ]
        """, roleId, roleName);
        return client.assignRoleToUser(userId, jsonBody);
    }
}
