package Controllers.Backend;

import Controllers.FrontEnd.LoginException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JWTHandler {

    private final String SERVER_SECRET = "ElonMuskMakesAGoodWario";
    private final long EXPIRATION_LENGTH = 43200000;

    //https://github.com/auth0/java-jwt

    //https://developer.okta.com/blog/2018/10/31/jwts-with-java

    /**
     * Creates a new JWT token
     * @param username the username that the JWT belongs to
     * @throws LoginException throws if a token cannot be constructed
     */
    public String createToken(String username) throws LoginException {
        String token = null;
        try {
            long nowMillis = System.currentTimeMillis();
            Date date = new Date(nowMillis + EXPIRATION_LENGTH);

            Algorithm algorithm = Algorithm.HMAC256(SERVER_SECRET);
            token = JWT.create()
                    .withExpiresAt(date)
                    .withSubject(username)
                    .withIssuer("auth0")
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new LoginException("Cannot Create Token");
        }
        return token;
    }

    /**
     * Verifies an existing token and errors if its invalid
     * @param token the token to validate
     * @throws LoginException the exception thrown that states what went wrong validating the token
     */
    public void verifyToken(String token) throws LoginException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SERVER_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            throw new LoginException("Invalid Login Token");
        }
    }
}
