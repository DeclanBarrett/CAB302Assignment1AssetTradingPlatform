package Controllers.BackEnd.NetworkObjects;

import Controllers.BackEnd.AccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestUser {
    User user;

    @BeforeEach
    public void ConstructUser() { user = new User("TestUser", "qwerty", AccountType.User, "Finance", "12345678"); }

    @Test
    void getUsername() { assertEquals("TestUser", user.getPassword());
    }

    // Dont think we need to test setters as Declan mentioned setters arent necessary, Will ask again about this today
    @Test
    void setUsername() {
    }

    @Test
    void getPassword() { assertEquals("qwerty", user.getPassword());
    }

    @Test
    void setPassword() {
    }

    @Test
    void getAccountType() { assertEquals(AccountType.User, user.getAccountType());
    }

    @Test
    void setAccountType() {
    }

    @Test
    void getOrganisationalUnit() { assertEquals("Finance", user.getAccountType());
    }

    @Test
    void setOrganisationalType() {
    }

    @Test
    void getSalt() { assertEquals("12335678", user.getSalt());
    }

    @Test
    void setSalt() {
    }
}