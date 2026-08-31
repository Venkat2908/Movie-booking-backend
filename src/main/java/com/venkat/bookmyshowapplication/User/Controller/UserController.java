package com.venkat.bookmyshowapplication.User.Controller;


import com.venkat.bookmyshowapplication.User.Dto.RegisterUserRequestDTO;
import com.venkat.bookmyshowapplication.User.Dto.UserResponseDTO;
import com.venkat.bookmyshowapplication.User.Model.RegisterationResult;
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



     if (user.isVerified()) {
         response.setRegisterationResult(RegisterationResult.ALREADY_EXISTS);
         return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response);
     }else {

         response.setRegisterationResult(RegisterationResult.CREATED);
            return    ResponseEntity.status(HttpStatus.CREATED).body(response);
          }



    }




}
