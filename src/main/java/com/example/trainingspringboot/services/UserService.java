package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.User;
import com.example.trainingspringboot.model.request.UserCreatingRequest;
import com.example.trainingspringboot.model.request.UserUpdatingRequest;
import com.example.trainingspringboot.model.response.JwtResponse;
import com.example.trainingspringboot.model.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserResponse> getListUser();
    UserResponse createUser(UserCreatingRequest userCreatingRequest);
    void deleteUser(Integer id);

    UserResponse updateUser(UserUpdatingRequest userUpdatingRequest, String currentUser, Integer id);

    JwtResponse userLogin(User user);
}
