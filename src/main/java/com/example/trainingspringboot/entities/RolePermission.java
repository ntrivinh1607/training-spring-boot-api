package com.example.trainingspringboot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RolePermission {
    @EmbeddedId
    private CompositeKeyRolePermission id = new CompositeKeyRolePermission();

    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id", referencedColumnName = "id")
    private Permission permission;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @JsonIgnore
    private Role role;
}
