package com.example.trainingspringboot.repositories;

import com.example.trainingspringboot.entities.Role;
import com.example.trainingspringboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByOrderByIdAsc();
    Optional<User> findByUsername(String username);
    int countAllByRole(Role role);
}
