package com.ems.employeemanagement.advice;

import com.ems.employeemanagement.model.response.ResponseMessage;
import com.ems.employeemanagement.model.response.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerApp {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleException(MethodArgumentNotValidException exception){
        Map<String,String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->{
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return errorMap;
    }
    @ExceptionHandler
    public ResponseEntity handleAccessDeniedException(AccessDeniedException e){

        ResponseMessage responseMessage=new ResponseMessage();

        responseMessage.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        responseMessage.setResponseStatus(ResponseStatus.Failure);
        responseMessage.setMessage("Access Denied..");

        return ResponseEntity.status(responseMessage.getStatusCode()).body(responseMessage);
    }
}
