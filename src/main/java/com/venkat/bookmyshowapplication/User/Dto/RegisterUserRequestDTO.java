package com.venkat.bookmyshowapplication.User.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequestDTO {
    private String name;
    private String email;
    private String password;
}
