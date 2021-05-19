package Tests.Controllers.NetworkObjects;

import Controllers.Backend.NetworkObjects.LoginToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLoginToken {

    private LoginToken loginToken;

    @BeforeEach
    public void ConstructToken(){
        loginToken = new LoginToken("User 1", new Date());
    }

    @Test
    public void TestUsername() {
        assertEquals("User 1", loginToken.getUsername());
    }

    @Test
    public void TestDate() {
        assertEquals(new Date(), loginToken.getDate());
    }
}
