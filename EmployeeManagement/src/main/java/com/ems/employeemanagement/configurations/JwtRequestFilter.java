package com.ems.employeemanagement.configurations;

import com.ems.employeemanagement.model.response.ResponseMessage;
import com.ems.employeemanagement.model.response.ResponseStatus;
import com.ems.employeemanagement.util.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(!isOpenApi(uri)){
            String requestHeader=request.getHeader("Authorization");
            String jwtToken;
            //Validation
            if(requestHeader == null || !requestHeader.startsWith("Bearer ")){
                ResponseMessage responseMessage = new ResponseMessage(400, ResponseStatus.Failure,"Invalid Token");
                PrintWriter writer=response.getWriter();
                response.setContentType("application/json");
                response.setStatus(400);
                writer.write(convertObjectToJson(responseMessage));
                writer.flush();
                return;
            }

            Authorization authorization=null;
            if(requestHeader != null && requestHeader.startsWith("Bearer ")){
                jwtToken=requestHeader.substring(7);
                try{
                    authorization = jwtUtils.getAllClaimsFromToken(jwtToken);
                }catch (IllegalArgumentException e){
                    logger.info("Unable to fetch JWT Token", e);
                    ResponseMessage responseMessage = new ResponseMessage(401, ResponseStatus.Failure,"Unable to validate signature from JWT Token, please check the format");
                    PrintWriter writer = response.getWriter();
                    response.setContentType("application/json");
                    response.setStatus(401);
                    writer.write(convertObjectToJson(responseMessage));
                    writer.flush();
                    return;
                }catch (ExpiredJwtException e) {
                    logger.info("JWT Token has expired", e);
                    ResponseMessage responseMessage = new ResponseMessage(401, ResponseStatus.Failure, "JWT Token has expired");
                    PrintWriter writer = response.getWriter();
                    response.setStatus(401);
                    response.setContentType("application/json");
                    writer.write(convertObjectToJson(responseMessage));
                    writer.flush();
                    return;
                } catch (MalformedJwtException e) {
                    logger.info("Jwt validation failed", e);
                    ResponseMessage responseMessage = new ResponseMessage(401, ResponseStatus.Failure,"JWT validation failed");
                    PrintWriter writer = response.getWriter();
                    response.setStatus(401);
                    response.setContentType("application/json");
                    writer.write(convertObjectToJson(responseMessage));
                    writer.flush();
                    return;
                }catch (Throwable e) {
                    // Handle any exception or error
                    logger.info("Jwt validation failed", e);
                    e.printStackTrace(); // Print the exception stack trace for debugging
                    ResponseMessage responseMessage = new ResponseMessage(401, ResponseStatus.Failure,"JWT validation failed");
                    PrintWriter writer = response.getWriter();
                    response.setStatus(401);
                    response.setContentType("application/json");
                    writer.write(convertObjectToJson(responseMessage));
                    writer.flush();
                    return;
                }
            }
            else {
                logger.error("Token passed is null/JWT Token does not begin with Bearer String");
                logger.info("Incorrect Jwt Token format");
                ResponseMessage responseMessage = new ResponseMessage(401,ResponseStatus.Failure,"Incorrect Jwt Token format");
                PrintWriter writer = response.getWriter();
                response.setStatus(401);
                response.setContentType("application/json");
                writer.write(convertObjectToJson(responseMessage));
                writer.flush();
                return;
            }
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SSOAuthentication ssoAuthentication = new SSOAuthentication();
                ssoAuthentication.setPrincipal(authorization);
                ssoAuthentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(ssoAuthentication);
            }
        }
        filterChain.doFilter(request,response);
    }

    private String convertObjectToJson(Object obj) throws JsonProcessingException {
        if (obj == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    private boolean isOpenApi(String uri) {
        return uri.contains("/login") || uri.contains("/register");
    }
}
