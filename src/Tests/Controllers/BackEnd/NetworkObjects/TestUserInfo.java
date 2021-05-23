package Tests.Controllers.BackEnd.NetworkObjects;

import Controllers.BackEnd.AccountType;
import Controllers.BackEnd.NetworkObjects.UserInfo;
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
        String username = userInfo.getUsername();
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
        AccountType type = userInfo.getAccountType();
        assertEquals(AccountType.User, type);
    }

    @Test
    public void TestGetOrganisationalUnit() {
        String unit = userInfo.getOrganisationalUnit();
        assertEquals("Sales", unit);
    }
}