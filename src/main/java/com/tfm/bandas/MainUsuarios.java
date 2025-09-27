package com.tfm.bandas;

import com.tfm.bandas.apis.KeycloakApiClient;
import com.tfm.bandas.apis.UserApiClient;
import com.tfm.bandas.services.KeycloakService;
import com.tfm.bandas.services.UserService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class MainUsuarios {
    private static String KEYCLOAK_HOST = "http://localhost:8080";
    private static String REALM = "tfm-bandas";
    private static String USERS_HOST = "http://localhost:8081";
    private static String USERNAME = "admin";
    private static String PASSWORD = "admin123";
    private static String CLIENT_ID = "realm-admin";
    private static String CLIENT_SECRET = "8nWwBGuo0RxdRQfHUaQgcqv7Fibt8JYX";

    static KeycloakService keycloakService = new KeycloakService(new KeycloakApiClient(KEYCLOAK_HOST, REALM, CLIENT_ID, CLIENT_SECRET));
    static UserService userService = new UserService(new UserApiClient(USERS_HOST, KEYCLOAK_HOST, REALM, USERNAME, PASSWORD));


    public static void main(String[] args) {
        try {

            String res ="";

            // KEYCLOAK ADMIN LOGIN
            // Crear Rol
//            res = keycloakService.createRole("NOMBRE_ROL", "DESCRIPCION_ROL");

            // Crear Usuario
            String username = "pepe";
            String firstName = "Jose";
            String lastName = "Blázquez";
            String secondLastName = "Martín";
            String email = "pepeblazquez@bandas.com";
            String password = "123456";
            String birthDate = "1995-09-01";
            String bandJoinDate = "2023-01-01";
            String phone = "123456789";
            String notes = "Notas del usuario";
            String profilePictureUrl = "http://example.com/profile.jpg";
            List<String> instrumentIds = List.of("5", "6"); // IDs de instrumentos
            List<String> roles = List.of("NOMBRE_ROL"); // Roles a asignar


            crearUsuarioCompleto(username, firstName, lastName, secondLastName, email, password,
                    birthDate, bandJoinDate, phone, null, null, instrumentIds, roles);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void crearUsuarioCompleto(String username, String firstName, String lastName, String secondLastName, String email, String password,
                                             String birthDate, String bandJoinDate, String phone, String notes, String profilePictureUrl,
                                             List<String> instrumentIds, List<String> roles) throws Exception {

        // Crear usuario en Keycloak
        String lastNameFull = lastName + (secondLastName != null && !secondLastName.isEmpty() ? " " + secondLastName : "");
        String res1 = keycloakService.createUser(username, email, firstName, lastNameFull, password);
        System.out.println("\nCrear usuario Keycloak response: " + prettyPrintJson(res1));

        // Obtener el ID del usuario recién creado de Keycloak
        String userResp = keycloakService.getUserInfoByUsername(username);
        System.out.println("\nGet user by username response: " + prettyPrintJson(userResp));
        String iamId = extractFromResponse(userResp, "id");
        // extract createdTimestamp y convertir a LocalDate
        String createdTimestampStr = extractFromResponse(userResp, "createdTimestamp");
        LocalDate systemSignupDate = null;
        if (createdTimestampStr != null && !createdTimestampStr.isEmpty()) {
            long createdTimestamp = Long.parseLong(createdTimestampStr);
            systemSignupDate = Instant.ofEpochMilli(createdTimestamp)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        if(systemSignupDate == null) {
            systemSignupDate = LocalDate.now();
        }

        // Crear perfil de usuario en el sistema
        String resp2 = userService.createUser(iamId, username, firstName, lastName, secondLastName, email,
                birthDate, bandJoinDate, systemSignupDate.toString(), phone, notes, profilePictureUrl, instrumentIds);
        System.out.println("\nCrear perfil usuario response: " + prettyPrintJson(resp2));
    }

    private static String extractFromResponse(String userResp, String field) {
        // Suponiendo que la respuesta es un JSON
        // Si es un array, se toma el primer elemento
        // Y si es un objeto dentro de otro se nombra con punto (.) Ej: "address.street"
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            if (userResp.trim().startsWith("[")) {
                // Es un array
                List<?> list = mapper.readValue(userResp, List.class);
                if (list.isEmpty()) return null;
                Object firstElem = list.get(0);
                String json = mapper.writeValueAsString(firstElem);
                return mapper.readTree(json).path(field).asText();
            } else {
                // Es un objeto
                return mapper.readTree(userResp).path(field).asText();
            }
        } catch (Exception e) {
            return null;
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
