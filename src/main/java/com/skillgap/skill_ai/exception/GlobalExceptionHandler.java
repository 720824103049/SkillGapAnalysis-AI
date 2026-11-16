
package com.skillgap.skill_ai.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.skillgap.skill_ai.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, Object> response = new LinkedHashMap<>();

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        response.put("status", 400);
        response.put("message", errorMessage);

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<Map<String, Object>> handleNotFound(
        ResourceNotFoundException ex) {

    Map<String, Object> response = new LinkedHashMap<>();

    response.put("status", 404);
    response.put("message", ex.getMessage());

    return new ResponseEntity<>(
            response,
            HttpStatus.NOT_FOUND
    );
}
}
