package Controllers.BackEnd.NetworkObjects;

import Controllers.BackEnd.AccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the user class
 */
public class TestUser {

    User user;

    @BeforeEach
    public void ConstructUser(){
        user = new User("Jack",  "b717415eb5e699e4989ef3e2c4e9cbf7", AccountType.User, "Sales", "12345");
    }

    @Test
    public void TestConstructBlankUsername() {
        user = new User();
    }

    @Test
    public void TestGetUsername() {
        String username = user.getUsername();
        assertEquals("Jack", username);
    }

    @Test
    public void TestGetAccountType() {
        AccountType type = user.getAccountType();
        assertEquals(AccountType.User, type);
    }

    @Test
    public void TestGetOrganisationalUnit() {
        String unit = user.getOrganisationalUnit();
        assertEquals("Sales", unit);
    }

    @Test
    public void TestPassword() {
        String password = user.getPassword();
        assertEquals("b717415eb5e699e4989ef3e2c4e9cbf7", password);
    }

    @Test
    public void TestSalt() {
        String salt = user.getSalt();
        assertEquals("12345", salt);
    }

    @Test
    public void TestSetUsername() {
        user.setUsername("Jack Ma");
        String username = user.getUsername();
        assertEquals("Jack Ma", username);
    }

    @Test
    public void TestSetAccountType() {
        user.setAccountType(AccountType.SystemAdmin);
        AccountType type = user.getAccountType();
        assertEquals(AccountType.SystemAdmin, type);
    }

    @Test
    public void TestSetOrganisationalUnit() {
        user.setOrganisationalType("Finance");
        String unit = user.getOrganisationalUnit();
        assertEquals("Finance", unit);
    }

    @Test
    public void TestSetPassword() {
        user.setPassword("OompaLoompa");
        String password = user.getPassword();
        assertEquals("OompaLoompa", password);
    }

    @Test
    public void TestSetSalt() {
        user.setSalt("qwerty");
        String salt = user.getSalt();
        assertEquals("qwerty", salt);
    }

    @Test
    public void TestIfEqual() {
        assertTrue(user.equals(user));
    }

    @Test
    public void TestEqualWithIncorrectObject() {
        assertFalse(user.equals(new User("Jack Ma",  "b717415eb5e699e4989ef3e2c4e9cbf7", AccountType.User, "Sales", "12345")));
    }

    @Test
    public void TestEqualWithIncorrectClass() {
        assertFalse(user.equals(new Date()));
    }

    @Test
    public void TestCompareOrg() {
        assertTrue(0 == user.compareTo(user));
    }
}
