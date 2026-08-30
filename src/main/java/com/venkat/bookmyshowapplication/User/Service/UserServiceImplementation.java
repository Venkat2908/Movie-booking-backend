package com.venkat.bookmyshowapplication.User.Service;

import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Model.UserResponseStatus;
import com.venkat.bookmyshowapplication.User.Repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository){
        this.userRepository= userRepository;
    }
    @Override
    public User RegisterUser(String name, String email, String Password) {

      Optional<User> existinguser = userRepository.findByEmail(email);
        User newuser = new User();

        if (existinguser.isPresent()){
            return  existinguser.get();

        }

      if (existinguser.isEmpty()){
          newuser.setName(name);
          newuser.setPassword(Password);
          newuser.setEmail(email);
          newuser.setStatus(UserResponseStatus.USER_CREATED);
          newuser.setVerifed(true);
          newuser.setCreatedAt(new Date());
          newuser.setUpdatedAt(new Date());
          userRepository.save(newuser);
      }
      return newuser;
    }
}
