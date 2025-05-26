package org.nettadx.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

  private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generate a valid secret key

  // 15 minutes
  private final long jwtExpiration = 15 * 60 * 1000;

  public String generateToken(Long id, String emailAddress, String role) {
    return Jwts.builder()
      .setSubject(emailAddress)
      .claim("role", role)
      .claim("id", id)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
      .signWith(jwtSecret) // Use the correct secret key
      .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parserBuilder() // Use parserBuilder instead of parser
      .setSigningKey(jwtSecret) // Set the signing key to validate the JWT
      .build() // Build the parser
      .parseClaimsJws(token) // Parse the JWT and extract claims
      .getBody() // Get the body of the claims
      .getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
        .setSigningKey(jwtSecret)
        .build()
        .parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
