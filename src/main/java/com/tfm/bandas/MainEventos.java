package com.tfm.bandas;

import com.tfm.bandas.apis.EventApiClient;
import com.tfm.bandas.apis.KeycloakApiClient;
import com.tfm.bandas.services.EventService;

public class MainEventos {
    private static String KEYCLOAK_HOST = "http://localhost:8080";
    private static String REALM = "tfm-bandas";
    private static String EVENTS_HOST = "http://localhost:8083";
    private static String USERNAME = "admin";
    private static String PASSWORD = "admin123";

    public static void main(String[] args) {
        try {
            EventService eventsService = new EventService(new EventApiClient(KEYCLOAK_HOST, REALM, EVENTS_HOST, USERNAME, PASSWORD));

            String res ="";

            // Crear un evento
//            res = eventsService.createEvent("Evento de prueba", "Descripción del evento de prueba", "Lugar del evento de prueba",
//                    "2024-12-01T18:00:00Z", "2024-12-01T20:00:00Z", "Europe/Madrid","REHEARSAL", "SCHEDULED", "PUBLIC");

            // Actualizar un evento
//            res = eventsService.updateEvent("15f7d284-3a66-43fc-884f-adba1cdbeff8",
//                    "Evento de prueba actualizado", "Descripción del evento de prueba actualizado", "Lugar del evento de prueba actualizado",
//                    "2025-12-01T19:00:00Z", "2025-12-01T21:00:00Z", "Europe/Madrid","PERFORMANCE", "SCHEDULED", "BAND_ONLY");

            // Eliminar un evento
//            eventsService.deleteEvent("15f7d284-3a66-43fc-884f-adba1cdbeff8");

            // Obtener un evento por ID
//            res = eventsService.getEventById("15f7d284-3a66-43fc-884f-adba1cdbeff8");

            // Listar eventos en un rango de fechas
//            res = eventsService.listEvents("2024-11-01T00:00:00Z", "2024-12-31T23:59:59Z", 0, 10, null);

            // Listar eventos pasados
//            res = eventsService.listPastEvents("2025-10-01T00:00:00Z", 0, 10, null);

            // Listar eventos en formato calendario (privados)
//            res = eventsService.privateCalendar("2024-11-01T00:00:00Z", "2026-12-31T23:59:59Z", "Europe/Madrid", 0, 10, null);

            // Listar eventos en formato calendario (públicos)
//            res = eventsService.publicCalendar("2024-11-01T00:00:00Z", "2026-12-31T23:59:59Z", "Europe/Madrid", 0, 10, null);

            // Buscar eventos con filtros
//            res = eventsService.searchEvents("prueba", null, null, null, null, null, null, null, 0, 10, null);



            System.out.println("Respuesta:\n" + prettyPrintJson(res));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String prettyPrintJson(String json) {
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Object obj = mapper.readValue(json, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            return json;
        }
    }
}
