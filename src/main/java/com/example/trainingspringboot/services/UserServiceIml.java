package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Role;
import com.example.trainingspringboot.entities.User;
import com.example.trainingspringboot.model.request.UserCreatingUpdatingRequest;
import com.example.trainingspringboot.model.response.JwtResponse;
import com.example.trainingspringboot.jwt.JwtUtils;
import com.example.trainingspringboot.model.response.UserResponse;
import com.example.trainingspringboot.repositories.RoleRepository;
import com.example.trainingspringboot.repositories.UserRepository;
import com.example.trainingspringboot.userDetail.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
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
        return repo.findAll().stream().map(user-> new UserResponse(user)).collect(Collectors.toList());
    }

    @Override
    public UserResponse createUser(UserCreatingUpdatingRequest userCreatingRequest)
    {
        try{
            User newUser = new User();
            newUser.setUsername(userCreatingRequest.getUsername());
            if(userCreatingRequest.getRole() != null && !userCreatingRequest.getRole().equals(""))
            {
                newUser.setRole(roleRepo.getRoleByName(userCreatingRequest.getRole()));
            } else {
                newUser.setRole(roleRepo.getRoleByName("STUDENT"));
            }
            newUser.setPassword(passwordEncoder.encode(userCreatingRequest.getPassword()));
            repo.save(newUser);
            return new UserResponse(newUser);
        }
        catch (Exception exc) {
            //throw new ResponseStatusException(HttpStatus.CONFLICT, "User conflict", exc);
            throw exc;
        }
    }

    @Override
    public void deleteUser(Integer id) {
        try{
            repo.deleteById(id);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Delete error", e);
        }
    }

    @Override
    public UserResponse updateUser(UserCreatingUpdatingRequest userUpdatingRequest, Integer id) {
        try{
            User oldUser = repo.getById(id);
            if(userUpdatingRequest.getPassword() != null && !userUpdatingRequest.getPassword().equals(""))
            {
                oldUser.setPassword(passwordEncoder.encode(userUpdatingRequest.getPassword()));
            }
            if(userUpdatingRequest.getUsername() != null && !userUpdatingRequest.getUsername().equals(""))
            {
                oldUser.setUsername(userUpdatingRequest.getUsername());
            }
            oldUser.setUpdatedDate(LocalDate.now(ZoneId.of("GMT+07:00")));
            repo.save(oldUser);
            return new UserResponse(oldUser);
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", exc);
        }
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
