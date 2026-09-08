package com.venkat.bookmyshowapplication.auth.ControllerAdvice;

import com.venkat.bookmyshowapplication.Common.Exceptions.InvalidRefreshTokenException;
import com.venkat.bookmyshowapplication.auth.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ExceptionDto> handleinvalidrefreshtoken(InvalidRefreshTokenException exception) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(exception.getMessage());
        exceptionDto.setSolution("Please re-login Again");

        ResponseEntity<ExceptionDto> response = new ResponseEntity<>(
                exceptionDto,
                HttpStatus.UNAUTHORIZED
        );

        return response;
    }
}
