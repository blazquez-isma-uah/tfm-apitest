package com.tfm.bandas.apis;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.tfm.bandas.Utils.API_EVENTS;

public class EventApiClient {

    private final HttpClient client;
    private final String keycloakHost;
    private final String realm;
    private final String eventsHost;
    private final String username;
    private final String password;

    public EventApiClient(String keycloakHost, String realm, String eventsHost, String username, String password) {
        this.client = HttpClient.newHttpClient();
        this.keycloakHost = keycloakHost;
        this.realm = realm;
        this.eventsHost = eventsHost;
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

    // ==================== Endpoints de Eventos ====================

    public String createEvent(String jsonBody) throws IOException, InterruptedException {
        String url = eventsHost + API_EVENTS;
        System.out.println("URL: " + url);
        System.out.println("Cuerpo de la solicitud: " + jsonBody);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String updateEvent(String eventId, String jsonBody, int headerVersion) throws IOException, InterruptedException {
        String url = eventsHost + API_EVENTS + "/" + eventId;
        System.out.println("URL: " + url);
        System.out.println("Cuerpo de la solicitud: " + jsonBody);
        HttpRequest request = baseRequest(url).header("If-Match", "W/\"" + headerVersion + "\"")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String deleteEvent(String eventId, int headerVersion) throws IOException, InterruptedException {
        String url = eventsHost + API_EVENTS + "/" + eventId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url).header("If-Match", "W/\"" + headerVersion + "\"")
                .DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getEventById(String eventId) throws IOException, InterruptedException {
        String url = eventsHost + API_EVENTS + "/" + eventId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // Ahora recibe el query completo (incluyendo el '?' inicial)
    public String listEvents(String query) throws IOException, InterruptedException {
        String url = eventsHost + API_EVENTS + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String listPastEvents(String query) throws IOException, InterruptedException {
        String url = eventsHost + API_EVENTS + "/past" + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String privateCalendar(String query) throws IOException, InterruptedException {
        String url = eventsHost + API_EVENTS + "/calendar" + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String searchEvents(String query) throws IOException, InterruptedException {
        String url = eventsHost + API_EVENTS + "/search" + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getScores(String eventId) throws IOException, InterruptedException {
        String url = eventsHost + API_EVENTS + "/" + eventId + "/scores";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // Público: sin Authorization
    public String publicCalendar(String query) throws IOException, InterruptedException {
        String url = eventsHost + API_EVENTS + "/public/calendar" + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
