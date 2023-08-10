package com.ems.employeemanagement.service.impl;

import com.ems.employeemanagement.dto.LoginRequest;
import com.ems.employeemanagement.dto.UserRequest;
import com.ems.employeemanagement.model.User;
import com.ems.employeemanagement.exception.ValidationException;
import com.ems.employeemanagement.repository.UserRepository;
import com.ems.employeemanagement.service.UserService;
import com.ems.employeemanagement.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    protected PasswordEncoder passwordEncoder;

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
        String encoderPassword = passwordEncoder.encode(userRequest.getPassword());

        //request to model
        User user=new User(userRequest.getName(),userRequest.getEmail(),userRequest.getPhoneNumber(),encoderPassword);
        //save db
        User newUser = userRepository.save(user);

        logger.info("{}: Function end: UserServiceImpl.signUp()",traceId);
        return newUser;
    }

    @Override
    public String login(LoginRequest loginRequest, String traceId) throws ValidationException{
        logger.info("{}: Function start: UserServiceImpl.login()",traceId);
        User userInDb = fetchUserByEmail(loginRequest.getEmail());
        System.out.println(userInDb.getPassword());
        System.out.println(loginRequest.getPassword());
        System.out.println(passwordEncoder.matches(loginRequest.getPassword(),userInDb.getPassword()));
        if (userInDb==null) throw new ValidationException("User doesn't Exist..");
        if (!passwordEncoder.matches(loginRequest.getPassword(),userInDb.getPassword())) {
            throw new ValidationException("Email Id or password is incorrect");
        }
        String token = jwtUtils.generateJwt(userInDb);

        logger.info("{}: Function end: UserServiceImpl.login()",traceId);
        return token;
    }


    public User fetchUser(String userName){
        return userRepository.findUserByName(userName);
    }

    public User fetchUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }
}
