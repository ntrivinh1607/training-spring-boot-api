package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.User;
import com.example.trainingspringboot.jwt.JwtResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<User> getListUser();
    public User createUser(User user);
    public void deleteUser(Integer id);

    User getUserById(int id);

    User updateUser(User user, int id);

    JwtResponse userLogin(User user);
}
