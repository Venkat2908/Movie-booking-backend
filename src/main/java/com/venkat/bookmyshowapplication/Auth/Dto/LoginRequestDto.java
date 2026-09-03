package com.venkat.bookmyshowapplication.Auth.Dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginRequestDto {
    @Email
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
