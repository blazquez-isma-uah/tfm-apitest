package com.tfm.bandas.apis;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.tfm.bandas.Utils.*;

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
        String url = usersHost + API_USERS + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getUserById(String userId) throws IOException, InterruptedException {
        String url = usersHost + API_USERS + "/" + userId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getUserByEmail(String email) throws IOException, InterruptedException {
        String url = usersHost + API_USERS + "/email/" + email;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getUserByUsername(String username) throws IOException, InterruptedException {
        String url = usersHost + API_USERS + "/username/" + username;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getUserByIamId(String iamId) throws IOException, InterruptedException {
        String url = usersHost + API_USERS + "/iam/" + iamId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String createUser(String jsonBody) throws IOException, InterruptedException {
        String url = usersHost + API_USERS;
        System.out.println("URL: " + url);
        System.out.println("Cuerpo de la solicitud: " + jsonBody);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String updateUser(String userId, String jsonBody, int headerVersion) throws IOException, InterruptedException {
        String url = usersHost + API_USERS + "/" + userId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).header("If-Match", "W/\"" + headerVersion + "\"")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String enableUser(String userId, int headerVersion) throws IOException, InterruptedException {
        String url = usersHost + API_USERS + "/" + userId + "/enable";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).header("If-Match", "W/\"" + headerVersion + "\"")
                .PUT(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String disableUser(String userId, int headerVersion) throws IOException, InterruptedException {
        String url = usersHost + API_USERS + "/" + userId + "/disable";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).header("If-Match", "W/\"" + headerVersion + "\"")
                .PUT(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String deleteUser(String userId, int headerVersion) throws IOException, InterruptedException {
        String url = usersHost + API_USERS + "/" + userId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).header("If-Match", "W/\"" + headerVersion + "\"")
                .DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String searchUsers(String query) throws IOException, InterruptedException {
        String url = usersHost + API_USERS + "/search" + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getMyProfile() throws IOException, InterruptedException {
        String url = usersHost + API_USERS + "/me";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // ==================== INSTRUMENTS ====================
    public String getAllInstruments(String query) throws IOException, InterruptedException {
        String url = usersHost + API_INSTRUMENTS + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getInstrumentById(String instrumentId) throws IOException, InterruptedException {
        String url = usersHost + API_INSTRUMENTS + "/" + instrumentId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String createInstrument(String jsonBody) throws IOException, InterruptedException {
        String url = usersHost + API_INSTRUMENTS;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String updateInstrument(String instrumentId, String jsonBody, int headerVersion) throws IOException, InterruptedException {
        String url = usersHost + API_INSTRUMENTS + "/" + instrumentId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).header("If-Match", "W/\"" + headerVersion + "\"")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String deleteInstrument(String instrumentId, int headerVersion) throws IOException, InterruptedException {
        String url = usersHost + API_INSTRUMENTS + "/" + instrumentId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).header("If-Match", "W/\"" + headerVersion + "\"")
                .DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String searchInstruments(String query) throws IOException, InterruptedException {
        String url = usersHost + API_INSTRUMENTS + "/search" + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String updateUserInstruments(String userId, String jsonBody, int headerVersion) throws IOException, InterruptedException {
        String url = usersHost + API_INSTRUMENTS + "/user/" + userId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).header("If-Match", "W/\"" + headerVersion + "\"")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String assignInstrumentToUser(String userId, String instrumentId, int headerVersion) throws IOException, InterruptedException {
        String url = usersHost + API_INSTRUMENTS + "/user/" + userId + "/" + instrumentId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).header("If-Match", "W/\"" + headerVersion + "\"")
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String removeInstrumentFromUser(String userId, String instrumentId, int headerVersion) throws IOException, InterruptedException {
        String url = usersHost + API_INSTRUMENTS + "/user/" + userId + "/" + instrumentId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).header("If-Match", "W/\"" + headerVersion + "\"")
                .DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // ==================== ROLES ====================

    public String getAllRoles() throws IOException, InterruptedException {
        String url = usersHost + API_ROLES;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String createRole(String jsonBody) throws IOException, InterruptedException {
        String url = usersHost + API_ROLES;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String deleteRole(String roleName) throws IOException, InterruptedException {
        String url = usersHost + API_ROLES + "/" + roleName;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getRoleById(String roleId) throws IOException, InterruptedException {
        String url = usersHost + API_ROLES + "/" + roleId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getRoleByName(String name) throws IOException, InterruptedException {
        String url = usersHost + API_ROLES + "/name/" + name;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String listUserRoles(String userId) throws IOException, InterruptedException {
        String url = usersHost + API_ROLES + "/user/" + userId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String listUserRolesByUsername(String username) throws IOException, InterruptedException {
        String url = usersHost + API_ROLES + "/user/username/" + username;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String updateUserRoles(String userId, String jsonBody, int headerVersion) throws IOException, InterruptedException {
        String url = usersHost + API_ROLES + "/user/" + userId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).header("If-Match", "W/\"" + headerVersion + "\"")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String assignRoleToUser(String userId, String roleName, int headerVersion) throws IOException, InterruptedException {
        String url = usersHost + API_ROLES + "/user/" + userId + "/" + roleName;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).header("If-Match", "W/\"" + headerVersion + "\"")
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String removeRoleFromUser(String userId, String roleName, int headerVersion) throws IOException, InterruptedException {
        String url = usersHost + API_ROLES + "/user/" + userId + "/" + roleName;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).header("If-Match", "W/\"" + headerVersion + "\"")
                .DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
