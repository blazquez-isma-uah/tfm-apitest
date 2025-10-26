package com.tfm.bandas.apis;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserApiClient {
    private final HttpClient client;
    private final String usersHost;
    private final String keycloakHost;
    private final String realm;
    private final String username;
    private final String password;

    public UserApiClient(String usersHost, String keycloakHost, String realm, String username, String password) {
        this.client = HttpClient.newHttpClient();
        this.usersHost = usersHost;
        this.keycloakHost = keycloakHost;
        this.realm = realm;
        this.username = username;
        this.password = password;
    }

    private HttpRequest.Builder baseRequest(String url) throws IOException, InterruptedException {
        String jwtToken = TokenApiClient.getToken(username, password, keycloakHost, realm, client, true);
        System.out.println("Usuario: " + username + " - Usando token de acceso: " + jwtToken);
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken);
    }

    // ==================== USERS ====================

    public String getAllUsers(String query) throws IOException, InterruptedException {
        String url = usersHost + "/api/users" + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getUserById(String id) throws IOException, InterruptedException {
        String url = usersHost + "/api/users/" + id;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getUserByEmail(String email) throws IOException, InterruptedException {
        String url = usersHost + "/api/users/email/" + email;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getUserByIamId(String iamId) throws IOException, InterruptedException {
        String url = usersHost + "/api/users/iam/" + iamId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String createUser(String jsonBody) throws IOException, InterruptedException {
        String url = usersHost + "/api/users";
        System.out.println("URL: " + url);
        System.out.println("Cuerpo de la solicitud: " + jsonBody);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String updateUser(String id, String jsonBody) throws IOException, InterruptedException {
        String url = usersHost + "/api/users/" + id;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String enableUser(String id) throws IOException, InterruptedException {
        String url = usersHost + "/api/users/" + id + "/enable";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).PUT(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String disableUser(String id) throws IOException, InterruptedException {
        String url = usersHost + "/api/users/" + id + "/disable";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).PUT(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String deleteUser(String id) throws IOException, InterruptedException {
        String url = usersHost + "/api/users/" + id;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String assignInstruments(String id, String jsonBody) throws IOException, InterruptedException {
        String url = usersHost + "/api/users/" + id + "/assign-instruments";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String searchUsers(String query) throws IOException, InterruptedException {
        String url = usersHost + "/api/users/search" + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getMyProfile() throws IOException, InterruptedException {
        String url = usersHost + "/api/users/me";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).PUT(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String testAuth() throws IOException, InterruptedException {
        String url = "http://localhost:8085" + "/users/api/users";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
    // ==================== INSTRUMENTS ====================

    public String getAllInstruments(String query) throws IOException, InterruptedException {
        String url = usersHost + "/api/instruments" + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getInstrumentById(String id) throws IOException, InterruptedException {
        String url = usersHost + "/api/instruments/" + id;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String createInstrument(String jsonBody) throws IOException, InterruptedException {
        String url = usersHost + "/api/instruments";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String deleteInstrument(String id) throws IOException, InterruptedException {
        String url = usersHost + "/api/instruments/" + id;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String searchInstruments(String query) throws IOException, InterruptedException {
        String url = usersHost + "/api/instruments/search" + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // ==================== ROLES ====================

    public String getAllRoles() throws IOException, InterruptedException {
        String url = usersHost + "/api/roles";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String createRole(String jsonBody) throws IOException, InterruptedException {
        String url = usersHost + "/api/roles";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String deleteRole(String roleName) throws IOException, InterruptedException {
        String url = usersHost + "/api/roles/" + roleName;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getRoleById(String id) throws IOException, InterruptedException {
        String url = usersHost + "/api/roles/" + id;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getRoleByName(String name) throws IOException, InterruptedException {
        String url = usersHost + "/api/roles/name/" + name;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String listUserRoles(String userId) throws IOException, InterruptedException {
        String url = usersHost + "/api/roles/user/" + userId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String assignRoleToUser(String userId, String roleName) throws IOException, InterruptedException {
        String url = usersHost + "/api/roles/user/" + userId + "/assign/" + roleName;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .PUT(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String removeRoleFromUser(String userId, String roleName) throws IOException, InterruptedException {
        String url = usersHost + "/api/roles/user/" + userId + "/remove/" + roleName;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .PUT(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
