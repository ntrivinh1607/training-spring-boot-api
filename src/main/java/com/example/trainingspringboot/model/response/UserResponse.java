package com.example.trainingspringboot.model.response;

import com.example.trainingspringboot.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer id;
    private String username;
    private String role;

    public UserResponse(User oldUser) {
        this.id = oldUser.getId();
        this.username = oldUser.getUsername();
        this.role = oldUser.getRole().getName();
    }
}
