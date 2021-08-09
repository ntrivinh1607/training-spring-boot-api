package com.example.trainingspringboot.repositories;

import com.example.trainingspringboot.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    List<Permission> findAllByOrderByIdAsc();
}