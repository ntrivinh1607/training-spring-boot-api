package com.example.trainingspringboot.repositories;

import com.example.trainingspringboot.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    @Query("select u from Permission u where u.name = ?1")
    Optional<Permission> findByName(String name);
}