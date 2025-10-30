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
        String title = "Test Event";
        String description = "Description";
        String location = "Location";
        String localStart = "2025-10-27T10:00:00Z";
        String localEnd = "2025-10-27T12:00:00Z";
        String timeZone = "Europe/Madrid";
        String type = "REHEARSAL";
        String status = "SCHEDULED";
        String visibility = "PUBLIC";

        String result = eventService.createEvent(title, description, location, localStart, localEnd, timeZone, type, status, visibility);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void UpdateEvent() throws Exception {
        String id = "eventId";
        String title = "Updated Event";
        String description = "Updated Description";
        String location = "Updated Location";
        String localStart = "2025-10-28T10:00:00Z";
        String localEnd = "2025-10-28T12:00:00Z";
        String timeZone = "Europe/Madrid";
        String type = "REHEARSAL";
        String status = "SCHEDULED";
        String visibility = "BAND_ONLY";

        String result = eventService.updateEvent(id, title, description, location, localStart, localEnd, timeZone, type, status, visibility);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void DeleteEvent() throws Exception {
        String id = "eventId";

        String result = eventService.deleteEvent(id);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetEventById() throws Exception {
        String id = "eventId";

        String result = eventService.getEventById(id);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void ListEvents() throws Exception {
        String from = "2025-01-01T00:00:00Z";
        String to = "2025-12-31T00:00:00Z";
        Integer page = 0;
        Integer size = 10;
        String sort = "startAt";

        String result = eventService.listEvents(from, to, page, size, sort);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void ListPastEvents() throws Exception {
        String before = "2025-10-30T00:00:00Z";
        Integer page = 0;
        Integer size = 10;
        String sort = "startAt";

        String result = eventService.listPastEvents(before, page, size, sort);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void PrivateCalendar() throws Exception {
        String from = "2024-10-01T00:00:00Z";
        String to = "2025-10-31T00:00:00Z";
        String tz = "Europe/Madrid";
        Integer page = 0;
        Integer size = 10;
        String sort = "startAt";

        String result = eventService.privateCalendar(from, to, tz, page, size, sort);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void PublicCalendar() throws Exception {
        String from = "2024-10-01T00:00:00Z";
        String to = "2025-11-30T00:00:00Z";
        String tz = "Europe/Madrid";
        Integer page = 0;
        Integer size = 10;
        String sort = "startAt";

        String result = eventService.publicCalendar(from, to, tz, page, size, sort);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void SearchEvents() throws Exception {
        String qText = "query";
        String title = "Title";
        String description = "Description";
        String location = "Location";
        String timeZone = "UTC";
        String type = "Type";
        String status = "Status";
        String visibility = "Public";
        Integer page = 1;
        Integer size = 10;
        String sort = "date";

        String result = eventService.searchEvents(qText, title, description, location, timeZone, type, status, visibility, page, size, sort);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetScores() throws Exception {
        String eventId = "eventId";

        String result = eventService.getScores(eventId);
        System.out.println(prettyPrintJson(result));
    }
}
