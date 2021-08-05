package com.example.trainingspringboot.repositories;

import com.example.trainingspringboot.entities.Permission;
import com.example.trainingspringboot.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
    Role getRoleByName(String name);
//    @Query("Select a from Role a left join RolePermission b on a.id=b.role.id where b.permission.id = ?1")
//    List<Role> getListRoleByPermissionId(Integer permissionId);
//
//    @Query("Select a from Permission a left join RolePermission b on a.id=b.permission.id where b.role.id = ?1")
//    List<Permission> getListPermissionByRoleId(Integer roleId);
}