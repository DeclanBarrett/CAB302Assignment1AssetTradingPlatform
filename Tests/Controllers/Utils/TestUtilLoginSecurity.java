package Controllers.Utils;

import Controllers.Exceptions.ServerException;
import Controllers.Utils.UtilLoginSecurity;
import Controllers.Exceptions.AuthenticationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

public class TestUtilLoginSecurity {

    private UtilLoginSecurity utilLoginSecurity;

    @BeforeEach
    public void ConstructSecurity(){
        utilLoginSecurity = new UtilLoginSecurity();
    }

    @Test
    public void TestGenerateSalt() {
        assertNotEquals(utilLoginSecurity.generateSalt(), null);
    }

    @Test
    public void TestGenerateRandomSalt() {
        assertNotEquals(utilLoginSecurity.generateSalt(), utilLoginSecurity.generateSalt());
    }

    @Test
    public void TestNullHashPassword() throws NoSuchAlgorithmException {
        assertThrows(AuthenticationException.class, () -> {
            utilLoginSecurity.hashPassword(null, null);
        });
    }

    @Test
    public void TestHashPasswordIsntSame() throws AuthenticationException {
        assertNotEquals(utilLoginSecurity.hashPassword("Garry", "GARRY"), utilLoginSecurity.hashPassword("Garry", "12345"));
    }

    @Test
    public void TestTwoHashPasswords() throws AuthenticationException {
        assertEquals(utilLoginSecurity.hashPassword("Garry", "123"), utilLoginSecurity.hashPassword("Garry", "123"));
    }

    @Test
    public void TestTwoDifferentPasswords() throws AuthenticationException {
        assertNotEquals(utilLoginSecurity.hashPassword("JerementClarksonsBumFluff", "123"), utilLoginSecurity.hashPassword("JeremenyClarksonsBumFluff", "123"));
    }

    @Test
    public void TestTwoDifferentHashes() throws AuthenticationException {
        assertNotEquals(utilLoginSecurity.hashPassword("jbcddksfj2423j434jh234jl3h", utilLoginSecurity.generateSalt()), utilLoginSecurity.hashPassword("jbcddksfj2423j434jh234jl3h", utilLoginSecurity.generateSalt()));
    }

    @Test
    public void TestLargeHash() throws NoSuchAlgorithmException {
        assertDoesNotThrow(() -> {
            utilLoginSecurity.hashPassword("Garry", "lsflsdfhksdhfksdhksahdfkjhsdfkjhsadkfhskdhfksdhfkshdfkjhsdkfhsakdfhkjsdahfkjsahdfkjhsadkfhksadfhkjasdhfkjsahdfkjhk");
        });
    }

    @Test
    public void TestLargePassword() throws NoSuchAlgorithmException {
        assertDoesNotThrow(() -> {
            utilLoginSecurity.hashPassword("lsflsdfhksdhfksdhksahdfkjhsdfkjhsadkfhskdhfksdhfkshdfkjhsdkfhsakdfhkjsdahfkjsahdfkjhsadkfhksadfhkjasdhfkjsahdfkjhk", "kjasdhfkjsahdfkjhk");
        });
    }

    @Test
    public void TestGenerateHashPasswords() throws AuthenticationException, ServerException {
        assertEquals("b717415eb5e699e4989ef3e2c4e9cbf7", utilLoginSecurity.generateHashedPassword("User 1", "qwerty"));
    }

    @Test
    public void TestGenerateBadUsername() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            utilLoginSecurity.generateHashedPassword("Bad User", "qwerty");
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("USERNAME OR PASSWORD INCORRECT"));
    }
}
