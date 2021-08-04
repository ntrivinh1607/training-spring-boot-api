package com.example.trainingspringboot.jwt;

import com.example.trainingspringboot.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String username;
    private Integer roleId;

    public JwtResponse(String accessToken, Integer id, String username, Integer roleId) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.roleId = roleId;
    }
}
