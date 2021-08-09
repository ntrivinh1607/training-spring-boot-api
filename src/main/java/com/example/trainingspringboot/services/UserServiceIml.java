package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Role;
import com.example.trainingspringboot.entities.User;
import com.example.trainingspringboot.model.request.UserCreatingRequest;
import com.example.trainingspringboot.model.request.UserUpdatingRequest;
import com.example.trainingspringboot.model.response.JwtResponse;
import com.example.trainingspringboot.jwt.JwtUtils;
import com.example.trainingspringboot.model.response.UserResponse;
import com.example.trainingspringboot.repositories.RoleRepository;
import com.example.trainingspringboot.repositories.UserRepository;
import com.example.trainingspringboot.userDetail.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceIml implements UserService {
    @Autowired
    private UserRepository repo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public List<UserResponse> getListUser() {
        return repo.findAllByOrderByIdAsc().stream().map(user-> new UserResponse(user)).collect(Collectors.toList());
    }

    @Override
    public UserResponse createUser(UserCreatingRequest userCreatingRequest)
    {
        User newUser = new User();
        newUser.setUsername(userCreatingRequest.getUsername());
        if(userCreatingRequest.getRole() != null && !userCreatingRequest.getRole().equals(""))
        {
            newUser.setRole(roleRepo.getRoleByName(userCreatingRequest.getRole()));
        } else {
            newUser.setRole(roleRepo.getById(1));
        }
        newUser.setPassword(passwordEncoder.encode(userCreatingRequest.getPassword()));
        repo.save(newUser);
        return new UserResponse(newUser);
    }

    @Override
    public void deleteUser(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public UserResponse updateUser(UserUpdatingRequest userUpdatingRequest, Integer id) {
        User oldUser = repo.getById(id);
        if(userUpdatingRequest.getPassword() != null && !userUpdatingRequest.getPassword().equals(""))
        {
            oldUser.setPassword(passwordEncoder.encode(userUpdatingRequest.getPassword()));
        }
        if(userUpdatingRequest.getUsername() != null && !userUpdatingRequest.getUsername().equals(""))
        {
            oldUser.setUsername(userUpdatingRequest.getUsername());
        }
        if(userUpdatingRequest.getRole() != null && !userUpdatingRequest.getRole().equals(""))
        {
            oldUser.setRole(roleRepo.getRoleByName(userUpdatingRequest.getRole()));
        }
        oldUser.setUpdatedDate(LocalDate.now(ZoneId.of("GMT+07:00")));
        repo.save(oldUser);
        return new UserResponse(oldUser);
    }

    @Override
    public JwtResponse userLogin(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();
        Role role = userDetails.getRole();

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                role.getName());
    }
}
