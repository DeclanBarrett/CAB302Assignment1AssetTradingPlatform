package Controllers.BackEnd.Processing;

import Controllers.Exceptions.AuthenticationException;
import Models.InformationGrabber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TestLoginChecker {

    LoginChecker checker;
    @BeforeEach
    public void ConstructLoginChecker() {
        checker = new LoginChecker(new InformationGrabber());
    }

    @Test
    public void TestLoginWorking() throws AuthenticationException {
        checker.compareLogin("User 1", "b717415eb5e699e4989ef3e2c4e9cbf7");
    }

    @Test
    public void TestLoginBadPassword() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            checker.compareLogin("User 1", "717415eb5e699e4989ef3e2c4e9cbf7a");
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Incorrect Username of Password"));
    }
}
