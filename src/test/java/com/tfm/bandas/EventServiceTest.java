package com.tfm.bandas;

import com.tfm.bandas.apis.EventApiClient;
import com.tfm.bandas.services.EventService;
import org.junit.jupiter.api.Test;

import static com.tfm.bandas.Utils.*;

public class EventServiceTest {

    private static final EventService eventService = new EventService(
            new EventApiClient(KEYCLOAK_HOST, REALM, EVENTS_HOST, ADMIN_USERNAME, ADMIN_PASSWORD)
    );

    @Test
    public void CreateEvent() throws Exception {
        String title = "Reunión celebración fin de año";
        String description = "Reunión para celebrar el fin de año y planificar eventos futuros";
        String location = "Sala de Reuniones, Alba de Tormes";
        String localStart = "2025-12-30T20:00:00Z";
        String localEnd = "2025-12-30T22:00:00Z";
        String timeZone = "Europe/Madrid";
        String type = "MEETING";// REHEARSAL, PERFORMANCE, MEETING, OTHER
        String status = "SCHEDULED"; // SCHEDULED, CANCELED, POSTPONED
        String visibility = "PUBLIC"; // PUBLIC, BAND_ONLY

        String result = eventService.createEvent(title, description, location, localStart, localEnd, timeZone, type, status, visibility);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void UpdateEvent() throws Exception {
        String id = "c6661332-8a12-4d40-be9c-d80824b380d5";
        String title = "Reunión Informativa Noviembre";
        String description = "Reunión Informativa de Noviembre";
        String location = "Sala de Reuniones, Alba de Tormes";
        String localStart = "2025-11-01T18:00:00Z";
        String localEnd = "2025-11-01T19:30:00Z";
        String timeZone = "Europe/Madrid";
        String type = "MEETING"; // REHEARSAL, PERFORMANCE, MEETING, OTHER
        String status = "CANCELED"; // SCHEDULED, CANCELED, POSTPONED
        String visibility = "PUBLIC"; // PUBLIC, BAND_ONLY

        String result = eventService.updateEvent(id, title, description, location, localStart, localEnd, timeZone, type, status, visibility);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void DeleteEvent() throws Exception {
        String id = "64259397-5ff2-49a7-9349-286f0fa37aaf";

        String result = eventService.deleteEvent(id);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetEventById() throws Exception {
        String id = "c6661332-8a12-4d40-be9c-d80824b380d5";

        String result = eventService.getEventById(id);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void ListEvents() throws Exception {
        String from = "2025-11-01T00:00:00Z";
        String to = "2025-11-30T00:00:00Z";
        Integer page = 0;
        Integer size = 10;
        String sort = "startAt,desc";

        String result = eventService.listEvents(from, to, page, size, sort);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void ListPastEvents() throws Exception {
        String before = "2025-10-31T00:00:00Z";
        Integer page = 0;
        Integer size = 10;
        String sort = "startAt,asc";

        String result = eventService.listPastEvents(before, page, size, sort);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void PrivateCalendar() throws Exception {
        String from = "2025-10-31T00:00:00Z";
        String to = "2025-12-31T00:00:00Z";
        String tz = "Europe/Madrid";
        Integer page = 0;
        Integer size = 10;
        String sort = "startAt";

        String result = eventService.privateCalendar(from, to, tz, page, size, sort);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void PublicCalendar() throws Exception {
        String from = "2025-10-31T00:00:00Z";
        String to = "2025-12-31T00:00:00Z";
        String tz = "Europe/Madrid";
        Integer page = 0;
        Integer size = 10;
        String sort = "startAt";

        String result = eventService.publicCalendar(from, to, tz, page, size, sort);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void SearchEvents() throws Exception {
        String qText = "";
        String title = "";
        String description = "";
        String location = "";
        String timeZone = "";
        String type = "";
        String status = "";
        String visibility = "BAND_ONLY";
        Integer page = 0;
        Integer size = 10;
        String sort = "startAt,asc";

        String result = eventService.searchEvents(qText, title, description, location, timeZone, type, status, visibility, page, size, sort);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetScores() throws Exception {
        String eventId = "656c376f-9614-11f0-91ac-0242ac130002";

        String result = eventService.getScores(eventId);
        System.out.println(prettyPrintJson(result));
    }
}
