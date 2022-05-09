package com.example.security.config;

import com.example.security.config.exception.BadRequestException;
import com.example.security.config.exception.NotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionConfig {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity <?> notFoundException (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({BadRequestException.class,
                      org.springframework.http.converter.HttpMessageNotReadableException.class,
                      org.springframework.web.HttpRequestMethodNotSupportedException.class,
                      org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class,
                      })
    public ResponseEntity <JsonNode> BadRequestException (Exception e) {

        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode errorJson = nodeFactory.objectNode();       

        if (e.getClass() == org.springframework.http.converter.HttpMessageNotReadableException.class){
            errorJson.put("message", "El id debe ser un número");
            return new ResponseEntity<>(errorJson, HttpStatus.BAD_REQUEST);
        }
        if (e.getClass() == org.springframework.web.HttpRequestMethodNotSupportedException.class){
            errorJson.put("message", "El parámetro 'id' es obligatorio");
            return new ResponseEntity<>(errorJson, HttpStatus.BAD_REQUEST);
        }
        if (e.getClass() == org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class){
            errorJson.put("message", "El parámetro 'id' debe ser un número");
            return new ResponseEntity<>(errorJson, HttpStatus.BAD_REQUEST);
        }
        
        errorJson.put("message", e.getMessage());
        return new ResponseEntity<>(errorJson, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({org.springframework.dao.QueryTimeoutException.class,
        java.lang.NullPointerException.class,
        org.springframework.dao.DataIntegrityViolationException.class})
    public ResponseEntity<JsonNode> InternalException2(Exception e){

        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode errorJson = nodeFactory.objectNode();
        errorJson.put("message", "Ocurrio un error interno:" + e.getMessage());

        return new ResponseEntity<>(errorJson, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*@Override
    protected ResponseEntity<?> handleHttpRequestMethodNotSupported(
    HttpRequestMethodNotSupportedException ex, 
    HttpHeaders headers, HttpStatus status, WebRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Error");

    }*/
    
}
