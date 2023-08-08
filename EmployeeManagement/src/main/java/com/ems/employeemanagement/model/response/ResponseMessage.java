package com.ems.employeemanagement.model.response;

public class ResponseMessage {
    private int statusCode;

    private ResponseStatus responseStatus;

    private String message;

    public ResponseMessage(int statusCode, ResponseStatus responseStatus, String message) {
        this.statusCode = statusCode;
        this.responseStatus = responseStatus;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
