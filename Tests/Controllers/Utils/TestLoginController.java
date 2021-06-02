package Controllers.Utils;

import Controllers.BackEnd.AccountType;
import Controllers.BackEnd.NetworkObjects.UserInfo;
import Controllers.BackEnd.Processing.JWTHandler;
import Controllers.BackEnd.Socket.ClientSocket;
import Controllers.Exceptions.AuthenticationException;
import Controllers.Exceptions.ServerException;
import Controllers.FrontEnd.Login.LoginController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the Login Controller class
 */
public class TestLoginController {

    LoginController loginController;

    @BeforeAll
    public static void ConstructMock() {
        //Create socket information
        ClientSocket clientSocket = ClientSocket.getInstance();
    }

    @BeforeEach
    public void ConstructController(){
        loginController = new LoginController();
    }

    @Test
    public void TestStandardLoginToken() throws AuthenticationException, ServerException {

        loginController.AttemptLogin("User 1", "qwerty");


        JWTHandler jwtHandler = new JWTHandler();

        String string = jwtHandler.createToken("User 1");

        assertEquals(jwtHandler.getUser(string), jwtHandler.getUser(LoginController.GetToken()));
    }

    @Test
    public void TestStandardLoginUser() throws AuthenticationException, ServerException {

        loginController.AttemptLogin("User 1", "qwerty");

        UserInfo userInfo = new UserInfo("User 1", AccountType.User, "Sales");

        assertEquals(userInfo, LoginController.GetUser());
    }

    @Test
    public void TestBadLoginException() {

        assertThrows(AuthenticationException.class, () -> {
            loginController.AttemptLogin("User 1", "q");
        });
    }

    @Test
    public void TestBadLoginUser(){

        assertThrows(AuthenticationException.class, () -> {
            loginController.AttemptLogin("User 1", "q");
        });

        assertEquals(null, LoginController.GetUser());
    }

    @Test
    public void TestBadLoginToken(){

        assertThrows(AuthenticationException.class, () -> {
            loginController.AttemptLogin("User 1", "q");
        });

        assertEquals(null, LoginController.GetToken());
    }

    @Test
    public void TestIncorrectPassword(){

        assertThrows(AuthenticationException.class, () -> {
            loginController.AttemptLogin("User 1", "sandwhich");
        });

        assertEquals(null, LoginController.GetToken());
    }

    @Test
    public void TestTwoLogins() throws AuthenticationException, ServerException {

        loginController.AttemptLogin("User 1", "qwerty");
        loginController.AttemptLogin("User 4", "1234");

        UserInfo userInfo = new UserInfo("User 4", AccountType.User, "Finance");

        assertEquals(userInfo, LoginController.GetUser());
    }

    /**
    @Test
    public void TestAllLogins() throws AuthenticationException, ServerException {
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
*/
    //CANNOT TEST LOGOUT SINCE LOGOUT USES THE SCENE CHANGER UTIL

}