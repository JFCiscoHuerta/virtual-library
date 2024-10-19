package com.gklyphon.VirtualLibrary.exception.custom;

/**
 * Custom exception thrown when an element is not found in the system.
 * This extends RuntimeException to allow unchecked propagation of the error.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 18-Oct-2024
 */
public class ElementNotFoundException extends RuntimeException {

    /**
     * Creates a new ElementNotFoundException with the specified detail message.
     *
     * @param message a descriptive message explaining the cause of the exception
     */
    public ElementNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a new ElementNotFoundException with the specified message and cause.
     *
     * @param message a descriptive message explaining the cause of the exception
     * @param cause the underlying reason for this exception (can be null)
     */
    public ElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
