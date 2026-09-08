package com.venkat.bookmyshowapplication.Common.Exceptions;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException(String message) {
        super(message);
    }

    public static class InvalidRefreshTokenException extends RuntimeException{
        public LoginCredientialsmismatchException(String message) {
            super(message);
        }
    }
}
