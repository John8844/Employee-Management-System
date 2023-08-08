package com.ems.employeemanagement.service;

import com.ems.employeemanagement.dto.LoginRequest;
import com.ems.employeemanagement.dto.UserRequest;
import com.ems.employeemanagement.exception.ValidationException;
import com.ems.employeemanagement.model.User;


public interface UserService {

    /**
     * Function to create User
     * @param userRequest
     * @param traceId
     * @return
     * @throws ValidationException
     */
     User signUp(UserRequest userRequest,String traceId) throws ValidationException;


    /**
     * Function to log in user
     * @param loginRequest
     * @param traceId
     * @return
     * @throws ValidationException
     */
    User login(LoginRequest loginRequest, String traceId) throws ValidationException;
}
