package com.venkat.bookmyshowapplication.Common.Exceptions;

public class ApiError extends RuntimeException {
    public ApiError(String message) {
        super(message);
    }
}
