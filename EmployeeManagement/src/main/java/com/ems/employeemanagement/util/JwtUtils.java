package com.ems.employeemanagement.util;

import com.ems.employeemanagement.advice.AccessDeniedException;
import com.ems.employeemanagement.configurations.Authorization;
import com.ems.employeemanagement.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}") //${jwt.secret}
    private String secret;

    @Value("${jwt.expiration}")  //${jwt.expiration}
    private long expiration;

    public String generateJwt(User user) {
        Date issuedAt = new Date();

        Claims claims = Jwts.claims().setIssuer(user.getName()).setIssuedAt(issuedAt);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(issuedAt.getTime() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean verify(String authorization){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization);
            return true;
        } catch (Exception e) {
            throw new AccessDeniedException("Access Denied..");
        }
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Authorization getAllClaimsFromToken(String token) throws JwtException {
        try {
            var claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            String name = claims.get("name", String.class);
            String role = claims.get("role", String.class);
            return new Authorization(name, role);
        } catch (JwtException  e) {
            System.out.println("Getting Error"+e);
            e.printStackTrace();
            throw  e;
        }
    }
}


