package com.devices.api.application.exception;

public class UseCaseException extends RuntimeException{
    public UseCaseException(String message) {
        super(message);
    }
}
