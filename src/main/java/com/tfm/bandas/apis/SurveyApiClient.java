package com.tfm.bandas.apis;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.tfm.bandas.Utils.API_SURVEYS;

public class SurveyApiClient {

    private final HttpClient client;
    private final String keycloakHost;
    private final String realm;
    private final String surveysHost;
    private final String username;
    private final String password;

    public SurveyApiClient(String keycloakHost, String realm, String surveysHost, String username, String password) {
        this.client = HttpClient.newHttpClient();
        this.keycloakHost = keycloakHost;
        this.realm = realm;
        this.surveysHost = surveysHost;
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

    // ==================== Endpoints de Encuestas ====================

    // POST /api/surveys - Crear encuesta (ADMIN)
    public String createSurvey(String jsonBody) throws IOException, InterruptedException {
        String url = surveysHost + API_SURVEYS;
        System.out.println("URL: " + url);
        System.out.println("Cuerpo de la solicitud: " + jsonBody);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // GET /api/surveys/{surveyId} - Obtener encuesta por ID (ADMIN, MUSICIAN)
    public String getSurveyById(String surveyId) throws IOException, InterruptedException {
        String url = surveysHost + API_SURVEYS + "/" + surveyId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // DELETE /api/surveys/{surveyId} - Eliminar encuesta (ADMIN)
    public String deleteSurvey(String surveyId) throws IOException, InterruptedException {
        String url = surveysHost + API_SURVEYS + "/" + surveyId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // POST /api/surveys/{surveyId}/open - Abrir encuesta (ADMIN)
    public String openSurvey(String surveyId) throws IOException, InterruptedException {
        String url = surveysHost + API_SURVEYS + "/" + surveyId + "/open";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // POST /api/surveys/{surveyId}/close - Cerrar encuesta (ADMIN)
    public String closeSurvey(String surveyId) throws IOException, InterruptedException {
        String url = surveysHost + API_SURVEYS + "/" + surveyId + "/close";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // POST /api/surveys/{surveyId}/cancel - Cancelar encuesta (ADMIN)
    public String cancelSurvey(String surveyId) throws IOException, InterruptedException {
        String url = surveysHost + API_SURVEYS + "/" + surveyId + "/cancel";
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // GET /api/surveys/listOpen/{eventId} - Listar encuestas abiertas por evento (ADMIN, MUSICIAN)
    public String listOpenSurveysByEventId(String eventId) throws IOException, InterruptedException {
        String url = surveysHost + API_SURVEYS + "/listOpen/" + eventId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // GET /api/surveys/listAll/{eventId} - Listar todas las encuestas por evento (ADMIN, MUSICIAN)
    public String listAllSurveysByEventId(String eventId) throws IOException, InterruptedException {
        String url = surveysHost + API_SURVEYS + "/listAll/" + eventId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // POST /api/surveys/{surveyId}/responses - Responder a encuesta (ADMIN, MUSICIAN)
    public String respondToSurvey(String surveyId, String jsonBody) throws IOException, InterruptedException {
        String url = surveysHost + API_SURVEYS + "/raesponses/" + surveyId;
        System.out.println("URL: " + url);
        System.out.println("Cuerpo de la solicitud: " + jsonBody);
        HttpRequest request = baseRequest(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // GET /api/surveys/yesNoMaybeResults/{surveyId} - Obtener resultados Yes/No/Maybe (ADMIN)
    public String getYesNoMaybeResults(String surveyId) throws IOException, InterruptedException {
        String url = surveysHost + API_SURVEYS + "/raesponses/yesNoMaybeResults/" + surveyId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // GET /api/surveys/completeResults/{surveyId} - Obtener resultados completos (ADMIN)
    public String getCompleteResults(String surveyId) throws IOException, InterruptedException {
        String url = surveysHost + API_SURVEYS + "/responses/completeResults/" + surveyId;
        System.out.println("URL: " + url);
        HttpRequest request = baseRequest(url)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}

