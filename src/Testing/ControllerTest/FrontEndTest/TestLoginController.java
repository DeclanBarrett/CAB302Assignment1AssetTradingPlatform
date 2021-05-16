package Testing.ControllerTest.FrontEndTest;

import Controllers.Backend.AccountType;
import Controllers.Backend.NetworkObjects.LoginToken;
import Controllers.Backend.NetworkObjects.UserInfo;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.Exceptions.LoginException;
import Controllers.Backend.Socket.MockSocket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestLoginController {

    LoginController loginController;

    @BeforeAll
    public static void ConstructMock() {
        //Create socket information
        MockSocket mockSocket = MockSocket.getInstance();
    }

    @BeforeEach
    public void ConstructSecurity(){
        loginController = new LoginController();
    }

    @Test
    public void TestStandardLoginToken() throws LoginException {

        loginController.AttemptLogin("User 1", "qwerty");

        LoginToken loginToken = new LoginToken("User 1", new Date());

        assertEquals(loginToken.getUsername(), LoginController.GetToken().getUsername());
    }

    @Test
    public void TestStandardLoginUser() throws LoginException {

        loginController.AttemptLogin("User 1", "qwerty");

        UserInfo userInfo = new UserInfo("User 1", AccountType.User, "Sales");

        assertEquals(userInfo, LoginController.GetUser());
    }

    @Test
    public void TestBadLoginException() {

        assertThrows(LoginException.class, () -> {
            loginController.AttemptLogin("User 1", "q");
        });
    }

    @Test
    public void TestBadLoginUser(){

        assertThrows(LoginException.class, () -> {
            loginController.AttemptLogin("User 1", "q");
        });

        assertEquals(null, LoginController.GetUser());
    }

    @Test
    public void TestBadLoginToken(){

        assertThrows(LoginException.class, () -> {
            loginController.AttemptLogin("User 1", "q");
        });

        assertEquals(null, LoginController.GetToken());
    }

    @Test
    public void TestIncorrectPassword(){

        assertThrows(LoginException.class, () -> {
            loginController.AttemptLogin("User 1", "sandwhich");
        });

        assertEquals(null, LoginController.GetToken());
    }

    @Test
    public void TestLogoutUser() throws LoginException{

        loginController.AttemptLogin("User 1", "qwerty");

        loginController.Logout();

        assertEquals(null, LoginController.GetUser());
    }

    @Test
    public void TestLogoutToken() throws LoginException {

        loginController.AttemptLogin("User 1", "qwerty");

        loginController.Logout();

        assertEquals(null, LoginController.GetToken());
    }

    @Test
    public void TestTwoLogins() throws LoginException {

        loginController.AttemptLogin("User 1", "qwerty");
        loginController.AttemptLogin("User 4", "1234");

        UserInfo userInfo = new UserInfo("User 4", AccountType.User, "Finance");

        assertEquals(userInfo, LoginController.GetUser());
    }

    @Test
    public void TestAllLogins() throws LoginException {
        loginController.AttemptLogin("User 1","qwerty");
        loginController.AttemptLogin("User 2","qwerty");
        loginController.AttemptLogin("User 3","1234");
        loginController.AttemptLogin("User 4","1234");
        loginController.AttemptLogin("User 5","password");
        loginController.AttemptLogin("User 6","password");
        loginController.AttemptLogin("User 7","password");
        loginController.AttemptLogin("User 8","password");
        loginController.AttemptLogin("User 9","password");
        loginController.AttemptLogin("Declan Testing", "qwerty");
        loginController.AttemptLogin("Aiden Testing","qwerty");
        loginController.AttemptLogin("Brad Testing","qwerty");
        loginController.AttemptLogin("Ethan Testing","qwerty");
        loginController.AttemptLogin("User 10","123");
        loginController.AttemptLogin("User 11","qwerty");
        loginController.AttemptLogin("User 12","qwerty");
        loginController.AttemptLogin("User 13","qwerty");
    }


}