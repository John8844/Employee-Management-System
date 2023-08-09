package com.ems.employeemanagement.util;

import com.ems.employeemanagement.advice.AccessDeniedException;
import com.ems.employeemanagement.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static String secret = "123";
    public String generateJwt(User user){
        Date issuedAt = new Date();

        Claims claims= Jwts.claims().setIssuer(user.getName()).setIssuedAt(issuedAt);

        return Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }

    public void verify(String authorization){
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization);
        }catch (Exception e){
            throw new AccessDeniedException("Access Denied..");
        }
    }
}
