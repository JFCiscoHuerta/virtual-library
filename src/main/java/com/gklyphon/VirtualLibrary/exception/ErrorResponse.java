package com.gklyphon.VirtualLibrary.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Represents a standardized error response for API exceptions.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 18-Oct-2024
 */
@Data
public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    /**
     * Constructs an ErrorResponse with the provided status and message.
     *
     * @param status  HTTP status code of the error
     * @param message Description of the error
     */
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructs an ErrorResponse using the HttpStatus enum.
     *
     * @param status  HTTP status of the error
     * @param message Description of the error
     */
    public ErrorResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }


}
