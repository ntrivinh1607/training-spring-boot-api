package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Permission;
import com.example.trainingspringboot.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class PermissionServiceIml implements PermissionService {
    @Autowired
    private PermissionRepository repo;

    @Override
    public List<Permission> getListPermission() {
        try{
            return repo.findAll();
        }catch(Exception exc)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found Permissions", exc);
        }
    }

    @Override
    public Permission savePermission(Permission permission) {
        try{
            return repo.save(permission);
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
    public Permission getPermissionById(Integer id) {
        try{
            return repo.getById(id);
        }
        catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Permission not found", exc);
        }
    }

    @Override
    public Permission updatePermission(Permission permission, Integer id) {
        try{
            Permission newPms = new Permission();
            newPms.setId(id);
            return repo.save(permission);
        }
        catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Permission not found", exc);
        }
    }
}
