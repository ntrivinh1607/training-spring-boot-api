package com.example.trainingspringboot.controllers;

import com.example.trainingspringboot.model.request.RoleCreatingRequest;
import com.example.trainingspringboot.model.request.RoleUpdatingRequest;
import com.example.trainingspringboot.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public ResponseEntity<?> getRole() {
        try{
            return ResponseEntity.ok(roleService.getListRole());
        }
        catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleCreatingRequest roleCreatingRequest) {
        return ResponseEntity.ok(roleService.createRole(roleCreatingRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@Valid @RequestBody RoleUpdatingRequest roleUpdatingRequest, @PathVariable int id) {
        return ResponseEntity.ok(roleService.updateRole(roleUpdatingRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable int id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Delete Success");
    }
}
