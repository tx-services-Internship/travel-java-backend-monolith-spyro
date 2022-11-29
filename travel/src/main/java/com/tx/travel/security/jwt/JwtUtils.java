package com.tx.travel.security.jwt;

import com.tx.travel.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${travel.app.jwtSecret}")
  private String jwtSecret;

  @Value("${travel.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${travel.app.jwtCookieName}")
  private String jwtCookie;

  public String getJwtFromCookies(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
    if (cookie != null) {
      return cookie.getValue();
    } else {
      return null;
    }
  }

  public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
    String jwt = generateTokenFromUser(userPrincipal);
    return ResponseCookie.from(jwtCookie, jwt)
        .path("/api")
        .maxAge(24 * 60 * 60L)
        .secure(true)
        .httpOnly(true)
        .build();
  }

  public ResponseCookie generateJwtCookieFromToken(String jwt) {
    return ResponseCookie.from(jwtCookie, jwt)
        .path("/api")
        .maxAge(24 * 60 * 60L)
        .httpOnly(true)
        .build();
  }

  public ResponseCookie getCleanJwtCookie() {
    return ResponseCookie.from(jwtCookie, null).path("/api").build();
  }

  public String getUserNameFromJwtToken(String token) {

    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  public String generateTokenFromUser(UserDetailsImpl userPrincipal) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", userPrincipal.getId().toString());
    claims.put("name", userPrincipal.getName());
    claims.put("surname", userPrincipal.getSurname());
    claims.put("passportNo", userPrincipal.getPassportNo());
    claims.put("idNo", userPrincipal.getIdNo());
    claims.put("costCenterId", userPrincipal.getCostCenterId().toString());

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userPrincipal.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }
}
