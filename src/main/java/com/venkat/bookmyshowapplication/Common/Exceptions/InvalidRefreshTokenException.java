package com.venkat.bookmyshowapplication.Common.Exceptions;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}
