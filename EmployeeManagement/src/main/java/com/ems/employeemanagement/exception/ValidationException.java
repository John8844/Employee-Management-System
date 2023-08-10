package com.ems.employeemanagement.exception;

public class ValidationException extends Exception {
    private static final long serialVersionUID = 1L;

    private final String exceptionMessage;

    public ValidationException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}

