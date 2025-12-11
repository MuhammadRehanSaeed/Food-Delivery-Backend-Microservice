package com.rehancode.user_service.Exceptions;

public class AllFieldsRequired extends RuntimeException{

    public AllFieldsRequired(String message) {
        super(message);
    }
}