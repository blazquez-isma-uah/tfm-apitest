package com.tfm.bandas.apis;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KeycloakApiClient {
    private final HttpClient client;
    private final String keycloakHost;
    private final String realm;
    private String clientId;
    private String clientSecret;

    public KeycloakApiClient(String keycloakHost, String realm, String clientId, String clientSecret) {
        this.client = HttpClient.newHttpClient();
        this.keycloakHost = keycloakHost;
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    private HttpRequest.Builder baseRequest(String url) throws IOException, InterruptedException {
        String accessToken = TokenApiClient.getToken(clientId, clientSecret, keycloakHost, realm, client, false);
        System.out.println("Client ID: " + clientId + " - Usando token de acceso: " + accessToken);
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken);
    }

    // ==================== ROLES ====================
    public String createRole(String jsonBody) throws IOException, InterruptedException {
        String url = keycloakHost + "/admin/realms/" + realm + "/roles";
        System.out.println("URL: " + url);
        System.out.println("Cuerpo de la solicitud: " + jsonBody);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getRoleInfo(String roleName) throws IOException, InterruptedException {
        String url = keycloakHost + "/admin/realms/" + realm + "/roles/" + roleName;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // ==================== USERS ====================
    public String createUser(String jsonBody) throws IOException, InterruptedException {
        String url = keycloakHost + "/admin/realms/" + realm + "/users";
        System.out.println("URL: " + url);
        System.out.println("Cuerpo de la solicitud: " + jsonBody);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String assignRoleToUser(String userId, String jsonBody) throws IOException, InterruptedException {
        String url = keycloakHost + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String removeRoleFromUser(String userId, String jsonBody) throws IOException, InterruptedException {
        String url = keycloakHost + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .method("DELETE", HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getUserRoles(String userId) throws IOException, InterruptedException {
        String url = keycloakHost + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String updateUser(String userId, String jsonBody) throws IOException, InterruptedException {
        String url = keycloakHost + "/admin/realms/" + realm + "/users/" + userId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String updateUserPassword(String userId, String jsonBody) throws IOException, InterruptedException {
        String url = keycloakHost + "/admin/realms/" + realm + "/users/" + userId + "/reset-password";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String deleteUser(String userId) throws IOException, InterruptedException {
        String url = keycloakHost + "/admin/realms/" + realm + "/users/" + userId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getUserInfo(String userId) throws IOException, InterruptedException {
        String url = keycloakHost + "/admin/realms/" + realm + "/users/" + userId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getUserInfoByUsername(String username) throws IOException, InterruptedException {
        String url = String.format("%s/admin/realms/%s/users?username=%s", keycloakHost, realm, username);
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
