package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Permission;
import com.example.trainingspringboot.model.request.PermissionCreatingUpdatingRequest;
import com.example.trainingspringboot.model.response.PermissionResponse;
import com.example.trainingspringboot.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionServiceIml implements PermissionService {
    @Autowired
    private PermissionRepository repo;

    @Override
    public List<PermissionResponse> getListPermission() {
        return repo.findAllByOrderByIdAsc().stream().map(permission-> new PermissionResponse(permission)).collect(Collectors.toList());
    }

    @Override
    public PermissionResponse createPermission(PermissionCreatingUpdatingRequest permissionReq) {
        Permission newPermission = new Permission();
        if(repo.findByName(permissionReq.getName()).isPresent())
        {
            throw new DataIntegrityViolationException("Duplicate name");
        }
        newPermission.setName(permissionReq.getName());
        repo.save(newPermission);
        return new PermissionResponse(newPermission);
    }

    @Override
    public void deletePermission(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public PermissionResponse updatePermission(PermissionCreatingUpdatingRequest permissionReq, Integer id) {
        Permission newPermission = repo.getById(id);
        if(!permissionReq.getName().equals("") && permissionReq.getName()!=null){
            if(repo.findByName(permissionReq.getName()).isPresent())
            {
                throw new DataIntegrityViolationException("Duplicate name");
            }
            newPermission.setName(permissionReq.getName());
        }
        repo.save(newPermission);
        return new PermissionResponse(newPermission);
    }
}
