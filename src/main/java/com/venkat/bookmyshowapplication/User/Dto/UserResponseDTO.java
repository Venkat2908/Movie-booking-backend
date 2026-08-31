package com.venkat.bookmyshowapplication.User.Dto;


import com.venkat.bookmyshowapplication.User.Model.RegisterationResult;
import com.venkat.bookmyshowapplication.User.Model.UserResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private long id;
    private String name;
    private String email;
    private UserResponseStatus status;
    private RegisterationResult registerationResult;
}
