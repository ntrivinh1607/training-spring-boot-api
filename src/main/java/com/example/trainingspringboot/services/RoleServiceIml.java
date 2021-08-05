package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Permission;
import com.example.trainingspringboot.entities.Role;
import com.example.trainingspringboot.model.request.RoleCreatingUpdatingRequest;
import com.example.trainingspringboot.model.response.RoleResponse;
import com.example.trainingspringboot.model.response.UserResponse;
import com.example.trainingspringboot.repositories.PermissionRepository;
import com.example.trainingspringboot.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleServiceIml implements RoleService {
    @Autowired
    private RoleRepository repo;
    @Autowired
    private PermissionRepository perRepo;

    @Override
    public List<RoleResponse> getListRole() {
        try{
            return repo.findAll().stream().map(role-> new RoleResponse(role)).collect(Collectors.toList());
        }catch(Exception exc)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found roles", exc);
        }
    }

    @Override
    public RoleResponse createRole(RoleCreatingUpdatingRequest roleCreatingUpdatingRequest) {
        try{
            Role newRole = new Role();
            newRole.setName(roleCreatingUpdatingRequest.getName());
            newRole.setMappedPermission(roleCreatingUpdatingRequest.getPermissions().stream().map(permission ->{
                return perRepo.findById(permission).get();
            }).collect(Collectors.toList()));
            repo.save(newRole);
            return new RoleResponse(newRole);
        }
        catch (NullPointerException exc) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflict Role name", exc);
        }
    }

    @Override
    public RoleResponse updateRole(RoleCreatingUpdatingRequest roleCreatingUpdatingRequest, Integer id) {
        try{
            Role newRole = repo.getById(id);

            if(roleCreatingUpdatingRequest.getName() != null && !roleCreatingUpdatingRequest.getName().equals(""))
            {
                newRole.setName(roleCreatingUpdatingRequest.getName());
            }
            if(roleCreatingUpdatingRequest.getPermissions() != null)
            {
                newRole.setMappedPermission(roleCreatingUpdatingRequest.getPermissions().stream().map(permission ->
                        perRepo.findById(permission).get()).collect(Collectors.toList()));
            }
            repo.save(newRole);
            return new RoleResponse(newRole);
        }
        catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found", exc);
        }
    }

    @Override
    public void deleteRole(Integer id) {
        try{
            repo.deleteById(id);
        }
        catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found", exc);
        }
    }
}
