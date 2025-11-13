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
        String surveyId = "83d8acb1-06b9-4b0e-ae95-a97e18e35cec";

        String result = surveyServiceAdmin.getSurveyById(surveyId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void DeleteSurvey() throws Exception {
        String surveyId = "83d8acb1-06b9-4b0e-ae95-a97e18e35cec";

        int ifMatchHeaderVersion = 10; // Current version of the survey

        String result = surveyServiceAdmin.deleteSurvey(surveyId, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void UpdateSurvey() throws Exception {
        String surveyId = "83d8acb1-06b9-4b0e-ae95-a97e18e35cec";
        String title = "Encuesta de prueba - Fiesta de fin de año";
        String description = "Prueba de encuesta para la fiesta de fin de año de la banda";
        String opensAt = "2025-11-01T10:00:00Z";
        String closesAt = "2025-12-05T23:59:59Z";

        int ifMatchHeaderVersion = 6; // Current version of the survey

        String result = surveyServiceAdmin.updateSurvey(surveyId, title, description, opensAt, closesAt, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }


    @Test
    public void OpenSurvey() throws Exception {
        String surveyId = "83d8acb1-06b9-4b0e-ae95-a97e18e35cec";

        int ifMatchHeaderVersion = 8; // Current version of the survey

        String result = surveyServiceAdmin.openSurvey(surveyId, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void CloseSurvey() throws Exception {
        String surveyId = "83d8acb1-06b9-4b0e-ae95-a97e18e35cec";

        int ifMatchHeaderVersion = 9; // Current version of the survey

        String result = surveyServiceAdmin.closeSurvey(surveyId, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void CancelSurvey() throws Exception {
        String surveyId = "83d8acb1-06b9-4b0e-ae95-a97e18e35cec";

        int ifMatchHeaderVersion = 2; // Current version of the survey

        String result = surveyServiceAdmin.cancelSurvey(surveyId, ifMatchHeaderVersion);
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
        String qText = "";
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
        String surveyId = "83d8acb1-06b9-4b0e-ae95-a97e18e35cec";
        String answer = "NO"; // Options: YES, NO, MAYBE
        String comment = "No podré asistir a la fiesta de fin de año.";

        SurveyService surveyService = new SurveyService(
                new SurveyApiClient(KEYCLOAK_HOST, REALM, SURVEYS_HOST, "iblazquezc", "123456")
        );

        int ifMatchHeaderVersion = 0; // Current version of the survey

        String result = surveyService.respondToSurvey(surveyId, answer, comment, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetYesNoMaybeResults() throws Exception {
        String surveyId = "83d8acb1-06b9-4b0e-ae95-a97e18e35cec";

        String result = surveyServiceAdmin.getYesNoMaybeResults(surveyId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetCompleteResults() throws Exception {
        String surveyId = "83d8acb1-06b9-4b0e-ae95-a97e18e35cec";

        String result = surveyServiceAdmin.getCompleteResults(surveyId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetMySurveyResponses() throws Exception {
        SurveyService surveyService = new SurveyService(
                new SurveyApiClient(KEYCLOAK_HOST, REALM, SURVEYS_HOST, "iblazquezc", "123456")
        );
        String surveyId = "83d8acb1-06b9-4b0e-ae95-a97e18e35cec";

        String result = surveyService.getMySurveyResponse(surveyId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void UpdateMySurveyResponse() throws Exception {
        SurveyService surveyService = new SurveyService(
                new SurveyApiClient(KEYCLOAK_HOST, REALM, SURVEYS_HOST, "iblazquezc", "123456")
        );

        String surveyId = "83d8acb1-06b9-4b0e-ae95-a97e18e35cec";
        String answer = "NO"; // Options: YES, NO, MAYBE
        String comment = "Lo siento, no podré asistir a la fiesta.";

        int ifMatchHeaderVersion = 1; // Current version of the survey

        String result = surveyService.updateMySurveyResponse(surveyId, answer, comment, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void DeleteMySurveyResponse() throws Exception {
        SurveyService surveyService = new SurveyService(
                new SurveyApiClient(KEYCLOAK_HOST, REALM, SURVEYS_HOST, "iblazquezc", "123456")
        );
        String surveyId = "83d8acb1-06b9-4b0e-ae95-a97e18e35cec";

        int ifMatchHeaderVersion = 2; // Current version of the survey

        String result = surveyService.deleteMySurveyResponse(surveyId, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void updateUserSurveyResponse() throws Exception {
        String surveyId = "83d8acb1-06b9-4b0e-ae95-a97e18e35cec";
        String userIamId = "dfc5304c-8539-436c-8214-bd98954380f5";
        String answer = "NO"; // Options: YES, NO, MAYBE
        String comment = "Lo siento, no podré asistir.";

        int ifMatchHeaderVersion = 0; // Current version of the survey

        String result = surveyServiceAdmin.updateUserSurveyResponse(surveyId, userIamId, answer, comment, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void deleteUserSurveyResponse() throws Exception {
        String surveyId = "83d8acb1-06b9-4b0e-ae95-a97e18e35cec";
        String userIamId = "dfc5304c-8539-436c-8214-bd98954380f5";

        int ifMatchHeaderVersion = 1; // Current version of the survey

        String result = surveyServiceAdmin.deleteUserSurveyResponse(surveyId, userIamId, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

}

