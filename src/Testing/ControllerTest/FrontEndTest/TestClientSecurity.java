package Testing.ControllerTest.FrontEndTest;

import Controllers.Backend.NetworkObjects.LoginToken;
import Controllers.FrontEnd.ClientSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestClientSecurity {

    private ClientSecurity clientSecurity;

    @BeforeEach
    public void ConstructSecurity(){
        clientSecurity = new ClientSecurity();
    }

    @Test
    public void TestGenerateSalt() {
        assertNotEquals(clientSecurity.generateSalt(), null);
    }

    @Test
    public void TestGenerateRandomSalt() {
        assertNotEquals(clientSecurity.generateSalt(), clientSecurity.generateSalt());
    }

    @Test
    public void TestNullHashPassword() throws NoSuchAlgorithmException {
        assertEquals(null, clientSecurity.hashPassword(null, null));
    }

    @Test
    public void TestHashPasswordIsntSame() throws NoSuchAlgorithmException {
        assertNotEquals(clientSecurity.hashPassword("Garry", "GARRY"), clientSecurity.hashPassword("Garry", "12345"));
    }

    @Test
    public void TestTwoHashPasswords() throws NoSuchAlgorithmException {
        assertEquals(clientSecurity.hashPassword("Garry", "123"), clientSecurity.hashPassword("Garry", "123"));
    }

    @Test
    public void TestTwoDifferentPasswords() throws NoSuchAlgorithmException {
        assertNotEquals(clientSecurity.hashPassword("JerementClarksonsBumFluff", "123"), clientSecurity.hashPassword("JeremenyClarksonsBumFluff", "123"));
    }

    @Test
    public void TestTwoDifferentHashes() throws NoSuchAlgorithmException {
        assertNotEquals(clientSecurity.hashPassword("jbcddksfj2423j434jh234jl3h", clientSecurity.generateSalt()), clientSecurity.hashPassword("jbcddksfj2423j434jh234jl3h", clientSecurity.generateSalt()));
    }

    @Test
    public void TestLargeHash() throws NoSuchAlgorithmException {
        assertDoesNotThrow(() -> {
            clientSecurity.hashPassword("Garry", "lsflsdfhksdhfksdhksahdfkjhsdfkjhsadkfhskdhfksdhfkshdfkjhsdkfhsakdfhkjsdahfkjsahdfkjhsadkfhksadfhkjasdhfkjsahdfkjhk");
        });
    }

    @Test
    public void TestLargePassword() throws NoSuchAlgorithmException {
        assertDoesNotThrow(() -> {
            clientSecurity.hashPassword("lsflsdfhksdhfksdhksahdfkjhsdfkjhsadkfhskdhfksdhfkshdfkjhsdkfhsakdfhkjsdahfkjsahdfkjhsadkfhksadfhkjasdhfkjsahdfkjhk", "kjasdhfkjsahdfkjhk");
        });
    }
}
