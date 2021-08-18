package com.example.trainingspringboot.controllers;

import com.example.trainingspringboot.entities.User;
import com.example.trainingspringboot.model.request.MessageRequest;
import com.example.trainingspringboot.model.request.UserCreatingRequest;
import com.example.trainingspringboot.model.request.UserUpdatingRequest;
import com.example.trainingspringboot.services.RabbitMQSender;
import com.example.trainingspringboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    RabbitMQSender rabbitMQSender;

    @GetMapping("/users")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok(userService.getListUser());
    }


    @PostMapping("/auth/signup")
    public ResponseEntity<?> signupUser(@Valid @RequestBody UserCreatingRequest userCreatingRequest) {
        rabbitMQSender.sendMessageForSignup(userCreatingRequest);
        return ResponseEntity.ok(userService.createUser(userCreatingRequest));
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreatingRequest userCreatingRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        rabbitMQSender.sendMessageForCreateUser(userCreatingRequest, userDetails);
        return ResponseEntity.ok(userService.createUser(userCreatingRequest));
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> userLogin(@RequestBody User user) {
        return ResponseEntity.ok(userService.userLogin(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdatingRequest user, @PathVariable Integer id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        rabbitMQSender.sendMessageForUpdateUser(user, userDetails, id);

        String currentUser = userDetails.getUsername();
        return ResponseEntity.ok(userService.updateUser(user, currentUser,id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        rabbitMQSender.sendMessageForDeleteUser(userDetails, id);

        String currentUser = userDetails.getUsername();
        userService.deleteUser(id, currentUser);
        return ResponseEntity.ok("Delete Success");
    }
}
