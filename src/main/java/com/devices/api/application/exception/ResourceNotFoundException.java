package com.devices.api.application.exception;

public class ResourceNotFoundException extends UseCaseException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
