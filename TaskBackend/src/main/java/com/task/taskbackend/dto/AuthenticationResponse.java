package com.task.taskbackend.dto;

import com.task.taskbackend.Models.UserRole;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {

    private  String jwt;
    private  Long userId;
    private UserRole userRole;

    public AuthenticationResponse(){

    }
}
