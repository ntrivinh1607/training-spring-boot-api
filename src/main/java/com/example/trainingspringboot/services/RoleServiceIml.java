package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Permission;
import com.example.trainingspringboot.entities.Role;
import com.example.trainingspringboot.model.request.RoleCreatingRequest;
import com.example.trainingspringboot.model.request.RoleUpdatingRequest;
import com.example.trainingspringboot.model.response.RoleResponse;
import com.example.trainingspringboot.repositories.PermissionRepository;
import com.example.trainingspringboot.repositories.RoleRepository;
import com.example.trainingspringboot.repositories.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RoleServiceIml implements RoleService {
    @Autowired
    private RoleRepository repo;
    @Autowired
    private PermissionRepository perRepo;
    @Autowired
    private UserRepository userRepo;

    @Override
    public List<RoleResponse> getListRole() {
        return repo.findAllByOrderByIdAsc().stream().map(role -> new RoleResponse(role)).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllName() {
        return repo.findAllByOrderByIdAsc().stream().map(role -> role.getName()).collect(Collectors.toList());
    }

    @Override
    public RoleResponse createRole(RoleCreatingRequest roleCreatingRequest) {
        Role newRole = new Role();
        if (repo.findByName(roleCreatingRequest.getName()).isPresent()) {
            throw new DataIntegrityViolationException("Duplicate name");
        }
        newRole.setName(roleCreatingRequest.getName());
        if (roleCreatingRequest.getPermissions() != null) {
            newRole.setMappedPermission(roleCreatingRequest.getPermissions().stream().map(permission -> {
                Optional<Permission> permissionFindFromRequest = perRepo.findById(permission);
                if (permissionFindFromRequest.isPresent()) {
                    return permissionFindFromRequest.get();
                } else {
                    throw new NoSuchElementException("Not found permission");
                }
            }).collect(Collectors.toList()));
        }
        repo.save(newRole);
        return new RoleResponse(newRole);
    }

    @Override
    public RoleResponse updateRole(RoleUpdatingRequest roleUpdatingRequest, Integer id) {
        Optional<Role> roleFindById = repo.findById(id);
        if (!roleFindById.isPresent()) {
            throw new NoSuchElementException("Not found role");
        }
        Role newRole = roleFindById.get();

        if (StringUtils.isNotBlank(roleUpdatingRequest.getName())) {
            Optional<Role> roleFindFromRequest = repo.findByName(roleUpdatingRequest.getName());
            if (roleFindFromRequest.isPresent() && (!roleFindFromRequest.get().getName().equals(newRole.getName()))) {
                throw new DataIntegrityViolationException("Duplicate role name");
            }
            newRole.setName(roleUpdatingRequest.getName());
        }
        if (roleUpdatingRequest.getPermissions() != null) {
            newRole.setMappedPermission(roleUpdatingRequest.getPermissions().stream().map(permission -> {
                Optional<Permission> permissionFindFromRequest = perRepo.findById(permission);
                if (permissionFindFromRequest.isPresent()) {
                    return permissionFindFromRequest.get();
                } else {
                    throw new NoSuchElementException("Not found permission");
                }
            }).collect(Collectors.toList()));
        }
        repo.save(newRole);
        return new RoleResponse(newRole);
    }

    @Override
    public void deleteRole(Integer id) {
        Optional<Role> roleFindById = repo.findById(id);
        if (roleFindById.isPresent()) {
            if (userRepo.countAllByRole(roleFindById.get()) != 0) {
                throw new IllegalArgumentException("Some user have this role");
            }
            repo.deleteById(id);
        } else {
            throw new NoSuchElementException("Not found role");
        }
    }
}
