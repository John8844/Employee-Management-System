package com.ems.employeemanagement.util;

import com.ems.employeemanagement.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static String secret = "123";
    public String generateJwt(User user){
        Date issuedAt = new Date();

        Claims claims= Jwts.claims().setIssuer(user.getName()).setIssuedAt(issuedAt);

        return Jwts.builder().setClaims(claims).compact();
    }
}