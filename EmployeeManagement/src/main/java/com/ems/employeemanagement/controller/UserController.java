package com.ems.employeemanagement.controller;

import com.ems.employeemanagement.dto.LoginRequest;
import com.ems.employeemanagement.dto.UserRequest;
import com.ems.employeemanagement.exception.ValidationException;
import com.ems.employeemanagement.model.response.ResponseMessage;
import com.ems.employeemanagement.model.response.ResponseStatus;
import com.ems.employeemanagement.service.UserService;
import com.ems.employeemanagement.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class UserController {

    @Autowired
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * REST API for New User Registration
     * @param userRequest
     * @return
     */

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserRequest userRequest){
        String traceId = Util.getTrackingId();
        logger.info("{}: Post Request to create User, User: {}",traceId,userRequest.getName());
        try {
            userService.signUp(userRequest,traceId);
            return new ResponseEntity<>(new ResponseMessage(201, ResponseStatus.Successful,"User created Successfully.."),HttpStatus.CREATED);
        }
        catch (ValidationException v) {
            return new ResponseEntity<>(new ResponseMessage(403, ResponseStatus.Failure,v.getExceptionMessage()), HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new ResponseMessage(500, ResponseStatus.Failure,"Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * REST API for User Log in
     * @param loginRequest
     * @return
     * @throws ValidationException
     */

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest){
        String traceId = Util.getTrackingId();
        logger.info("{}: Post Request to login User, Email: {}",traceId,loginRequest.getEmail());
        try {
            String token = userService.login(loginRequest,traceId);
            return new ResponseEntity<>(new ResponseMessage(201, ResponseStatus.Successful,token),HttpStatus.OK);
        }
        catch (ValidationException v) {
            return new ResponseEntity<>(new ResponseMessage(403, ResponseStatus.Failure,v.getExceptionMessage()), HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new ResponseMessage(500, ResponseStatus.Failure,"Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
