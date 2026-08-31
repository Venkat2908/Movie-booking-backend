package com.venkat.bookmyshowapplication.User.Controller;


import com.venkat.bookmyshowapplication.Auth.Service.AuthService;
import com.venkat.bookmyshowapplication.User.Dto.RegisterUserRequestDTO;
import com.venkat.bookmyshowapplication.User.Dto.UserResponseDTO;
import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    private UserService  userService;
    private AuthService authService;


    public UserController(UserService userService,AuthService authService){
        this.userService = userService;
        this.authService =  authService;
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
          case PENDING_VERIFICATION -> ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response);
             default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };

    }




}
