package Testing.ControllerTest.NetworkObjects;

import Controllers.Backend.AccountType;
import Controllers.Backend.NetworkObjects.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUserInfo {

    UserInfo userInfo;

    @BeforeEach
    public void ConstructUser(){
        userInfo = new UserInfo("Jack", AccountType.User, "Sales");
    }

    @Test
    public void TestGetUsername() {
        String username = userInfo.GetUsername();
        assertEquals("Jack", username);
    }

    /*
    @Test
    public void TestGetPassword() {
        String password = user.GetPassword();
        assertEquals("qwerty", password);
    }
    */

    @Test
    public void TestGetAccountType() {
        AccountType type = userInfo.GetAccountType();
        assertEquals(AccountType.User, type);
    }

    @Test
    public void TestGetOrganisationalUnit() {
        String unit = userInfo.GetOrganisationalUnit();
        assertEquals("Sales", unit);
    }
}