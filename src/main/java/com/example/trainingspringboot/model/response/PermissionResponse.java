package com.example.trainingspringboot.model.response;

import com.example.trainingspringboot.entities.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponse {
    private Integer id;
    private String name;

    public PermissionResponse(Permission permission) {
        this.name = permission.getName();
        this.id = permission.getId();
    }
}
