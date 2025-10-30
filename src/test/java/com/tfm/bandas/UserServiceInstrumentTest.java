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
    public void DeleteInstrument() throws Exception {
        String instrumentId = "24";

        String result = userService.deleteInstrument(instrumentId);
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

        String result = userService.updateUserInstruments(userId, instruments);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void AssignInstrumentToUser() throws Exception {
        String userId = "15";
        String instrumentId = "14";
        String result = userService.assignInstrumentToUser(userId, instrumentId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void RemoveInstrumentFromUser() throws Exception {
        String userId = "15";
        String instrumentId = "22";
        String result = userService.removeInstrumentFromUser(userId, instrumentId);
        System.out.println(prettyPrintJson(result));
    }
}
