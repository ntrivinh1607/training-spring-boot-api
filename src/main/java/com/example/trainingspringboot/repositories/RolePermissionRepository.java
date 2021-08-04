package com.example.trainingspringboot.repositories;

import com.example.trainingspringboot.entities.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM RolePermission WHERE role.id = ?1")
    void deleteByRoleId(Integer roleId);
}