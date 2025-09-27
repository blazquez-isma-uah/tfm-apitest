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
        System.out.println("Usando token JWT: " + jwtToken);
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken);
    }

    // ==================== USERS ====================

    public String getAllUsers(String query) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/users" + query).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getUserById(String id) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/users/" + id).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getUserByEmail(String email) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/users/email/" + email).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getUserByIamId(String iamId) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/users/iam/" + iamId).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String createUser(String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/users")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        System.out.println("Body de la request: " + jsonBody);
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String updateUser(String id, String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/users/" + id)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String enableUser(String id) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/users/" + id + "/enable").PUT(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String disableUser(String id) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/users/" + id + "/disable").PUT(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String deleteUser(String id) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/users/" + id).DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String assignInstruments(String id, String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/users/" + id + "/assign-instruments")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String searchUsers(String query) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/users/search" + query).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getMyProfile() throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/users/me").PUT(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // ==================== INSTRUMENTS ====================

    public String getAllInstruments(String query) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/instruments" + query).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getInstrumentById(String id) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/instruments/" + id).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String createInstrument(String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/instruments")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String deleteInstrument(String id) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/instruments/" + id).DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String searchInstruments(String query) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(usersHost + "/api/instruments/search" + query).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
