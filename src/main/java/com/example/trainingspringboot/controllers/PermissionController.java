package com.example.trainingspringboot.controllers;

import com.example.trainingspringboot.model.request.PermissionCreatingUpdatingRequest;
import com.example.trainingspringboot.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("")
    public ResponseEntity<?> getPermission() {
        return ResponseEntity.ok(permissionService.getListPermission());
    }

    @PostMapping("")
    public ResponseEntity<?> createPermission(@Valid @RequestBody PermissionCreatingUpdatingRequest permission) {
        return ResponseEntity.ok(permissionService.createPermission(permission));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePermission(@Valid @RequestBody PermissionCreatingUpdatingRequest permissionReq, @PathVariable Integer id) {
        return ResponseEntity.ok(permissionService.updatePermission(permissionReq, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable Integer id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok("Delete Success");
    }
}