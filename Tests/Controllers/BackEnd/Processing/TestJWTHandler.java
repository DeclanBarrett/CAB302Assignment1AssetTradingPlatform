package Controllers.BackEnd.Processing;

import Controllers.Exceptions.AuthenticationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TestJWTHandler {

    JWTHandler jwtHandler;

    String user = "Jack";
    String errorMessage = "Invalid Login Token";

    @BeforeEach
    public void ConstructJWTHandler() {
        jwtHandler = new JWTHandler();
    }

    @Test
    public void TestCreateTokenStandard() throws AuthenticationException {
        String jwt = jwtHandler.createToken(user);
        assertEquals(user, jwtHandler.getUser(jwt));
    }

    @Test
    public void TestCreateTokenNull() {
        Exception exception = assertThrows(AuthenticationException.class, () -> {
            jwtHandler.createToken(null);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Cannot Create Token"));
    }

    @Test
    public void TestCreateTokenLong() throws AuthenticationException {
        String jwt = jwtHandler.createToken("123456789012345678901234567890123456789012345678901234567890");
        assertEquals("123456789012345678901234567890123456789012345678901234567890", jwtHandler.getUser(jwt));
    }

    @Test
    public void TestCreateTokenCheckEquals() throws AuthenticationException {
        assertEquals(jwtHandler.createToken(user), jwtHandler.createToken(user));
    }

    @Test
    public void TestVerifyTokenStandard() throws AuthenticationException {
        String jwt = jwtHandler.createToken(user);
        jwtHandler.verifyToken(jwt);
    }

    @Test
    public void TestVerifyTokenNull() {
        String jwt = null;

        Exception exception = assertThrows(AuthenticationException.class, () -> {
            jwtHandler.verifyToken(jwt);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(errorMessage));
    }

    @Test
    public void TestVerifyTokenRandomString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        Exception exception = assertThrows(AuthenticationException.class, () -> {
            jwtHandler.verifyToken(generatedString);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(errorMessage));
    }

    @Test
    public void TestVerifyUserStandard() throws AuthenticationException {
        String jwt = jwtHandler.createToken(user);
        assertEquals(user, jwtHandler.getUser(jwt));
    }

    @Test
    public void TestVerifyUserNull() throws AuthenticationException {

        String jwt = null;

        Exception exception = assertThrows(AuthenticationException.class, () -> {
            jwtHandler.verifyToken(jwt);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(errorMessage));
    }

    @Test
    public void TestVerifyUserRandomString() throws AuthenticationException {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        Exception exception = assertThrows(AuthenticationException.class, () -> {
            jwtHandler.getUser(generatedString);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(errorMessage));
    }
}
