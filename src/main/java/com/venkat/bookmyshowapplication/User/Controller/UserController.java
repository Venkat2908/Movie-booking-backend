package com.venkat.bookmyshowapplication.User.Controller;


import com.venkat.bookmyshowapplication.User.Dto.RegisterUserRequestDTO;
import com.venkat.bookmyshowapplication.User.Dto.UserResponseDTO;
import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Service.UserService;
import com.venkat.bookmyshowapplication.User.Service.UserServiceImplementation;
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

        System.out.println("REGISTER ENDPOINT HIT");
        User user= userService.RegisterUser(userRequest.getName(),userRequest.getEmail(), userRequest.getPassword());

        UserResponseDTO response = new UserResponseDTO();

        response.setEmail(user.getEmail());
        response.setId(user.getId());
        response.setName(user.getName());
        response.setStatus(user.getStatus());

      return   switch (response.getStatus()){
            case USER_CREATED -> ResponseEntity.status(HttpStatus.CREATED).body(response);
             default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };

    }
}
