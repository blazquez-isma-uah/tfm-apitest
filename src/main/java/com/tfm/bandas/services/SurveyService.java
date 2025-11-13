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
    public String deleteSurvey(String surveyId, int headerVersion) throws Exception {
        return client.deleteSurvey(surveyId, headerVersion);
    }

    /**
     * Actualizar una encuesta (requiere rol ADMIN)
     * @param surveyId ID de la encuesta
     * @param title Nuevo título de la encuesta
     * @param description Nueva descripción de la encuesta (opcional)
     * @param opensAt Nueva fecha/hora de apertura en formato ISO-8601
     * @param closesAt Nueva fecha/hora de cierre en formato ISO-8601
     */
    public String updateSurvey(String surveyId, String title, String description,
                               String opensAt, String closesAt, int headerVersion) throws Exception {
        StringBuilder jsonBody = new StringBuilder();
        jsonBody.append("{\n");
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
        return client.updateSurvey(surveyId, jsonBody.toString(), headerVersion);
    }

    /**
     * Abrir una encuesta para que los usuarios puedan responder (requiere rol ADMIN)
     * @param surveyId ID de la encuesta
     */
    public String openSurvey(String surveyId, int headerVersion) throws Exception {
        return client.openSurvey(surveyId, headerVersion);
    }

    /**
     * Cerrar una encuesta para que no se acepten más respuestas (requiere rol ADMIN)
     * @param surveyId ID de la encuesta
     */
    public String closeSurvey(String surveyId, int headerVersion) throws Exception {
        return client.closeSurvey(surveyId, headerVersion);
    }

    /**
     * Cancelar una encuesta (requiere rol ADMIN)
     * @param surveyId ID de la encuesta
     */
    public String cancelSurvey(String surveyId, int headerVersion) throws Exception {
        return client.cancelSurvey(surveyId, headerVersion);
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
     * Buscar encuestas con filtros opcionales
     * @param qText Texto de búsqueda en título y descripción (opcional)
     * @param title Título de la encuesta (opcional)
     * @param description Descripción de la encuesta (opcional)
     *                    @param eventId ID del evento (opcional)
     * @param status Estado de la encuesta: "OPEN", "CLOSED", "CANCELLED" (opcional)
     * @param opensFrom Fecha/hora de apertura desde en formato ISO-8601 (opcional)
     * @param opensTo Fecha/hora de apertura hasta en formato ISO-8601 (opcional)
     * @param closesFrom Fecha/hora de cierre desde en formato ISO-8601 (opcional)
     * @param closesTo Fecha/hora de cierre hasta en formato ISO-8601 (opcional)
     */
    public String searchSurveys(String qText, String title, String description, String eventId,
                                String status, String opensFrom, String opensTo,
                                String closesFrom, String closesTo) throws Exception {
        StringBuilder queryParams = new StringBuilder("?");
        if (qText != null && !qText.isBlank()) {
            queryParams.append("qText=").append(qText).append("&");
        }
        if (title != null && !title.isBlank()) {
            queryParams.append("title=").append(title).append("&");
        }
        if (description != null && !description.isBlank()) {
            queryParams.append("description=").append(description).append("&");
        }
        if (eventId != null && !eventId.isBlank()) {
            queryParams.append("eventId=").append(eventId).append("&");
        }
        if (status != null && !status.isBlank()) {
            queryParams.append("status=").append(status).append("&");
        }
        if (opensFrom != null && !opensFrom.isBlank()) {
            queryParams.append("opensFrom=").append(opensFrom).append("&");
        }
        if (opensTo != null && !opensTo.isBlank()) {
            queryParams.append("opensTo=").append(opensTo).append("&");
        }
        if (closesFrom != null && !closesFrom.isBlank()) {
            queryParams.append("closesFrom=").append(closesFrom).append("&");
        }
        if (closesTo != null && !closesTo.isBlank()) {
            queryParams.append("closesTo=").append(closesTo).append("&");
        }
        // Eliminar el último '&' o '?' si no se agregaron parámetros
        if (queryParams.charAt(queryParams.length() - 1) == '&' || queryParams.charAt(queryParams.length() - 1) == '?') {
            queryParams.deleteCharAt(queryParams.length() - 1);
        }
        return client.searchSurveys(queryParams.toString());
    }

    /**
     * Responder a una encuesta con respuesta Yes/No/Maybe
     * @param surveyId ID de la encuesta
     * @param answer Respuesta: "YES", "NO", o "MAYBE"
     * @param comment Comentario adicional (opcional)
     */
    public String respondToSurvey(String surveyId, String answer, String comment, int headerVersion) throws Exception {
        StringBuilder jsonBody = new StringBuilder();
        jsonBody.append("{\n");
        jsonBody.append("  \"answer\": \"").append(answer).append("\"");

        if (comment != null && !comment.isBlank()) {
            jsonBody.append(",\n  \"comment\": \"").append(comment).append("\"");
        }

        jsonBody.append("\n}");

        return client.respondToSurvey(surveyId, headerVersion, jsonBody.toString());
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

    /**
     * Obtener mi respuesta a una encuesta específica
     * @param surveyId ID de la encuesta
     */
    public String getMySurveyResponse(String surveyId) throws Exception {
        return client.getMySurveyResponse(surveyId);
    }

    /**
     * Actualizar mi respuesta a una encuesta específica
     * @param surveyId ID de la encuesta
     * @param answer Nueva respuesta: "YES", "NO", o "MAYBE"
     * @param comment Nuevo comentario adicional (opcional)
     */
    public String updateMySurveyResponse(String surveyId, String answer, String comment, int headerVersion) throws Exception {
        StringBuilder jsonBody = new StringBuilder();
        jsonBody.append("{\n");
        jsonBody.append("  \"answer\": \"").append(answer).append("\"");

        if (comment != null && !comment.isBlank()) {
            jsonBody.append(",\n  \"comment\": \"").append(comment).append("\"");
        }

        jsonBody.append("\n}");

        return client.updateMySurveyResponse(surveyId, headerVersion, jsonBody.toString());
    }

    /**
     * Eliminar mi respuesta a una encuesta específica
     * @param surveyId ID de la encuesta
     */
    public String deleteMySurveyResponse(String surveyId, int headerVersion) throws Exception {
        return client.deleteMySurveyResponse(surveyId, headerVersion);
    }

    /**
     * Actualizar respuesta de un usuario a una encuesta específica (requiere rol ADMIN)
     * @param surveyId ID de la encuesta
     * @param userId ID del usuario
     * @param answer Nueva respuesta: "YES", "NO", o "MAYBE"
     * @param comment Nuevo comentario adicional (opcional)
     */
    public String updateUserSurveyResponse(String surveyId, String userId, String answer, String comment, int headerVersion) throws Exception {
        StringBuilder jsonBody = new StringBuilder();
        jsonBody.append("{\n");
        jsonBody.append("  \"answer\": \"").append(answer).append("\"");

        if (comment != null && !comment.isBlank()) {
            jsonBody.append(",\n  \"comment\": \"").append(comment).append("\"");
        }

        jsonBody.append("\n}");

        return client.updateUserSurveyResponse(surveyId, userId, headerVersion, jsonBody.toString());
    }

    /**
     * Eliminar respuesta de un usuario a una encuesta específica (requiere rol ADMIN)
     * @param surveyId ID de la encuesta
     * @param userId ID del usuario
     */
    public String deleteUserSurveyResponse(String surveyId, String userId, int headerVersion) throws Exception {
        return client.deleteUserSurveyResponse(surveyId, userId, headerVersion);
    }

}


