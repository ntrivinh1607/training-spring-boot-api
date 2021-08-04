package com.example.trainingspringboot.controllers;

import com.example.trainingspringboot.entities.Role;
import com.example.trainingspringboot.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/role")
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
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        return ResponseEntity.ok(roleService.saveRole(role));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@RequestBody Role role, @PathVariable int id) {
        return ResponseEntity.ok(roleService.updateRole(role, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable int id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Delete Success");
    }
}
