package com.task.taskbackend.Auth;

import com.task.taskbackend.dto.SignUpRequest;
import com.task.taskbackend.dto.UserDto;

public interface AuthService {

      UserDto SignUpUser(SignUpRequest signUpRequest);
      Boolean hasUserWithEmail(String Email);
