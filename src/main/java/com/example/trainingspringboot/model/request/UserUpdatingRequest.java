package com.example.trainingspringboot.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatingRequest {
    private String username;
    private String role;

    @Size(min = 2, max = 20, message = "Password must be between 2 and 20 characters")
    private String password;
}
