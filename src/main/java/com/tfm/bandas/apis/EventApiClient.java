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
        System.out.println("Usuario: " + username + " - Usando token de acceso: " + jwtToken);
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken);
    }

    // ==================== Endpoints de Eventos ====================

    public String createEvent(String jsonBody) throws IOException, InterruptedException {
        String url = eventsHost + "/api/events";
        System.out.println("URL: " + url);
        System.out.println("Cuerpo de la solicitud: " + jsonBody);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String updateEvent(String id, String jsonBody) throws IOException, InterruptedException {
        String url = eventsHost + "/api/events/" + id;
        System.out.println("URL: " + url);
        System.out.println("Cuerpo de la solicitud: " + jsonBody);
        HttpRequest request = baseRequest(url)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String deleteEvent(String id) throws IOException, InterruptedException {
        String url = eventsHost + "/api/events/" + id;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getEventById(String id) throws IOException, InterruptedException {
        String url = eventsHost + "/api/events/" + id;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // Ahora recibe el query completo (incluyendo el '?' inicial)
    public String listEvents(String query) throws IOException, InterruptedException {
        String url = eventsHost + "/api/events" + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String listPastEvents(String query) throws IOException, InterruptedException {
        String url = eventsHost + "/api/events/past" + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String privateCalendar(String query) throws IOException, InterruptedException {
        String url = eventsHost + "/api/events/calendar" + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String searchEvents(String query) throws IOException, InterruptedException {
        String url = eventsHost + "/api/events/search" + query;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getScores(String eventId) throws IOException, InterruptedException {
        String url = eventsHost + "/api/events/" + eventId + "/scores";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // Público: sin Authorization
    public String publicCalendar(String query) throws IOException, InterruptedException {
        String url = eventsHost + "/api/public/events/calendar" + query;
        System.out.println("URL: " + url);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
