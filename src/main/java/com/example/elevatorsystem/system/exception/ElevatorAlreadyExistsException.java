package com.example.elevatorsystem.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ElevatorAlreadyExistsException extends RuntimeException {
    public ElevatorAlreadyExistsException(String message) {
        super(message);
    }
}
