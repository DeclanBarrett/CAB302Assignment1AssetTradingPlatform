package Controllers.FrontEnd.Admin;

import Controllers.FrontEnd.Login.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Tests the Admin Processing class
 */
public class TestAdminProcessing {

    AdminProcessing processing;

    @BeforeEach
    public void ConstructAdminProcessing() {
        processing = new AdminProcessing();
    }

    @Test
    public void TestCreateStandardUser() throws Exception {
        processing.createUser("User", "New User", "qwerty", "Sales");
    }

    @Test
    public void TestCreateUserNotEnoughInformation() {
        Exception exception = assertThrows(Exception.class, () -> {
            processing.createUser("User", "New User", "qwerty", null);
        });
    }

    @Test
    public void TestCreateUserNoUser() {
        Exception exception = assertThrows(Exception.class, () -> {
            processing.createUser("", "", null, null);
        });
    }

    @Test
    public void TestCreateUserInvalidAccount() {
        Exception exception = assertThrows(Exception.class, () -> {
            processing.createUser("Use", "New User", "qwerty", "Sales");
        });
    }

    @Test
    public void TestCreateUserInvalidOrganisation() {
        Exception exception = assertThrows(Exception.class, () -> {
            processing.createUser("User", "New User", "qwerty", "Sensory");
        });
    }

    @Test
    public void TestCreateUserPasswordHash() throws Exception {
        processing.createUser("User", "New User", "qwerty", "Sales");
        LoginController controller = new LoginController();
        controller.AttemptLogin("New User", "qwerty");
    }

    @Test
    public void TestEditPasswordStandard() throws Exception {
        processing.editUserPassword("User 1", "12345");
    }

    @Test
    public void TestEditPasswordNull() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            processing.editUserPassword(null, "qwerty");
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(""));
    }

    @Test
    public void TestEditPasswordBadUser() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            processing.editUserPassword("Bad User", "qwerty");
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(""));
    }

    @Test
    public void TestEditPasswordHash() throws Exception {
        processing.editUserPassword("User 1", "12345");
        LoginController controller = new LoginController();
        controller.AttemptLogin("User 1", "12345");

    }
}
