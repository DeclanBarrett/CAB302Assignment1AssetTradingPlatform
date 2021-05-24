package Controllers.BackEnd.NetworkObjects;

import Controllers.BackEnd.AccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUser {

    User user;

    @BeforeEach
    public void ConstructUser(){
        user = new User("Jack",  "b717415eb5e699e4989ef3e2c4e9cbf7", AccountType.User, "Sales", "12345");
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
}
