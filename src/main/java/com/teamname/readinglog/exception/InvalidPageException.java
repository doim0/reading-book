package com.teamname.readinglog.exception;

public class InvalidPageException extends RuntimeException {
    public InvalidPageException(String message) {
        super(message);
    }
    
    public InvalidPageException(String message, Throwable cause) {
        super(message, cause);
    }
}
