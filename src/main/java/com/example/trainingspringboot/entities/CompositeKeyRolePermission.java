package com.example.trainingspringboot.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CompositeKeyRolePermission implements Serializable {
    Integer roleId;

    Integer permissionId;

    @Override
    public  boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof CompositeKeyRolePermission)) return false;
        CompositeKeyRolePermission compositeKeyRolePermissionId = (CompositeKeyRolePermission) o;
        return Objects.equals(getRoleId(), compositeKeyRolePermissionId.getPermissionId()) &&
                Objects.equals(getPermissionId(), compositeKeyRolePermissionId.getPermissionId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getRoleId(), getPermissionId());
    }
}
