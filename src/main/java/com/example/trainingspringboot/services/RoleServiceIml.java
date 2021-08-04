package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Permission;
import com.example.trainingspringboot.entities.Role;
import com.example.trainingspringboot.entities.RolePermission;
import com.example.trainingspringboot.entities.User;
import com.example.trainingspringboot.repositories.PermissionRepository;
import com.example.trainingspringboot.repositories.RolePermissionRepository;
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
    @Autowired
    private RolePermissionRepository ropeRepo;
    @Override
    public List<Role> getListRole() {
        try{
            return repo.findAll();
        }catch(Exception exc)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found roles", exc);
        }
    }

    @Override
    public Role saveRole(Role role) {
        try{
            Role newRole = new Role();
            newRole.setName(role.getName());
            newRole.getRolePermissions().addAll((role.getRolePermissions()
                    .stream()
                    .map(rolePermission -> {
                        Permission per = perRepo.findById(rolePermission.getPermission().getId()).get();
                        RolePermission newRoPe = new RolePermission();
                        newRoPe.setPermission(per);
                        newRoPe.setRole(newRole);
                        return newRoPe;
                    })
                    .collect(Collectors.toList())
            ));
            return repo.save(newRole);
        }
        catch (NullPointerException exc) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "onflict Role name", exc);
        }
    }

    @Override
    public Role updateRole(Role role, Integer id) {
        try{
            Role oldRole = repo.findById(id).get();
            oldRole.setName(role.getName());
            if(role.getRolePermissions().size() != 0)   ropeRepo.deleteByRoleId(id);
            oldRole.setRolePermissions(role.getRolePermissions().stream().map(rolePermission -> {
                Permission per = perRepo.findById(rolePermission.getPermission().getId()).get();
                RolePermission newRoPe = new RolePermission();
                newRoPe.setPermission(per);
                newRoPe.setRole(oldRole);
                return newRoPe;
            }).collect(Collectors.toList()));
            return repo.saveAndFlush(oldRole);
        }
        catch (Exception exc) {
            // throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found", exc);
            throw exc;
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
