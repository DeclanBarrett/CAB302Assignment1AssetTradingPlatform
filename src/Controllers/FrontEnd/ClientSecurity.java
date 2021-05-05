package Controllers.FrontEnd;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Large class for potential password storage,
 */
public class ClientSecurity {

    public ClientSecurity() {
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
    public String hashPassword(String userInputPassword, String salt) throws NoSuchAlgorithmException {
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
