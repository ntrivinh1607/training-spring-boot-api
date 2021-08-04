package com.example.trainingspringboot.controllers;


import com.example.trainingspringboot.entities.Permission;
import com.example.trainingspringboot.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("")
    public ResponseEntity<?> getPermission() {
        try{
            return ResponseEntity.ok(permissionService.getListPermission());
        }
        catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createPermission(@RequestBody Permission permission) {
        return ResponseEntity.ok(permissionService.savePermission(permission));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePermission(@RequestBody Permission permission, @PathVariable int id) {
        return ResponseEntity.ok(permissionService.updatePermission(permission, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable int id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok("Delete Success");
    }
}