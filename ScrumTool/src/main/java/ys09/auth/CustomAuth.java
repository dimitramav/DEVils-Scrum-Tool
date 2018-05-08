package ys09.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;
import ys09.conf.Configuration;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;

public class CustomAuth {

    // Creates an auth token
    // TODO: Add more parameters
  public String createToken(String mail) {
      // the key would be read from our application configuration.
      Configuration config = Configuration.getInstance();
      String compactJws =  Jwts.builder()
              .setSubject(mail)
              .signWith(SignatureAlgorithm.HS512, config.getKey())
              .compact();

      return compactJws;
  }

  // Checks if auth token is valid
  public boolean checkAuthToken(String token) {
  // TODO: Where to Store the key ??? Dimitra
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
      catch( MalformedJwtException e1) {
        // Parser Exception
        // It's not in token format with the double dots
        return false;
      }
  }

  // Extracts the data from the Auth Token
    
}
