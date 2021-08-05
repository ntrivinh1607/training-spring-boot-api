package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.User;
import com.example.trainingspringboot.model.request.UserCreatingUpdatingRequest;
import com.example.trainingspringboot.model.response.JwtResponse;
import com.example.trainingspringboot.model.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserResponse> getListUser();
    UserResponse createUser(UserCreatingUpdatingRequest userCreatingRequest);
    void deleteUser(Integer id);

    UserResponse updateUser(UserCreatingUpdatingRequest userUpdatingRequest, Integer id);

    JwtResponse userLogin(User user);
}
