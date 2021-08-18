package com.example.trainingspringboot.repositories;

import com.example.trainingspringboot.entities.Permission;
import com.example.trainingspringboot.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAllByOrderByIdAsc();
    Optional<Role> findByName(String name);
    int countByMappedPermissionContains(Permission permission);
}