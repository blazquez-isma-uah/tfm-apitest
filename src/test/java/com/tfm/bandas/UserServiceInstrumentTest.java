package com.tfm.bandas;

import com.tfm.bandas.apis.UserApiClient;
import com.tfm.bandas.services.UserService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tfm.bandas.Utils.*;

public class UserServiceInstrumentTest {

    private static final UserService userService = new UserService(
            new UserApiClient(USERS_HOST, KEYCLOAK_HOST, REALM, ADMIN_USERNAME, ADMIN_PASSWORD)
    );

    @Test
    public void GetAllInstruments() throws Exception {
        Integer page = 0;
        Integer size = 10;
        String sort = "instrumentName";

        String result = userService.getAllInstruments(page, size, sort);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetInstrumentById() throws Exception {
        String instrumentId = "24";

        String result = userService.getInstrumentById(instrumentId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void CreateInstrument() throws Exception {
        String name = "Flauta";
        String type = "2";

        String result = userService.createInstrument(name, type);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void UpdateInstrument() throws Exception {
        String instrumentId = "24";
        String name = "Flauta Transversal";
        String type = "2";
        int ifMatchHeaderVersion = 1; // Current version of the instrument
        String result = userService.updateInstrument(instrumentId, name, type, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void DeleteInstrument() throws Exception {
        String instrumentId = "24";

        int ifMatchHeaderVersion = 0; // Current version

        String result = userService.deleteInstrument(instrumentId, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void SearchInstruments() throws Exception {
        String name = "Flauta";
        String type = "";
        Integer page = 0;
        Integer size = 10;
        String sort = "id";

        String result = userService.searchInstruments(name, type, page, size, sort);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void UpdateUserInstruments() throws Exception {
        String userId = "15";
        List<String> instruments = List.of("9", "10", "22");

        int ifMatchHeaderVersion = 0; // Current version

        String result = userService.updateUserInstruments(userId, instruments, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void AssignInstrumentToUser() throws Exception {
        String userId = "15";
        String instrumentId = "14";

        int ifMatchHeaderVersion = 0; // Current version

        String result = userService.assignInstrumentToUser(userId, instrumentId, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void RemoveInstrumentFromUser() throws Exception {
        String userId = "15";
        String instrumentId = "22";

        int ifMatchHeaderVersion = 0; // Current version

        String result = userService.removeInstrumentFromUser(userId, instrumentId, ifMatchHeaderVersion);
        System.out.println(prettyPrintJson(result));
    }
}
