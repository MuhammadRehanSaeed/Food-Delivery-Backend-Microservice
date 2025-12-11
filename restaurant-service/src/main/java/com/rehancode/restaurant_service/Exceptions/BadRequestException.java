package com.rehancode.restaurant_service.Exceptions;


public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

}