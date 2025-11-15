package com.tfm.bandas;

import com.tfm.bandas.apis.UserApiClient;
import com.tfm.bandas.services.UserService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tfm.bandas.Utils.*;

public class UserServiceTest {

    private static final UserService userService = new UserService(
            new UserApiClient(USERS_HOST, KEYCLOAK_HOST, REALM, ADMIN_USERNAME, ADMIN_PASSWORD)
//            new UserApiClient(USERS_HOST, KEYCLOAK_HOST, REALM, MUSICIAN_USERNAME, MUSICIAN_PASSWORD)
    );
    private static final List<String> INSTRUMENTOS_CATALOGO = new ArrayList<>();

    @Test
    public void GetAllUsers() throws Exception {
        Integer page = 0;
        Integer size = 5;
        String sort = "username,desc";

        String result = userService.getAllUsers(page, size, sort);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetUserById() throws Exception {
        String userId = "24";

        String result = userService.getUserById(userId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetUserByEmail() throws Exception {
        String email = "lfernandezr@bandas.com";

        String result = userService.getUserByEmail(email);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetUserByUsername() throws Exception {
        String username = "ralonsor";

        String result = userService.getUserByUsername(username);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetUserByIamId() throws Exception {
        String iamId = "d034f3ff-187f-4abe-999d-3df357667940";

        String result = userService.getUserByIamId(iamId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void CreateUser() throws Exception {
        String email = "iblazquezc@bandas.com";
        String username = "iblazquezc";
        String password = "123456";
        String firstName = "Ismael";
        String lastName = "Blázquez";
        String secondLastName = "Carabias";
        String birthDate = "1996-05-04";
        String bandJoinDate = "2008-10-01";
        String systemSignupDate = LocalDate.now().toString();
        String phone = "1234567890";
        String notes = "This is a test user for API testing.";
        String profilePictureUrl = "http://example.com/profile.jpg";
        List<String> instrumentIds = List.of("2", "3", "4");
        List<String> rolesNames = List.of("MUSICIAN", "ADMIN"); // MUSICIAN and/or ADMIN
        String result = userService.createUser(email, username, password, firstName, lastName,
                secondLastName, birthDate, bandJoinDate, systemSignupDate,
                phone, notes, profilePictureUrl, instrumentIds, rolesNames);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void UpdateUser() throws Exception {
        String userId = "24";
        String email = "iblazquezc@bandas.com";
        String firstName = "Ismael";
        String lastName = "Blázquez";
        String secondLastName = "Carabias";
        String birthDate = "1996-05-04";
        String bandJoinDate = "2008-10-15";
        String phone = "123456789";
        String notes = "Usuario actualizado desde test";
        String profilePictureUrl = "http://example.com/profile1.jpg";

        int ifMatchHeaderVersion = 0; // Current version

        String result = userService.updateUser(userId, firstName, lastName, secondLastName,
                email, birthDate, bandJoinDate, phone, notes, profilePictureUrl, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void EnableUser() throws Exception {
        String userId = "24";

        int ifMatchHeaderVersion = 0; // Current version

        String result = userService.enableUser(userId, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void DisableUser() throws Exception {
        String userId = "24";

        int ifMatchHeaderVersion = 0; // Current version

        String result = userService.disableUser(userId, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void DeleteUser() throws Exception {
        String userId = "23";

        int ifMatchHeaderVersion = 0; // Current version

        String result = userService.deleteUser(userId, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void SearchUsers() throws Exception {
        String firstName = "";
        String lastName = "";
        String secondLastName = "Serrano";
        String email = "";
        Boolean active = null;
        Long instrumentId = null;
        Integer page = 0;
        Integer size = 10;
        String sort = "username";

        String result = userService.searchUsers(firstName, lastName, secondLastName, email, active, instrumentId, page, size, sort);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetMyProfile() throws Exception {
        String result = userService.getMyProfile();
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GenerateTestUsers() throws Exception {
        int count = 5; // Cambiar según sea necesario

        String emailDomain = "bandas.com";
        List<String> roles = List.of("MUSICIAN"); // MUSICIAN and/or ADMIN

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
            userService.createUser(email, username, "123456", firstName, lastName,
                    secondLastName, birthDate.toString(), bandJoinDate.toString(),
                    LocalDate.now().toString(), phone, notes, profilePictureUrl, instrumentIds, roles);
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
}
