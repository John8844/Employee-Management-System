package com.ems.employeemanagement.configurations;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SSOAuthentication implements Authentication {

    private Authorization authorization;
    private boolean authentication = false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return authorization;
    }
    public void setPrincipal(Authorization auth) {
        authorization = auth;
    }

    @Override
    public boolean isAuthenticated() {
        return authentication;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authentication=isAuthenticated;
    }

    @Override
    public String getName() {
        return "NAME";
    }
}
