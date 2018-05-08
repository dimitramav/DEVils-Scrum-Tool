package ys09.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;

public class CustomAuth {

    // Creates an auth token
    // TODO: Add more parameters
  public String createToken(String mail) {

      // Store the Key Somewhere safe
      String compactJws =  Jwts.builder()
              .setSubject(mail)
              .signWith(SignatureAlgorithm.HS512, "scrummm")
              .compact();

      return compactJws;
  }

  // Checks if auth token is valid
  public boolean checkAuthToken(String token) {
  // TODO: Where to Store the key ??? Dimitra
      try {
          Jwts.parser().setSigningKey("scrummm").parseClaimsJws(token);
          return true;
          //OK, we can trust this JWT

      } catch (SignatureException e) {
        return false;
          //don't trust the JWT!
      }
  }

  // Extracts the data from the Auth Token
    
}
