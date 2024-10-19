package com.gklyphon.VirtualLibrary.exception;

import com.gklyphon.VirtualLibrary.exception.custom.ElementNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the Virtual Library API.
 * This class centralizes exception management and ensures
 * consistent error responses.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 18-Oct-2024
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ElementNotFoundException and returns a NOT_FOUND response.
     *
     * @param ex the exception that was thrown when an element was not found
     * @return ResponseEntity containing the exception message and HTTP status 404
     */
    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleElementNotFound(ElementNotFoundException ex) {
        log.error("Element not found: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}