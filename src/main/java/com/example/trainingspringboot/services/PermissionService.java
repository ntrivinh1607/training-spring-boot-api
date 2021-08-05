package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Permission;
import com.example.trainingspringboot.model.request.PermissionCreatingUpdatingRequest;
import com.example.trainingspringboot.model.response.PermissionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionService {
    List<PermissionResponse> getListPermission();
    PermissionResponse createPermission(PermissionCreatingUpdatingRequest permissionCreatingUpdatingRequest);
    void deletePermission(Integer id);

    PermissionResponse updatePermission(PermissionCreatingUpdatingRequest permissionReq, Integer id);
}
