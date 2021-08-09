package com.example.trainingspringboot.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatingRequest {
    @NotNull(message = "Username is required")
    @NotEmpty(message = "Username is required")
    private String username;

    private String role;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    @Size(min = 2, max = 20, message = "Password must be between 2 and 20 characters")
    private String password;
}
