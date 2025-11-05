package com.tfm.bandas;

import com.tfm.bandas.apis.UserApiClient;
import com.tfm.bandas.services.UserService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tfm.bandas.Utils.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceRoleTest {

    private static final UserService userService = new UserService(
            new UserApiClient(USERS_HOST, KEYCLOAK_HOST, REALM, ADMIN_USERNAME, ADMIN_PASSWORD)
    );

    @Test
    public void GetAllRoles() throws Exception {
        String result = userService.getAllRoles();
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void CreateRole() throws Exception {
        String roleName = "Role";
        String description = "Role for ing purposes";

        String result = userService.createRole(roleName, description);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void DeleteRole() throws Exception {
        String roleName = "Role";

        String result = userService.deleteRole(roleName);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetRoleById() throws Exception {
        String roleId = "8cd94498-be47-4323-9ddf-1384cecf09f9";

        String result = userService.getRoleById(roleId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void GetRoleByName() throws Exception {
        String roleName = "ADMIN";

        String result = userService.getRoleByName(roleName);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void ListUserRoles() throws Exception {
        String userId = "24";

        String result = userService.listUserRoles(userId);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void ListUserRolesByUsername() throws Exception {
        String username = "sserranoa";
        String result = userService.listUserRolesByUsername(username);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void UpdateUserRoles() throws Exception {
        String userId = "21";
        List<String> roleNames = List.of("MUSICIAN", "ADMIN");

        String result = userService.updateUserRoles(userId, roleNames);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void AssignRoleToUser() throws Exception {
        String userId = "20";
        String roleName = "MUSICIAN";

        String result = userService.assignRoleToUser(userId, roleName);
        System.out.println(prettyPrintJson(result));
    }

    @Test
    public void RemoveRoleFromUser() throws Exception {
        String userId = "21";
        String roleName = "ADMIN";

        String result = userService.removeRoleFromUser(userId, roleName);
        System.out.println(prettyPrintJson(result));
    }
}
