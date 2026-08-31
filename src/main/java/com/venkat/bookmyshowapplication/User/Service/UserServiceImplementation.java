package com.venkat.bookmyshowapplication.User.Service;

import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Model.UserResponseStatus;
import com.venkat.bookmyshowapplication.User.Repository.UserRepository;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository= userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User RegisterUser(String name, String email, String Password) {

      Optional<User> existinguser = userRepository.findByEmail(email);
        User newuser = new User();

        if (existinguser.isPresent()){
            existinguser.get().setStatus(UserResponseStatus.ALREADY_CREATED);

            return  existinguser.get();

        }

      if (existinguser.isEmpty()){
          newuser.setName(name);
          newuser.setPassword(passwordEncoder.encode(Password));
          newuser.setEmail(email);
          newuser.setStatus(UserResponseStatus.ACTIVE);
          newuser.setVerified(false);
          newuser.setCreatedAt(new Date());
          newuser.setUpdatedAt(new Date());
          userRepository.save(newuser);
      }
      return newuser;
    }
}
