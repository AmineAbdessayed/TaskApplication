package com.task.taskbackend.dto;

import com.task.taskbackend.Models.UserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthenticationResponse {

    private  String jwt;
    private  Long userId;
    private UserRole userRole;
}
