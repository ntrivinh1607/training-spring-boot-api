package com.example.trainingspringboot.repositories;

import com.example.trainingspringboot.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    List<Permission> findAllByOrderByIdAsc();
    Optional<Permission> findByName(String name);
    Permission getPermissionByName(String name);
}