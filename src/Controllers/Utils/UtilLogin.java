package Controllers.Utils;

import Controllers.Backend.NetworkObjects.LoginToken;
import Controllers.Backend.NetworkObjects.User;
import Controllers.FrontEnd.LoginException;
import Models.MockSocket;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

        //https://www.javaguides.net/2020/02/java-sha-256-hash-with-salt-example.html

        //Attempts the hash the password

        String hashedPassword = null;

        try {
            //Get an instance of the message digester in security
            MessageDigest messageDigester = MessageDigest.getInstance("SHA-256");

            //Make the message digester use the salt retrieved from the database
            //It uses bytes so it must be converted to and from it with the help of string builder
            messageDigester.update(Byte.parseByte(salt));
            byte[] bytes = messageDigester.digest(password.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            //In case the algorithm is missing (the SHA-256 in Message Digester)
            e.printStackTrace();
        }

        return hashedPassword;
    }
}
