package Controllers.BackEnd.Processing;

import Controllers.Exceptions.AuthenticationException;
import Controllers.Utils.UtilFieldCheckers;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class JWTHandler {

    public static final String ISSUER = "Unnamed Corporation";
    private final String SERVER_SECRET = "ElonMuskMakesAGoodWario";
    private final long EXPIRATION_LENGTH = 43200000;

    //https://github.com/auth0/java-jwt

    //https://developer.okta.com/blog/2018/10/31/jwts-with-java

    /**
     * Creates a new JWT token
     * @param username the username that the JWT belongs to
     * @throws AuthenticationException throws if a token cannot be constructed
     */
    public String createToken(String username) throws AuthenticationException {
        String token = null;
        try {
            UtilFieldCheckers.checkMissingValues(new ArrayList<>(Arrays.asList(username)));
            long nowMillis = System.currentTimeMillis();
            Date date = new Date(nowMillis + EXPIRATION_LENGTH);

            Algorithm algorithm = Algorithm.HMAC256(SERVER_SECRET);
            token = JWT.create()
                    .withExpiresAt(date)
                    .withSubject(username)
                    .withIssuer(ISSUER)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new AuthenticationException("Cannot Create Token");
        } catch (NullPointerException e) {
            throw new AuthenticationException("Cannot Create Token");
        }
        return token;
    }

    /**
     * Verifies an existing token and errors if its invalid
     * @param token the token to validate
     * @throws AuthenticationException the exception thrown that states what went wrong validating the token
     */
    public void verifyToken(String token) throws AuthenticationException {
        try {
            UtilFieldCheckers.checkMissingValues(new ArrayList<>(Arrays.asList(token)));
            Algorithm algorithm = Algorithm.HMAC256(SERVER_SECRET);
            System.out.println("Going to Verify");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("Verified Token " + jwt);
            return;
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            throw new AuthenticationException("Invalid Login Token");
        } catch (NullPointerException e) {
            throw new AuthenticationException("Invalid Login Token");
        }
    }

    /**
     * Gets the user from the token
     * @param token - the JWT token to gather the username from
     * @return - returns the user
     * @throws AuthenticationException - if the token isnt a valid token it fails
     */
    public String getUser(String token) throws AuthenticationException {
        try {
            UtilFieldCheckers.checkMissingValues(new ArrayList<>(Arrays.asList(token)));
            Algorithm algorithm = Algorithm.HMAC256(SERVER_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            throw new AuthenticationException("Invalid Login Token");
        } catch (NullPointerException e) {
            throw new AuthenticationException("Invalid Login Token");
        }
    }
}
