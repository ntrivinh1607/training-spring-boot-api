package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Permission;
import com.example.trainingspringboot.model.request.PermissionCreatingUpdatingRequest;
import com.example.trainingspringboot.model.response.PermissionResponse;
import com.example.trainingspringboot.model.response.RoleResponse;
import com.example.trainingspringboot.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionServiceIml implements PermissionService {
    @Autowired
    private PermissionRepository repo;

    @Override
    public List<PermissionResponse> getListPermission() {
        try{
            return repo.findAll().stream().map(permission-> new PermissionResponse(permission)).collect(Collectors.toList());
        }catch(Exception exc)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found Permissions", exc);
        }
    }

    @Override
    public PermissionResponse createPermission(PermissionCreatingUpdatingRequest permissionReq) {
        try{
            Permission newPermission = new Permission();
            newPermission.setName(permissionReq.getName());
            repo.save(newPermission);
            return new PermissionResponse(newPermission);
        }
        catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Permission conflict", exc);
        }
    }

    @Override
    public void deletePermission(Integer id) {
        try{
            repo.deleteById(id);
        }
        catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Permission not found", exc);
        }
    }

    @Override
    public PermissionResponse updatePermission(PermissionCreatingUpdatingRequest permissionReq, Integer id) {
        try{
            Permission newPermission = repo.getById(id);
            if(!permissionReq.getName().equals("") && permissionReq.getName()!=null){
                newPermission.setName(permissionReq.getName());
            }
            repo.save(newPermission);
            return new PermissionResponse(newPermission);
        }
        catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Permission not found or map with role", exc);
        }
    }
}
