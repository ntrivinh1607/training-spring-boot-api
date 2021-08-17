package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Permission;
import com.example.trainingspringboot.model.request.PermissionCreatingUpdatingRequest;
import com.example.trainingspringboot.model.response.PermissionResponse;
import com.example.trainingspringboot.repositories.PermissionRepository;
import com.example.trainingspringboot.repositories.RoleRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PermissionServiceIml implements PermissionService {
    @Autowired
    private PermissionRepository repo;
    @Autowired
    private RoleRepository roleRepo;

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
        Optional<Permission> permissionFindById = repo.findById(id);
        if(permissionFindById.isPresent()){
            if(roleRepo.findAllByMappedPermissionContains(permissionFindById.get()).size() == 0){
                repo.deleteById(id);
            }else{
                throw new IllegalArgumentException("Permission in relationship with some role");
            }
        }else{
            throw new NoSuchElementException("Not found permission");
        }
    }

    @Override
    public PermissionResponse updatePermission(PermissionCreatingUpdatingRequest permissionReq, Integer id) {
        Optional<Permission> permissionFindById = repo.findById(id);
        if(!permissionFindById.isPresent()){
            throw new NoSuchElementException("Not found object");
        }
        Permission newPermission = permissionFindById.get();
        if(!permissionReq.getName().equals("") && permissionReq.getName()!=null){
            Optional<Permission> permissionFindByRequest = repo.findByName(permissionReq.getName());
            if(permissionFindByRequest.isPresent() &&
                    (!permissionFindByRequest.get().getName().equals(newPermission.getName())))
            {
                throw new DataIntegrityViolationException("Duplicate name");
            }
            newPermission.setName(permissionReq.getName());
        }
        repo.save(newPermission);
        return new PermissionResponse(newPermission);
    }
}
