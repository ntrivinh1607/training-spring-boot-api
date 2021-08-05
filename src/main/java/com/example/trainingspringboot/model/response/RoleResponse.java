package com.example.trainingspringboot.model.response;

import com.example.trainingspringboot.entities.Role;
import com.example.trainingspringboot.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private Integer id;
    private String name;
    private List<String> users;
    private List<PermissionResponse> permissions;

    public RoleResponse(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.users = role.getUsers().stream().map(user -> user.getUsername()).collect(Collectors.toList());
        this.permissions = role.getMappedPermission().stream().map(permission -> new PermissionResponse(permission)).collect(Collectors.toList());
    }
}
