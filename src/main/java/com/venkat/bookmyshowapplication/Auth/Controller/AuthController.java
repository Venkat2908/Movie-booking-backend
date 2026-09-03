package com.venkat.bookmyshowapplication.Auth.Controller;

import com.venkat.bookmyshowapplication.Auth.Dto.LoginRequestDto;
import com.venkat.bookmyshowapplication.Auth.Dto.LoginResponseDto;
import com.venkat.bookmyshowapplication.Auth.Model.ResponseStatus;
import com.venkat.bookmyshowapplication.Auth.Model.Token;
import com.venkat.bookmyshowapplication.Auth.Service.AuthService;
import com.venkat.bookmyshowapplication.Common.Exceptions.LoginCredientialsmismatchException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginrequest(@Valid @RequestBody LoginRequestDto requestdto) throws LoginCredientialsmismatchException {
        Token token = authService.LoginValidation(requestdto.getEmail(), requestdto.getPassword());
        LoginResponseDto dto = new LoginResponseDto();

        dto.setAccessToken(token.getAccesstoken());
        dto.setAccessType(token.getAccessType());
        dto.setEmail(token.getUser_details().getEmail());
        dto.setExpiry_time_in_Seconds(token.getExpriy_time());
        dto.setRefreshtoken(token.getRefreshtoken());
        dto.setId(token.getUser_details().getId());

        if (token.getAccesstoken().isEmpty()){
            dto.setResponseStatus(ResponseStatus.LOGIN_FAILED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(dto);
        }
            dto.setResponseStatus(ResponseStatus.LOGIN_SUCCESSFUL);
         return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
