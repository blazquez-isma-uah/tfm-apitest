package com.tfm.bandas.services;

import com.tfm.bandas.apis.SurveyApiClient;

public class SurveyService {
    private final SurveyApiClient client;

    public SurveyService(SurveyApiClient client) {
        this.client = client;
    }

    // ==================== Métodos de Encuestas ====================

    /**
     * Crear una nueva encuesta (requiere rol ADMIN)
     * @param eventId ID del evento asociado
     * @param title Título de la encuesta
     * @param description Descripción de la encuesta (opcional)
     * @param opensAt Fecha/hora de apertura en formato ISO-8601 (opcional)
     * @param closesAt Fecha/hora de cierre en formato ISO-8601 (opcional)
     */
    public String createSurvey(String eventId, String title, String description,
                               String opensAt, String closesAt) throws Exception {
        StringBuilder jsonBody = new StringBuilder();
        jsonBody.append("{\n");
        jsonBody.append("  \"eventId\": \"").append(eventId).append("\",\n");
        jsonBody.append("  \"title\": \"").append(title).append("\"");

        if (description != null && !description.isBlank()) {
            jsonBody.append(",\n  \"description\": \"").append(description).append("\"");
        }

        if (opensAt != null && !opensAt.isBlank()) {
            jsonBody.append(",\n  \"opensAt\": \"").append(opensAt).append("\"");
        }

        if (closesAt != null && !closesAt.isBlank()) {
            jsonBody.append(",\n  \"closesAt\": \"").append(closesAt).append("\"");
        }

        jsonBody.append("\n}");

        return client.createSurvey(jsonBody.toString());
    }

    /**
     * Obtener una encuesta por su ID
     * @param surveyId ID de la encuesta
     */
    public String getSurveyById(String surveyId) throws Exception {
        return client.getSurveyById(surveyId);
    }

    /**
     * Eliminar una encuesta (requiere rol ADMIN)
     * @param surveyId ID de la encuesta
     */
    public String deleteSurvey(String surveyId) throws Exception {
        return client.deleteSurvey(surveyId);
    }

    /**
     * Abrir una encuesta para que los usuarios puedan responder (requiere rol ADMIN)
     * @param surveyId ID de la encuesta
     */
    public String openSurvey(String surveyId) throws Exception {
        return client.openSurvey(surveyId);
    }

    /**
     * Cerrar una encuesta para que no se acepten más respuestas (requiere rol ADMIN)
     * @param surveyId ID de la encuesta
     */
    public String closeSurvey(String surveyId) throws Exception {
        return client.closeSurvey(surveyId);
    }

    /**
     * Cancelar una encuesta (requiere rol ADMIN)
     * @param surveyId ID de la encuesta
     */
    public String cancelSurvey(String surveyId) throws Exception {
        return client.cancelSurvey(surveyId);
    }

    /**
     * Listar todas las encuestas abiertas de un evento específico
     * @param eventId ID del evento
     */
    public String listOpenSurveysByEventId(String eventId) throws Exception {
        return client.listOpenSurveysByEventId(eventId);
    }

    /**
     * Listar todas las encuestas de un evento específico (abiertas y cerradas)
     * @param eventId ID del evento
     */
    public String listAllSurveysByEventId(String eventId) throws Exception {
        return client.listAllSurveysByEventId(eventId);
    }

    /**
     * Responder a una encuesta con respuesta Yes/No/Maybe
     * @param surveyId ID de la encuesta
     * @param answer Respuesta: "YES", "NO", o "MAYBE"
     * @param comment Comentario adicional (opcional)
     */
    public String respondToSurvey(String surveyId, String answer, String comment) throws Exception {
        StringBuilder jsonBody = new StringBuilder();
        jsonBody.append("{\n");
        jsonBody.append("  \"answer\": \"").append(answer).append("\"");

        if (comment != null && !comment.isBlank()) {
            jsonBody.append(",\n  \"comment\": \"").append(comment).append("\"");
        }

        jsonBody.append("\n}");

        return client.respondToSurvey(surveyId, jsonBody.toString());
    }

    /**
     * Obtener resultados agregados (conteo) de una encuesta Yes/No/Maybe (requiere rol ADMIN)
     * @param surveyId ID de la encuesta
     */
    public String getYesNoMaybeResults(String surveyId) throws Exception {
        return client.getYesNoMaybeResults(surveyId);
    }

    /**
     * Obtener resultados completos (todas las respuestas individuales) de una encuesta (requiere rol ADMIN)
     * @param surveyId ID de la encuesta
     */
    public String getCompleteResults(String surveyId) throws Exception {
        return client.getCompleteResults(surveyId);
    }
}

