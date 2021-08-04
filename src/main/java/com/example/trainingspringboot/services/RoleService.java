package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    List<Role> getListRole();

    Role saveRole(Role role);

    Role updateRole(Role role, Integer id);

    void deleteRole(Integer id);
}
