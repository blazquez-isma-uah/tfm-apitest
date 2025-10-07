package com.tfm.bandas;

import com.tfm.bandas.apis.EventApiClient;
import com.tfm.bandas.services.EventService;

import static com.tfm.bandas.Utils.*;

public class MainEventos {

    public static void main(String[] args) {
        try {
            EventService eventsService = new EventService(new EventApiClient(KEYCLOAK_HOST, REALM, EVENTS_HOST, ADMIN_USERNAME, ADMIN_PASSWORD));

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
}
