package com.ems.employeemanagement.configurations;

import com.ems.employeemanagement.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String auth = request.getHeader("authorization");

        if (!shouldSkipAuthentication(request.getRequestURI())) {
            jwtUtils.verify(auth);
        }
        return true;
    }

    private boolean shouldSkipAuthentication(String requestURI) {
        return requestURI.contains("/login") || requestURI.contains("/signup");
    }
}

