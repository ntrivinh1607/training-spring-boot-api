package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Permission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionService {
    public List<Permission> getListPermission();
    public Permission savePermission(Permission Permission);
    public void deletePermission(Integer id);

    Permission getPermissionById(Integer id);

    Permission updatePermission(Permission Permission, Integer id);
}
