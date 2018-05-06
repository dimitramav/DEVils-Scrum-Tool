package ys09.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

public class CustomAuth {

  public String createToken(String mail) {
      Key key = MacProvider.generateKey();

      String compactJws =  Jwts.builder()
              .setSubject(mail)
              .signWith(SignatureAlgorithm.HS512, key)
              .compact();

      return compactJws;
  }

}
