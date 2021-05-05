package Testing.ControllerTest.NetworkObjects;

import Controllers.Backend.AccountType;
import Controllers.Backend.NetworkObjects.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUser {

    User user;

    @BeforeEach
    public void ConstructUser(){
         user = new User("Jack", "qwerty", AccountType.User, "Sales");
    }

    @Test
    public void TestGetUsername() {
        String username = user.GetUsername();
        assertEquals("Jack", username);
    }

    @Test
    public void TestGetPassword() {
        String password = user.GetPassword();
        assertEquals("qwerty", password);
    }

    @Test
    public void TestGetAccountType() {
        AccountType type = user.GetAccountType();
        assertEquals(AccountType.User, type);
    }

    @Test
    public void TestGetOrganisationalUnit() {
        String unit = user.GetOrganisationalUnit();
        assertEquals("Sales", unit);
    }
}
