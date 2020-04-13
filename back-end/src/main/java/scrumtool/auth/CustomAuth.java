package scrumtool.auth;

import scrumtool.conf.Configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;


public class CustomAuth {

    // Creates an auth token
    // TODO: Add more parameters
    public String createToken(String userId) {
        // the key would be read from our application configuration.
        Configuration config = Configuration.getInstance();
        String compactJws =  Jwts.builder()
                .setSubject(userId)
                .signWith(SignatureAlgorithm.HS512, config.getKey())
                .compact();

        return compactJws;
    }

    // Checks if auth token is valid
    public boolean checkAuthToken(String token) {
        try {
            Configuration config = Configuration.getInstance();
            Jwts.parser().setSigningKey(config.getKey()).parseClaimsJws(token);
            return true;
            //OK, we can trust this JWT

        } catch (SignatureException e) {
            // Signature Exception
            // It has not been signed by our API
            return false;
            //don't trust the JWT!
        }
        catch (MalformedJwtException e1) {
            // Parser Exception
            // It's not in token format with the double dots
            return false;
        }
    }

    // Get UserId
    public boolean userValidation(String token, String userId) {
        try {
            Configuration config = Configuration.getInstance();
            String subject = Jwts.parser().setSigningKey(config.getKey()).parseClaimsJws(token).getBody().getSubject();
            //System.out.println(subject);
            //System.out.println("xoxo");
            if (subject.equals(userId)) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (SignatureException e) {
            return false;
        }
        catch (MalformedJwtException el) {
            return false;
        }
    }
    // Extracts the data from the Auth Token
}
