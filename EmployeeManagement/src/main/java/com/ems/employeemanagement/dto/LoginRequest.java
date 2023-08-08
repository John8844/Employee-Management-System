package com.ems.employeemanagement.dto;

import javax.persistence.Column;

public class LoginRequest {
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
