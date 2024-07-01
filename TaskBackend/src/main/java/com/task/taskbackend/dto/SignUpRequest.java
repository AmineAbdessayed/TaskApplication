package com.task.taskbackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignUpRequest {

    private String name;
    private String email;

    private String password;
}
