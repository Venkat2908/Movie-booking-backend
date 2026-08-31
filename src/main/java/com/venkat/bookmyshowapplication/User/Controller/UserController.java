package com.venkat.bookmyshowapplication.User.Controller;


import com.venkat.bookmyshowapplication.Auth.Controller.AuthController;
import com.venkat.bookmyshowapplication.Common.Exceptions.LoginCredientialsmismatchException;
import com.venkat.bookmyshowapplication.User.Dto.LoginRequestDto;
import com.venkat.bookmyshowapplication.User.Dto.RegisterUserRequestDTO;
import com.venkat.bookmyshowapplication.User.Dto.UserResponseDTO;
import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Service.UserService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/user")
public class UserController {

    private UserService  userService;


    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/Register")
    public ResponseEntity<UserResponseDTO> RegisterUser(@Valid @RequestBody RegisterUserRequestDTO userRequest){

        User user= userService.RegisterUser(userRequest.getName(),userRequest.getEmail(), userRequest.getPassword());

        UserResponseDTO response = new UserResponseDTO();

        response.setEmail(user.getEmail());
        response.setId(user.getId());
        response.setName(user.getName());
        response.setStatus(user.getStatus());

      return   switch (response.getStatus()){
          case ACTIVE -> ResponseEntity.status(HttpStatus.CREATED).body(response);
          case ALREADY_CREATED -> ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response);
             default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };

    }


    @PostMapping("/login")
    public ResponseEntity<String> loginuser(@Valid @RequestBody LoginRequestDto logindto) throws LoginCredientialsmismatchException {

        AuthController authController = new AuthController();
         return authController.loginrequest(logindto.getEmail(), logindto.getPassword());
    }

}
