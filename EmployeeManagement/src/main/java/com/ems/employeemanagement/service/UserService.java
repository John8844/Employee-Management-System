package com.ems.employeemanagement.service;

import com.ems.employeemanagement.dto.UserRequest;
import com.ems.employeemanagement.exception.ValidationException;
import com.ems.employeemanagement.model.User;


public interface UserService {

     User signUp(UserRequest userRequest,String traceId) throws ValidationException;

}
