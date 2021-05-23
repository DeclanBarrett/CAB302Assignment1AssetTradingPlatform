package Controllers.Utils;
import Controllers.BackEnd.Socket.MockSocket;
import Controllers.Exceptions.AuthenticationException;
import Controllers.Exceptions.ServerException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Large class for potential password storage,
 */
public class UtilLoginSecurity {

    public static final String MD_5 = "MD5";

    public static final String LOGIN_ERROR_USERNAME_PASSWORD_2 = "USERNAME OR PASSWORD INCORRECT";

    /**
     * Creates login security
     */
    public UtilLoginSecurity() {
    }

    /**
     * Generates random 18 character string.
     * @return returns random generated nonce.
     */
    public String generateSalt()
    {
        SecureRandom generateRandSalt = new SecureRandom();
        StringBuilder storageForSalt = new StringBuilder();
        for (int i = 0; i < 18; i++)
        {
            storageForSalt.append(generateRandSalt.nextInt(10));
        }
        String salt = storageForSalt.toString();
        return salt;
    }

    /**
     * Generates secure password using md5 framework
     * @param userInputPassword password inputted by the user
     * @param salt salt array for security
     * @return returns a secure hashed string
     * @throws NoSuchAlgorithmException thrown when cryptographic algorithm is requested but it does
     * not exist.
     */
    public String hashPassword(String userInputPassword, String salt) throws AuthenticationException {

        StringBuilder hexaDFormat = new StringBuilder();

        try {
            MessageDigest md5 = MessageDigest.getInstance(MD_5);

            md5.update(salt.getBytes());
            byte[] getHashBytes = md5.digest(userInputPassword.getBytes());

            // hexadecimal conversion taken from:
            // https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/

            // convets array from decimal format to hexadecimal.
            //
            for (int i = 0; i < getHashBytes.length; i++) {
                hexaDFormat.append(Integer.toString((getHashBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (Exception e) {
            throw new AuthenticationException("PASSWORD HASHING HAS FAILED");
        }
        return hexaDFormat.toString();
    }

    /**
     * Generates a hashed password from the password and the user's salt
     * @param username - the username for the user
     * @param password - the password to hash
     * @return the hashed password
     * @throws AuthenticationException if a username or password is mismatched
     */
    public String generateHashedPassword(String username, String password) throws AuthenticationException, ServerException {

        //Get the salt for the password
        String salt = MockSocket.getInstance().GetSalt(username);

        //Make sure the salt isn't blank
        if (salt == null) {
            throw new AuthenticationException(LOGIN_ERROR_USERNAME_PASSWORD_2);
        }

        // Generates salted and hashed password using md5 algorithm.
        //String salt = securityManager.generateSalt();
        String hashedPassword = hashPassword(password, salt);

        System.out.println("Password: " + password + ", Salt: " + salt);
        System.out.println("Hashed Password: " + hashedPassword);

        return hashedPassword;
    }

}
