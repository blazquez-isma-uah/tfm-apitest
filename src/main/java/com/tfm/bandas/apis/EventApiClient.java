package com.tfm.bandas.apis;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
        System.out.println("Usando token: " + jwtToken);
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken);
    }

    // ==================== Endpoints de Eventos ====================

    public String createEvent(String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(eventsHost + "/api/events")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String updateEvent(String id, String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(eventsHost + "/api/events/" + id)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String deleteEvent(String id) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(eventsHost + "/api/events/" + id)
                .DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getEventById(String id) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(eventsHost + "/api/events/" + id)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // Ahora recibe el query completo (incluyendo el '?' inicial)
    public String listEvents(String query) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(eventsHost + "/api/events" + query)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String listPastEvents(String query) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(eventsHost + "/api/events/past" + query)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String privateCalendar(String query) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(eventsHost + "/api/events/calendar" + query)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String searchEvents(String query) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(eventsHost + "/api/events/search" + query)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getScores(String eventId) throws IOException, InterruptedException {
        HttpRequest request = baseRequest(eventsHost + "/api/events/" + eventId + "/scores")
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // Público: sin Authorization
    public String publicCalendar(String query) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(eventsHost + "/api/public/events/calendar" + query))
                .header("Content-Type", "application/json")
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
