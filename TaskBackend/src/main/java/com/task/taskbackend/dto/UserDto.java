package com.task.taskbackend.dto;


import com.task.taskbackend.Models.UserRole;
import lombok.Data;

@Data
public class UserDto {

  private  Long id;
    private String name;
    private String email;

    private String password;
    private UserRole userRole;
}
