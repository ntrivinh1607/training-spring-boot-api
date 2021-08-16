package com.example.trainingspringboot.services;

import com.example.trainingspringboot.model.request.RoleCreatingRequest;
import com.example.trainingspringboot.model.request.RoleUpdatingRequest;
import com.example.trainingspringboot.model.response.RoleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    List<RoleResponse> getListRole();

    List<String> getAnonymousListRole ();

    RoleResponse createRole(RoleCreatingRequest roleCreatingRequest);

    RoleResponse updateRole(RoleUpdatingRequest roleUpdatingRequest, Integer id);

    void deleteRole(Integer id);
}
