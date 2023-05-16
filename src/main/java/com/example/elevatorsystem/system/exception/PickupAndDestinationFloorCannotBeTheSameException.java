package com.example.elevatorsystem.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PickupAndDestinationFloorCannotBeTheSameException extends RuntimeException {
    public PickupAndDestinationFloorCannotBeTheSameException(String message) {
        super(message);
    }
}
