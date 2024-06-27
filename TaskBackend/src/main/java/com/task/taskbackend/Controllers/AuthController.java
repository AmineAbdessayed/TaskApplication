package com.task.taskbackend.Controllers;


import com.task.taskbackend.Auth.AuthService;
import com.task.taskbackend.Models.User;
import com.task.taskbackend.Repositories.UserRepository;
import com.task.taskbackend.Services.UserService;
import com.task.taskbackend.dto.AuthenticationRequest;
import com.task.taskbackend.dto.AuthenticationResponse;
import com.task.taskbackend.dto.SignUpRequest;
import com.task.taskbackend.dto.UserDto;
import com.task.taskbackend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private  final AuthService authService;
    private JwtUtil jwtUtil;
    private  final UserRepository userRepository;
    private  final AuthenticationManager authenticationManager;
    private  final UserService userService;



    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignUpRequest signUpRequest) {

        if (authService.hasUserWithEmail(signUpRequest.getEmail()))

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists with this email");

        UserDto createdUser = authService.SignUpUser(signUpRequest);

        if (createdUser == null){


            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USER NOT CREATED"); }

            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);



    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest){

        try {

         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));


        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        }
        final UserDetails userDetails=userService.userDetailsservice().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser=userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwtToken=jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse= new AuthenticationResponse();
        if(optionalUser.isPresent()){

            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());

        }

        return  authenticationResponse;



    }


}
