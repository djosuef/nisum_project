package com.example.security.config.exception;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NotFoundException extends RuntimeException{

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
    
}
