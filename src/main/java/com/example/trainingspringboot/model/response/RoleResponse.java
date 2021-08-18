package com.example.trainingspringboot.model.response;

import com.example.trainingspringboot.entities.Role;
import com.example.trainingspringboot.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private Integer id;
    private String name;
    private List<PermissionResponse> permissions;
    private Date updated_date;
    private Date created_date;

    public RoleResponse(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.permissions = role.getMappedPermission().stream().map(permission -> new PermissionResponse(permission)).collect(Collectors.toList());
        this.updated_date = role.getUpdatedDate();
        this.created_date = role.getCreatedDate();
    }
}
