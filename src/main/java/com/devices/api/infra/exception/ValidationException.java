package com.devices.api.infra.exception;

import com.devices.api.domain.exception.DomainException;

public class ValidationException extends DomainException {
    public ValidationException(String message) {
        super(message);
    }
}
