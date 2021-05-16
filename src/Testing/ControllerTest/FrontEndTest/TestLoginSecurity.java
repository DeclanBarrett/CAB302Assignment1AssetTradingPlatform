package Testing.ControllerTest.FrontEndTest;

import Controllers.Utils.LoginSecurity;
import Controllers.Exceptions.LoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

public class TestLoginSecurity {

    private LoginSecurity loginSecurity;

    @BeforeEach
    public void ConstructSecurity(){
        loginSecurity = new LoginSecurity();
    }

    @Test
    public void TestGenerateSalt() {
        assertNotEquals(loginSecurity.generateSalt(), null);
    }

    @Test
    public void TestGenerateRandomSalt() {
        assertNotEquals(loginSecurity.generateSalt(), loginSecurity.generateSalt());
    }

    @Test
    public void TestNullHashPassword() throws NoSuchAlgorithmException {
        assertThrows(LoginException.class, () -> {
            loginSecurity.hashPassword(null, null);
        });
    }

    @Test
    public void TestHashPasswordIsntSame() throws LoginException {
        assertNotEquals(loginSecurity.hashPassword("Garry", "GARRY"), loginSecurity.hashPassword("Garry", "12345"));
    }

    @Test
    public void TestTwoHashPasswords() throws LoginException {
        assertEquals(loginSecurity.hashPassword("Garry", "123"), loginSecurity.hashPassword("Garry", "123"));
    }

    @Test
    public void TestTwoDifferentPasswords() throws LoginException {
        assertNotEquals(loginSecurity.hashPassword("JerementClarksonsBumFluff", "123"), loginSecurity.hashPassword("JeremenyClarksonsBumFluff", "123"));
    }

    @Test
    public void TestTwoDifferentHashes() throws LoginException {
        assertNotEquals(loginSecurity.hashPassword("jbcddksfj2423j434jh234jl3h", loginSecurity.generateSalt()), loginSecurity.hashPassword("jbcddksfj2423j434jh234jl3h", loginSecurity.generateSalt()));
    }

    @Test
    public void TestLargeHash() throws NoSuchAlgorithmException {
        assertDoesNotThrow(() -> {
            loginSecurity.hashPassword("Garry", "lsflsdfhksdhfksdhksahdfkjhsdfkjhsadkfhskdhfksdhfkshdfkjhsdkfhsakdfhkjsdahfkjsahdfkjhsadkfhksadfhkjasdhfkjsahdfkjhk");
        });
    }

    @Test
    public void TestLargePassword() throws NoSuchAlgorithmException {
        assertDoesNotThrow(() -> {
            loginSecurity.hashPassword("lsflsdfhksdhfksdhksahdfkjhsdfkjhsadkfhskdhfksdhfkshdfkjhsdkfhsakdfhkjsdahfkjsahdfkjhsadkfhksadfhkjasdhfkjsahdfkjhk", "kjasdhfkjsahdfkjhk");
        });
    }
}
