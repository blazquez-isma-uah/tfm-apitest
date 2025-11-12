package com.tfm.bandas;

import com.tfm.bandas.apis.SurveyApiClient;
import com.tfm.bandas.services.SurveyService;
import org.junit.jupiter.api.Test;

import static com.tfm.bandas.Utils.*;

public class SurveyServiceTest {

    private static final SurveyService surveyServiceAdmin = new SurveyService(
            new SurveyApiClient(KEYCLOAK_HOST, REALM, SURVEYS_HOST, ADMIN_USERNAME, ADMIN_PASSWORD)
    );

    private static final SurveyService surveyServiceMusician = new SurveyService(
            new SurveyApiClient(KEYCLOAK_HOST, REALM, SURVEYS_HOST, MUSICIAN_USERNAME, MUSICIAN_PASSWORD)
    );

    @Test
    public void CreateSurvey() throws Exception {
        String eventId = "509add0e-f91c-4060-9d90-4b964bea782e";
        String title = "Encuesta de prueba - Fiesta de fin de año";
        String description = "Prueba de encuesta para la fiesta de fin de año de la banda.";
        String opensAt = "2025-11-01T10:00:00Z";
        String closesAt = "2025-11-04T23:59:59Z";

        String result = surveyServiceAdmin.createSurvey(eventId, title, description, opensAt, closesAt);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetSurveyById() throws Exception {
        String surveyId = "7106452f-891e-43f0-9d99-101334cba20e";

        String result = surveyServiceAdmin.getSurveyById(surveyId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void DeleteSurvey() throws Exception {
        String surveyId = "1633d5fd-bf51-45cb-81ba-aeeb60d74436";

        String result = surveyServiceAdmin.deleteSurvey(surveyId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void UpdateSurvey() throws Exception {
        String surveyId = "0827473b-9e56-487d-9df4-e2afbd3ae827";
        String title = "Encuesta actualizada - Fiesta de fin de año";
        String description = "Encuesta actualizada para la fiesta de fin de año de la banda.";
        String opensAt = "2025-11-02T10:00:00Z";
        String closesAt = "2025-11-05T23:59:59Z";

        String result = surveyServiceAdmin.updateSurvey(surveyId, title, description, opensAt, closesAt);
        System.out.println(prettyPrintJson(result));
    }


    @Test
    public void OpenSurvey() throws Exception {
        String surveyId = "0827473b-9e56-487d-9df4-e2afbd3ae827";

        String result = surveyServiceAdmin.openSurvey(surveyId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void CloseSurvey() throws Exception {
        String surveyId = "0827473b-9e56-487d-9df4-e2afbd3ae827";

        String result = surveyServiceAdmin.closeSurvey(surveyId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void CancelSurvey() throws Exception {
        String surveyId = "7106452f-891e-43f0-9d99-101334cba20e";

        String result = surveyServiceAdmin.cancelSurvey(surveyId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void ListOpenSurveysByEventId() throws Exception {
        String eventId = "509add0e-f91c-4060-9d90-4b964bea782e";

        String result = surveyServiceMusician.listOpenSurveysByEventId(eventId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void ListAllSurveysByEventId() throws Exception {
        String eventId = "509add0e-f91c-4060-9d90-4b964bea782e";

        String result = surveyServiceAdmin.listAllSurveysByEventId(eventId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void SearchSurveys() throws Exception {
        String qText = "fiesta";
        String title = null;
        String description = null;
        String eventId = "509add0e-f91c-4060-9d90-4b964bea782e";
        String status = "OPEN";
        String opensFrom = null;
        String opensTo = null;
        String closesFrom = null;
        String closesTo = null;

        String result = surveyServiceAdmin.searchSurveys(
                qText, title, description, eventId, status,
                opensFrom, opensTo, closesFrom, closesTo
        );
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void RespondToSurvey() throws Exception {
        String surveyId = "1633d5fd-bf51-45cb-81ba-aeeb60d74436";
        String answer = "YES"; // Options: YES, NO, MAYBE
        String comment = null;

        SurveyService surveyService = new SurveyService(
                new SurveyApiClient(KEYCLOAK_HOST, REALM, SURVEYS_HOST, "lfernandezr", "123456")
        );

        String result = surveyService.respondToSurvey(surveyId, answer, comment);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetYesNoMaybeResults() throws Exception {
        String surveyId = "0827473b-9e56-487d-9df4-e2afbd3ae827";

        String result = surveyServiceAdmin.getYesNoMaybeResults(surveyId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetCompleteResults() throws Exception {
        String surveyId = "0827473b-9e56-487d-9df4-e2afbd3ae827";

        String result = surveyServiceAdmin.getCompleteResults(surveyId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetMySurveyResponses() throws Exception {
        SurveyService surveyService = new SurveyService(
                new SurveyApiClient(KEYCLOAK_HOST, REALM, SURVEYS_HOST, "lfernandezr", "123456")
        );

        String surveyId = "0827473b-9e56-487d-9df4-e2afbd3ae827";

        String result = surveyService.getMySurveyResponse(surveyId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void UpdateMySurveyResponse() throws Exception {
        SurveyService surveyService = new SurveyService(
                new SurveyApiClient(KEYCLOAK_HOST, REALM, SURVEYS_HOST, "lfernandezr", "123456")
        );

        String surveyId = "0827473b-9e56-487d-9df4-e2afbd3ae827";
        String answer = "MAYBE"; // Options: YES, NO, MAYBE
        String comment = "Tal vez pueda asistir, depende de mi agenda.";

        String result = surveyService.updateMySurveyResponse(surveyId, answer, comment);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void DeleteMySurveyResponse() throws Exception {
        SurveyService surveyService = new SurveyService(
                new SurveyApiClient(KEYCLOAK_HOST, REALM, SURVEYS_HOST, "lfernandezr", "123456")
        );
        String surveyId = "0827473b-9e56-487d-9df4-e2afbd3ae827";
        String result = surveyService.deleteMySurveyResponse(surveyId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void updateUserSurveyResponse() throws Exception {
        String surveyId = "7106452f-891e-43f0-9d99-101334cba20e";
        String userIamId = "musician1";
        String answer = "NO"; // Options: YES, NO, MAYBE
        String comment = "Lo siento, no podré asistir.";

        String result = surveyServiceAdmin.updateUserSurveyResponse(surveyId, userIamId, answer, comment);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void deleteUserSurveyResponse() throws Exception {
        String surveyId = "7106452f-891e-43f0-9d99-101334cba20e";
        String userIamId = "musician1";

        String result = surveyServiceAdmin.deleteUserSurveyResponse(surveyId, userIamId);
        System.out.println(prettyPrintJson(result));
    }

}

