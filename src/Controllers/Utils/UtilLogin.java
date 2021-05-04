package Controllers.Utils;

import Controllers.Backend.NetworkObjects.LoginToken;
import Controllers.Backend.NetworkObjects.User;
import Controllers.FrontEnd.LoginException;
import Models.MockSocket;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Used to check for correct login input.
 */
public class UtilLogin {

    private static LoginToken currentLogin;
    private static User currentUser;

    /**
     * Global Utility for attempting to login to the server
     */
    public static void AttemptLogin(String username, String password) throws LoginException {

        String hashPassword = ReceiveNonceAndHash(username, password);

        currentLogin = MockSocket.Login(username, hashPassword);

        if (currentLogin != null) {
            currentUser = MockSocket.GetUser(username);
            return;
        }

        throw new LoginException("Username or Password Incorrect 1");
    }

    public static User GetUser() {
        return currentUser;
    }

    public static void Logout() {
        currentLogin = null;
        currentUser = null;
    }

    /**
     * From a username and password, get a secure password
     * @param username The username that the password is for
     * @param password The password that the user is entering
     * @return A secure hashed version of the password
     * @throws LoginException Thrown in cases where the user doesnt exist or a connection error occurs
     */
    private static String ReceiveNonceAndHash(String username, String password) throws LoginException {

        String salt = MockSocket.RetrieveNonce(username);

        if (salt == null) {
            throw new LoginException("Username or Password Incorrect 2");
        }

        String hashedPassword = null;

        try {
            hashedPassword = getPassword(password, salt);
        } catch (NoSuchAlgorithmException e) {
            //In case the algorithm is missing (the SHA-256 in Message Digester)
            e.printStackTrace();
        }

        return hashedPassword;
    }


    /**
     * Generates random 18 character string.
     * @return returns random generated nonce.
     */
    private static String generateNonce()
    {
        SecureRandom generateRandNonce = new SecureRandom();
        StringBuilder storageForNonce = new StringBuilder();
        for (int i = 0; i < 18; i++)
        {
            storageForNonce.append(generateRandNonce.nextInt(10));
        }
        String nonce = storageForNonce.toString();
        return nonce;
    }

    /**
     * Generates secure password using md5 framework
     * @param userInputPassword password inputted by the user
     * @param salt salt array for security
     * @return returns a secure hashed string
     * @throws NoSuchAlgorithmException thrown when cryptographic algorithm is requested but it does
     * not exist.
     */
    public static String getPassword(String userInputPassword, String salt) throws NoSuchAlgorithmException {
        String securePassword = null;
        StringBuilder hexaDFormat = new StringBuilder();
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        md5.update(Byte.parseByte(salt));
        byte[] getHashBytes = md5.digest(userInputPassword.getBytes());

        // hexadecimal conversion taken from:
        // https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/

        // convets array from decimal format to hexadecimal.
        //
        for (int i = 0; i < getHashBytes.length; i++)
        {
            hexaDFormat.append(Integer.toString((getHashBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return securePassword = hexaDFormat.toString();
    }
}
