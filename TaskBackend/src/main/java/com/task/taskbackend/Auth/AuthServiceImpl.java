package com.task.taskbackend.Auth;


import com.task.taskbackend.Models.User;
import com.task.taskbackend.Models.UserRole;
import com.task.taskbackend.Repositories.UserRepository;
import com.task.taskbackend.dto.SignUpRequest;
import com.task.taskbackend.dto.UserDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements  AuthService{

    private  final UserRepository userRepository;


    @PostConstruct
    public void createAdminAccount(){

      Optional<User> Opuser= userRepository.findByUserRole(UserRole.ADMIN);

        if(Opuser.isEmpty()){

            User user= new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);

            System.out.println("Admin account Created Succefully!!");

        } else {
            System.out.println("Admin account already exists!!");
        }


    }

    @Override
    public UserDto SignUpUser(SignUpRequest signUpRequest) {
        User user= new User();

        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        user.setUserRole(UserRole.EMPLOYEE);
       User CreatedUser= userRepository.save(user);

        return CreatedUser.getUserDto();
    }

    public  Boolean hasUserWithEmail(String Email){
        return  userRepository.findFirstByEmail(Email).isPresent();
    }
}

