package com.ems.employeemanagement.advice;

public class AccessDeniedException extends RuntimeException{
        public AccessDeniedException(String message){
            super(message);
        }
}
