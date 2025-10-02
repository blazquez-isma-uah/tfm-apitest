package com.tfm.bandas;

import com.tfm.bandas.apis.UserApiClient;
import com.tfm.bandas.services.UserService;

import java.util.List;
import java.util.Random;

import static com.tfm.bandas.Utils.*;

public class MainInstrumentos {

    private static final List<String> NOMBRES_INSTRUMENTOS = List.of(
            "Flauta", "Trompeta", "Percusión", "Saxofón Alto", "Saxofón Tenor", "Clarinete", "Trombón", "Oboe", "Trompa", "Bombardino", "Tuba"
    );

    private static final List<String> VOCES = List.of(
            "Principal", "1", "2", "3"
    );

    static UserService userService = new UserService(new UserApiClient(USERS_HOST, KEYCLOAK_HOST, REALM, USERNAME, PASSWORD));

    public static void main(String[] args) {
        try {

            // Obtener todos los instrumentos
//            String allInstruments = userService.getAllInstruments(0, 20, "instrumentName,voice");
//            System.out.println("Instrumentos disponibles:\n" + prettyPrintJson(allInstruments));

            // Crear un nuevo instrumento
//            String newInstrument = userService.createInstrument("Percusión", "1");
//            System.out.println("Instrumento creado:\n" + prettyPrintJson(newInstrument));

            // Crear varios instrumentos aleatorios
//            createRandomInstruments(5);

            // Buscar instrumentos por nombre y voz
            String searchResults = userService.searchInstruments("Flauta", "1", 0, 10, null);
            System.out.println("Resultados de búsqueda:\n" + prettyPrintJson(searchResults));

            // Obtener un instrumento por ID
//            String instrument = userService.getInstrumentById("17");
//            System.out.println("Detalles del instrumento:\n" + prettyPrintJson(instrument));

            // Eliminar un instrumento
//            String deleteResponse = userService.deleteInstrument("17");
//            System.out.println("Instrumento eliminado:\n" + prettyPrintJson(deleteResponse));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createRandomInstruments(int count) throws Exception {
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String instrumentName = pick(NOMBRES_INSTRUMENTOS);
            String voice = pick(VOCES);

            // Verificar si el instrumento ya existe y si existe, omitir su creación y atrasar el contador
            String searchResults = userService.searchInstruments(instrumentName, voice, 0, 1, null);
            if (!searchResults.contains("\"content\":[]")) {
                System.out.println("El instrumento " + instrumentName + " con voz " + voice + " ya existe. Se omite su creación.");
                i--;
                continue;
            }

            // Crear el instrumento
            String newInstrument = userService.createInstrument(instrumentName, voice);
            System.out.println("Instrumento creado:\n" + prettyPrintJson(newInstrument));
        }
    }
}
