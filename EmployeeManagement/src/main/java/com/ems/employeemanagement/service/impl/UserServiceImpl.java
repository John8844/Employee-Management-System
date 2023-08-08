package com.ems.employeemanagement.service.impl;

import com.ems.employeemanagement.dto.LoginRequest;
import com.ems.employeemanagement.dto.UserRequest;
import com.ems.employeemanagement.model.User;
import com.ems.employeemanagement.exception.ValidationException;
import com.ems.employeemanagement.repository.UserRepository;
import com.ems.employeemanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User signUp(UserRequest userRequest,String traceId) throws ValidationException {
        User userInDB = fetchUser(userRequest.getName());
        logger.info("{}: Function start: UserServiceImpl.signUp()",traceId);
        //validation
        if (userInDB!=null) throw new ValidationException("User already Exist..");
        //request to model
        User user=new User(userRequest.getName(),userRequest.getEmail(),userRequest.getPhoneNumber(),userRequest.getPassword());
        //save db
        User newUser = userRepository.save(user);

        logger.info("{}: Function end: UserServiceImpl.signUp()",traceId);
        return newUser;
    }

    @Override
    public User login(LoginRequest loginRequest, String traceId) throws ValidationException{
        logger.info("{}: Function start: UserServiceImpl.login()",traceId);
        User userInDb = userRepository.findByEmailIgnoreCaseAndPassword(loginRequest.getEmail(),loginRequest.getPassword());
        if (userInDb==null) throw new ValidationException("User doesn't Exist..");
        User user = new User(loginRequest.getEmail(),loginRequest.getPassword());
        logger.info("{}: Function end: UserServiceImpl.login()",traceId);
        return user;
    }


    public User fetchUser(String userName){
        return userRepository.findUserByName(userName);
    }
}
