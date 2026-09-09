package com.venkat.bookmyshowapplication.User.Service;

import com.venkat.bookmyshowapplication.Common.Exceptions.InvalidCredentialsException;
import com.venkat.bookmyshowapplication.User.Model.Role;
import com.venkat.bookmyshowapplication.User.Model.RoleName;
import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Model.UserResponseStatus;
import com.venkat.bookmyshowapplication.User.Repository.RoleRepository;
import com.venkat.bookmyshowapplication.User.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserServiceImplementation(UserRepository userRepository,PasswordEncoder passwordEncoder,RoleRepository roleRepository){
        this.userRepository= userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
    @Override
    public User RegisterUser(String name, String email, String Password) {

      Optional<User> existinguser = userRepository.findByEmail(email);
        User newuser = new User();

        if (existinguser.isPresent()){

            return  existinguser.get();

        }

        Role userrole =  roleRepository.findByName(RoleName.USER).orElseThrow();

      if (existinguser.isEmpty()){
          newuser.setName(name);
          newuser.setPassword(passwordEncoder.encode(Password));
          newuser.setEmail(email);
          newuser.setStatus(UserResponseStatus.ACTIVE);
          newuser.getRoles().add(userrole);
          newuser.setVerified(false);
          newuser.setCreatedAt(new Date());
          newuser.setUpdatedAt(new Date());
          userRepository.save(newuser);
      }
      return newuser;
    }



    @Override
    public User AccountVerification(String email) throws InvalidCredentialsException {


        Optional<User> existinguser = userRepository.findByEmail(email);

        if (existinguser.isEmpty()){
            throw new InvalidCredentialsException("User Not found");
        }

        User    user = new User();
        user.setVerified(true);
        user.setEmail(existinguser.get().getEmail());
        user.setName(existinguser.get().getName());
        user.setId(existinguser.get().getId());

        if (existinguser.get().getStatus().equals(UserResponseStatus.ACTIVE)){
            user.setStatus(UserResponseStatus.VERIFIED);
        }
        else{
            user.setStatus(UserResponseStatus.PENDING_VERIFICATION);
        }

        return  user;
    }
}
