package com.ems.employeemanagement.controller;

import com.ems.employeemanagement.dto.UserRequest;
import com.ems.employeemanagement.exception.ValidationException;
import com.ems.employeemanagement.model.response.ResponseMessage;
import com.ems.employeemanagement.model.response.ResponseStatus;
import com.ems.employeemanagement.service.UserService;
import com.ems.employeemanagement.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class UserController {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

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

    }
}
