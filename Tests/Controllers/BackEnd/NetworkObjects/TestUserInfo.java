package Controllers.BackEnd.NetworkObjects;

import Controllers.BackEnd.AccountType;
import Controllers.BackEnd.NetworkObjects.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void TestIfEqual() {
        assertTrue(userInfo.equals(userInfo));
    }

    @Test
    public void TestEqualWithIncorrectObject() {
        assertFalse(userInfo.equals(new UserInfo("Jack Ma",  AccountType.User, "Sales")));
    }

    @Test
    public void TestEqualWithIncorrectClass() {
        assertFalse(userInfo.equals(new Date()));
    }

    @Test
    public void TestCompareOrg() {
        assertTrue(0 == userInfo.compareTo(userInfo));
    }
}