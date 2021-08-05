package com.example.trainingspringboot.controllers;

import com.example.trainingspringboot.entities.User;
import com.example.trainingspringboot.model.request.UserCreatingUpdatingRequest;
import com.example.trainingspringboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getUser() {
        try{
            return ResponseEntity.ok(userService.getListUser());
        }
        catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> createUser(@RequestBody UserCreatingUpdatingRequest userCreatingRequest) {
        return ResponseEntity.ok(userService.createUser(userCreatingRequest));
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> userLogin(@RequestBody User user) {
        return ResponseEntity.ok(userService.userLogin(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserCreatingUpdatingRequest user, @PathVariable Integer id) {
        return ResponseEntity.ok(userService.updateUser(user, id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Delete Success");
    }
}
