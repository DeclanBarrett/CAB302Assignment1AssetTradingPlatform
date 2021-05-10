package Controllers.Backend;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JWTHandler {

    private static final String SERVER_SECRET = "ElonMuskMakesAGoodWario";
    private static final long EXPIRATION_LENGTH = 43200000;

    //https://github.com/auth0/java-jwt

    //https://developer.okta.com/blog/2018/10/31/jwts-with-java
    private void createToken(String username) {
        try {
            long nowMillis = System.currentTimeMillis();
            Date date = new Date(nowMillis + EXPIRATION_LENGTH);

            Algorithm algorithm = Algorithm.HMAC256(SERVER_SECRET);
            String token = JWT.create()
                    .withExpiresAt(date)
                    .withSubject(username)
                    .withIssuer("auth0")
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        }
    }

    private void verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
        }
    }
}
