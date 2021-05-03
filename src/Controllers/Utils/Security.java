package Controllers.Utils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Large class for potential password storage,
 */
public class Security {

    /**
     * Generates random 18 character string.
     * @return returns random generated nonce.
     */
    public static String generateNonce()
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
    public static String getPassword(String userInputPassword, byte[]salt) throws NoSuchAlgorithmException {
        String securePassword = null;
        StringBuilder hexaDFormat = new StringBuilder();
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        md5.update(salt);
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


    /**
     * Generates salt for additional security
     * @return returns byte array
     * @throws NoSuchAlgorithmException thrown when requested algorithm does not exist.
     */
    public static byte[] getSalt() throws NoSuchAlgorithmException
    {
        // use SecureRandom for random generation
        SecureRandom secureSalt = SecureRandom.getInstance("SHA1PRNG");

        //array to store salt
        byte[] salt = new byte[16];

        //add salt to array
        secureSalt.nextBytes(salt);

        return salt;
    }







}
