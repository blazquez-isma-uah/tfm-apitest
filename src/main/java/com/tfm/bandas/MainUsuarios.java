package com.tfm.bandas;

import com.tfm.bandas.apis.KeycloakApiClient;
import com.tfm.bandas.apis.UserApiClient;
import com.tfm.bandas.services.KeycloakService;
import com.tfm.bandas.services.UserService;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.tfm.bandas.Utils.*;

public class MainUsuarios {
    static KeycloakService keycloakService = new KeycloakService(new KeycloakApiClient(KEYCLOAK_HOST, REALM, CLIENT_ID, CLIENT_SECRET));
    static UserService userService = new UserService(new UserApiClient(USERS_HOST, KEYCLOAK_HOST, REALM, USERNAME, PASSWORD));

    private static final List<String> NOMBRES = List.of(
            "Alejandro","María","José","Lucía","Pablo","Laura","David","Ana","Sergio","Marta",
            "Daniel","Paula","Javier","Elena","Carlos","Sara","Diego","Alba","Manuel","Irene",
            "Adrián","Noelia","Rubén","Claudia","Hugo","Carmen","Iván","Patricia","Raúl","Beatriz"
    );

    private static final List<String> APELLIDOS = List.of(
            "García","Rodríguez","González","Fernández","López","Martínez","Sánchez","Pérez","Gómez","Martín",
            "Jiménez","Ruiz","Hernández","Díaz","Moreno","Muñoz","Álvarez","Romero","Alonso","Gutiérrez",
            "Navarro","Torres","Domínguez","Vázquez","Ramos","Gil","Ramírez","Serrano","Blanco","Suárez"
    );

    private static final List<String> INSTRUMENTOS_CATALOGO = new ArrayList<>();

    public static void main(String[] args) {
        try {
            // Crear Rol
//            keycloakService.createRole("ADMIN", "Administrador del sistema");
//            keycloakService.createRole("MUSICIAN", "Músico de la banda");
            // Obtener info Rol
//            assignRolesToUser("pepe", List.of("ADMIN", "MUSICIAN"));

            // Crear Usuarios: Genera N usuarios de prueba
//            generarUsuariosDePrueba(20, "bandas.com", List.of("MUSICIAN"));
//            crearUsuarioCompleto(
//                    "adminBandas",
//                    "Administrador",
//                    "Bandas",
//                    "",
//                    "admin@bandas.com",
//                    "admin123",
//                    "1996-05-04",
//                    "2008-01-01",
//                    "625363757",
//                    "Usuario administrador del sistema en Keycloak y del sistema de gestión de bandas.",
//                    "",
//                    List.of(),
//                    List.of("ADMIN")
//            );

            // Borrar Usuario
//            String username = "pruebauser";
//            deleteUser(username);

            // Update Usuario
//            String newEmail = ""; String newFirstname = ""; String newLastname = ""; String newSecondLastname = "";
//            String birthDate = ""; String bandJoinDate = ""; String phone = ""; String notes = ""; String profilePictureUrl = "";
//            List<String> instrumentIds = List.of(); List<String> roles = List.of(); String newPassword = "";
//            updateCompleteUser(username, newEmail, newFirstname, newLastname, newSecondLastname, newPassword,
//                    birthDate, bandJoinDate, phone, notes, profilePictureUrl, instrumentIds, roles);
//            updateCompleteUser("ahernandezm", null, null, "Hernández", "Martín", null,
//                    null, "01-06-2010", "611122221", null, null, List.of("15","16"), List.of("MUSICIAN", "ADMIN"));

            // Get Usuarios
//            String allUsers = userService.getAllUsers(0, 50, null);
//            System.out.println("\nUsuarios en el sistema: \n" + prettyPrintJson(allUsers));

            // Get usuario
//            String username = "ahernandezm";
//            getUserInfo(username);

            // Buscar usuarios
            String search = userService.searchUsers(null, null, null, null, 17L,
                    null, null, null);
            System.out.println("\nUsuarios encontrados: \n" + prettyPrintJson(search));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getUserInfo(String username) throws Exception {
        String keycloakUser = keycloakService.getUserInfoByUsername(username);
        String keycloakUserId = extractFromResponse(keycloakUser, "id");
        String userInfo = userService.getUserByIamId(keycloakUserId);
        System.out.println("\nUsuario " + username + " Keycloak info: \n" + prettyPrintJson(keycloakUser));
        System.out.println("\nUsuario " + username + " system info: \n" + prettyPrintJson(userInfo));
    }


    private static void assignRolesToUser(String username, List<String> roles) throws Exception {
        String keycloakUser = keycloakService.getUserInfoByUsername(username);
        String keycloakUserId = extractFromResponse(keycloakUser, "id");
        if (keycloakUserId != null && !keycloakUserId.isEmpty()) {
            updateRoles(username, roles, keycloakUserId);
        } else {
            System.out.println("El usuario " + username + " no existe en Keycloak, no se pueden asignar roles.");
        }
    }

    private static void updateCompleteUser(String username, String newEmail, String newFirstname, String newLastname, String newSecondLastname, String newPassword,
                                           String birthDate, String bandJoinDate, String phone, String notes, String profilePictureUrl, List<String> instrumentIds, List<String> roles) throws Exception {
        String keycloakUser = keycloakService.getUserInfoByUsername(username);
        String keycloakUserId = extractFromResponse(keycloakUser, "id");
        if(keycloakUserId != null && !keycloakUserId.isEmpty()) {
            // Solo la información que contiene Keycloak
            updateKeycloakInfo(newEmail, newFirstname, newLastname, newSecondLastname, keycloakUserId, keycloakUser);
            // Cambiar contraseña
            if(newPassword != null && !newPassword.isBlank()) {
                keycloakService.updateUserPassword(keycloakUserId, newPassword, false);
            }
            // Roles
            updateRoles(username, roles, keycloakUserId);
            // Información del perfil en el sistema
            updateSystemUserProfileInfo(keycloakUserId, username, newEmail, newFirstname, newLastname, newSecondLastname,
                    birthDate, bandJoinDate, phone, notes, profilePictureUrl, instrumentIds);
        } else {
            System.out.println("El usuario " + username + " no existe en Keycloak, no se puede actualizar.");
        }
    }

    private static void updateSystemUserProfileInfo(String keycloakUserId, String username, String newEmail, String newFirstname, String newLastname, String newSecondLastname,
                                                    String birthDate, String bandJoinDate, String phone, String notes, String profilePictureUrl, List<String> instrumentIds) throws Exception {
        String userResp = userService.getUserByIamId(keycloakUserId);
        String userId = extractFromResponse(userResp, "id");

        String birthToSend = (birthDate != null && !birthDate.isBlank())
                ? requireIsoDate(birthDate, "birthDate")
                : extractFromResponse(userResp, "birthDate");

        String bandJoinToSend = (bandJoinDate != null && !bandJoinDate.isBlank())
                ? requireIsoDate(bandJoinDate, "bandJoinDate")
                : extractFromResponse(userResp, "bandJoinDate");

        if(userId != null && !userId.isEmpty()) {
            String updateUserResp = userService.updateUser(
                    userId,
                    keycloakUserId,
                    username,
                    newFirstname != null && !newFirstname.isBlank() ? newFirstname : extractFromResponse(userResp, "firstName"),
                    newLastname != null && !newLastname.isBlank() ? newLastname : extractFromResponse(userResp, "lastName"),
                    newSecondLastname != null ? newSecondLastname : extractFromResponse(userResp, "secondLastName"),
                    newEmail != null && !newEmail.isBlank() ? newEmail : extractFromResponse(userResp, "email"),
                    birthToSend,
                    bandJoinToSend,
                    extractFromResponse(userResp, "systemSignupDate"),
                    phone != null ? phone : extractFromResponse(userResp, "phone"),
                    notes != null ? notes : extractFromResponse(userResp, "notes"),
                    profilePictureUrl != null ? profilePictureUrl : extractFromResponse(userResp, "profilePictureUrl"),
                    instrumentIds != null && !instrumentIds.isEmpty() ? instrumentIds : List.of()
            );
            System.out.println("\nUsuario " + username + " actualizado en el sistema: \n" + prettyPrintJson(updateUserResp));
        } else {
            System.out.println("El usuario " + username + " no existe en el sistema, no se puede actualizar su perfil.");
        }
    }

    private static void updateRoles(String username, List<String> roles, String keycloakUserId) throws Exception {
        if(roles != null && !roles.isEmpty()) {
            for(String role : roles) {
                String roleInfo = keycloakService.getRoleInfoByName(role);
                String roleId = extractFromResponse(roleInfo, "id");
                if(roleId != null && !roleId.isEmpty()) {
                    keycloakService.assignRealmRoleToUser(keycloakUserId, roleId, role);
                    System.out.println("El rol " + role + " asignado al usuario " + username + ".");
                } else {
                    System.out.println("El rol " + role + " no existe en Keycloak, no se puede asignar al usuario " + username + ".");
                }
            }
        }
    }

    private static void updateKeycloakInfo(String newEmail, String newFirstname, String newLastname, String newSecondLastname, String keycloakUserId, String keycloakUser) throws Exception {
        if((newEmail != null && !newEmail.isBlank()) ||
                (newFirstname != null && !newFirstname.isBlank()) ||
                (newLastname != null && !newLastname.isBlank())) {
            String lastNameFull = null;
            if (newLastname != null && !newLastname.isEmpty()) {
                lastNameFull = newLastname + (newSecondLastname != null && !newSecondLastname.isEmpty() ? " " + newSecondLastname : "");
            }
            System.out.println("Actualizando usuario en Keycloak: " + keycloakUserId + " con email=" + newEmail + ", firstname=" + newFirstname + ", lastname=" + lastNameFull);
            String s = keycloakService.updateUser(
                    keycloakUserId,
                    newEmail != null && !newEmail.isEmpty() ? newEmail : extractFromResponse(keycloakUser, "email"),
                    newFirstname != null && !newFirstname.isEmpty() ? newFirstname : extractFromResponse(keycloakUser, "firstName"),
                    lastNameFull != null ? lastNameFull : extractFromResponse(keycloakUser, "lastName"));
            System.out.println("Usuario " + keycloakUserId + " actualizado en Keycloak: \n" + prettyPrintJson(s));
        }
    }

    private static void deleteUser(String username) throws Exception {
        String keycloakUser = keycloakService.getUserInfoByUsername(username);
        String keycloakUserId = extractFromResponse(keycloakUser, "id");
        if(keycloakUserId != null && !keycloakUserId.isEmpty()) {
            keycloakService.deleteUser(keycloakUserId);
            System.out.println("El usuario " + username + " eliminado de Keycloak.");
            String userResp = userService.getUserByIamId(keycloakUserId);
            String userId = extractFromResponse(userResp, "id");
            if(userId != null && !userId.isEmpty()) {
                userService.deleteUser(userId);
                System.out.println("El usuario " + username + " eliminado del sistema.");
            } else {
                System.out.println("El usuario " + username + " no existe en el sistema, no se puede eliminar.");
            }
        } else {
            System.out.println("El usuario " + username + " no existe en Keycloak, no se puede eliminar.");
        }
    }

    // Genera `count` usuarios y los crea con crearUsuarioCompleto
    private static void generateTestUsers(int count, String emailDomain, List<String> roles) throws Exception {
        // Si no hay instrumentos en el sistema, los carga
        if (INSTRUMENTOS_CATALOGO.isEmpty()) {
            loadInstrumentCatalog();
        }

        Set<String> usados = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String firstName = pick(NOMBRES);
            String lastName = pick(APELLIDOS);
            String secondLastName = pickDistinct(APELLIDOS, lastName);

            String baseUser = buildUsernameBase(firstName, lastName, secondLastName);
            String username = ensureUnique(baseUser, usados);
            String email = username + "@" + emailDomain;

            LocalDate birthDate = randomDateBetween(LocalDate.of(1965, 1, 1), LocalDate.of(2008, 12, 31));
            LocalDate minJoin = birthDate.plusYears(12).isAfter(LocalDate.of(2010, 1, 1)) ? birthDate.plusYears(12) : LocalDate.of(2010, 1, 1);
            LocalDate bandJoinDate = randomDateBetween(minJoin, LocalDate.now());

            String phone = randomPhoneEs();
            String notes = "Usuario de prueba generado el " + LocalDate.now();
            String profilePictureUrl = "https://picsum.photos/seed/" + username + "/200/200";

            List<String> instrumentIds = pickRandomInstruments(3, INSTRUMENTOS_CATALOGO);

            System.out.println("--------------------------------------------------------------");
            System.out.println("CREANDO USUARIO: " + username);
            createCompleteUser(
                    username,
                    firstName,
                    lastName,
                    secondLastName,
                    email,
                    "123456",
                    birthDate.toString(),
                    bandJoinDate.toString(),
                    phone,
                    notes,
                    profilePictureUrl,
                    instrumentIds,
                    roles
            );
            System.out.println("--------------------------------------------------------------");
        }
    }

    // Carga el catálogo de instrumentos desde el sistema
    private static void loadInstrumentCatalog() throws Exception {
        int page = 0;
        int size = 10; // tamaño de página razonable
        boolean last;
        INSTRUMENTOS_CATALOGO.clear();
        do {
            String resp = userService.getAllInstruments(page, size, null);
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(resp);

            com.fasterxml.jackson.databind.JsonNode content = root.path("content");
            if (content.isArray()) {
                for (com.fasterxml.jackson.databind.JsonNode item : content) {
                    String id = item.path("id").asText(null);
                    if (id != null && !id.isBlank() && !INSTRUMENTOS_CATALOGO.contains(id)) {
                        INSTRUMENTOS_CATALOGO.add(id);
                    }
                }
            }
            last = root.path("last").asBoolean(false);
            page++;
        } while (!last);
        System.out.println("Catálogo de instrumentos cargado (" + INSTRUMENTOS_CATALOGO.size() + "): " + INSTRUMENTOS_CATALOGO);
    }

    private static void createCompleteUser(String username, String firstName, String lastName, String secondLastName, String email, String password,
                                           String birthDate, String bandJoinDate, String phone, String notes, String profilePictureUrl,
                                           List<String> instrumentIds, List<String> roles) throws Exception {
        // Comprueba si el usuario ya existe en Keycloak
        String existingUserResp = keycloakService.getUserInfoByUsername(username);
        String existingId = extractFromResponse(existingUserResp, "id");
        if (existingId != null && !existingId.isEmpty()) {
            System.out.println("El usuario " + username + " ya existe en Keycloak con ID " + existingId + ", se omite su creación.");
        } else {
            // Crear usuario en Keycloak
            String lastNameFull = lastName + (secondLastName != null && !secondLastName.isEmpty() ? " " + secondLastName : "");
            keycloakService.createUser(username, email, firstName, lastNameFull, password);
            System.out.println("El usuario " + username + " creado en Keycloak.");
        }
        // Obtener el ID del usuario recién creado de Keycloak
        String userResp = keycloakService.getUserInfoByUsername(username);
        System.out.println("\nUsuario " + username + " info Keycloak: \n" + prettyPrintJson(userResp));
        // Asignar roles
        assignRolesToUser(username, roles);

        // Extraer ID y createdTimestamp
        String iamId = extractFromResponse(userResp, "id");
        String createdTimestampStr = extractFromResponse(userResp, "createdTimestamp");
        LocalDate systemSignupDate = stringTimestampToLocalDate(createdTimestampStr);

        String birthToSend = (birthDate != null && !birthDate.isBlank())
                ? requireIsoDate(birthDate, "birthDate")
                : extractFromResponse(userResp, "birthDate");

        String bandJoinToSend = (bandJoinDate != null && !bandJoinDate.isBlank())
                ? requireIsoDate(bandJoinDate, "bandJoinDate")
                : extractFromResponse(userResp, "bandJoinDate");

        // Crear perfil de usuario en el sistema
        String createUserResp = userService.createUser(iamId, username, firstName, lastName, secondLastName, email,
                birthToSend, bandJoinToSend, systemSignupDate.toString(), phone, notes, profilePictureUrl, instrumentIds);
        System.out.println("\nUsuario " + username + " creado en el sistema: \n" + prettyPrintJson(createUserResp));
    }
}
