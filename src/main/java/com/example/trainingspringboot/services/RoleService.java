package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Role;
import com.example.trainingspringboot.model.request.RoleCreatingUpdatingRequest;
import com.example.trainingspringboot.model.response.RoleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    List<RoleResponse> getListRole();

    RoleResponse createRole(RoleCreatingUpdatingRequest roleCreatingUpdatingRequest);

    RoleResponse updateRole(RoleCreatingUpdatingRequest roleCreatingUpdatingRequest, Integer id);

    void deleteRole(Integer id);
}
