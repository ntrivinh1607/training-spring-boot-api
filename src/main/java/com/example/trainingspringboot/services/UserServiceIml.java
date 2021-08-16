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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;
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
        return repo.findAllByOrderByIdAsc().stream().map(user-> new UserResponse(user)).collect(Collectors.toList());
    }

    @Override
    public UserResponse createUser(UserCreatingRequest userCreatingRequest)
    {
        if(repo.findByUsername(userCreatingRequest.getUsername()).isPresent()){
            throw new DataIntegrityViolationException("Duplicate username");
        }
        User newUser = new User();
        newUser.setUsername(userCreatingRequest.getUsername());
        if(userCreatingRequest.getRole() != null && !userCreatingRequest.getRole().equals(""))
        {
            Optional<Role> roleFindFromRequest = roleRepo.findByName(userCreatingRequest.getRole());
            if(roleFindFromRequest.isPresent()){
                Role newUserRole = roleFindFromRequest.get();
                newUser.setRole(newUserRole);
            } else {
                throw new NoSuchElementException("Not found role");
            }
        } else {
            throw new IllegalArgumentException("Invalid request");
        }
        newUser.setPassword(passwordEncoder.encode(userCreatingRequest.getPassword()));
        repo.save(newUser);
        return new UserResponse(newUser);
    }

    @Override
    public void deleteUser(Integer id, String currentUser) {
        Optional<User> userGetById = repo.findById(id);
        if(userGetById.isPresent()){
            if(userGetById.get().getUsername().equals(currentUser))
            {
                throw new IllegalArgumentException("Cannot delete current signined user");
            }
            repo.deleteById(id);
        } else {
            throw new NoSuchElementException("Not found user");
        }
    }

    @Override
    public UserResponse updateUser(UserUpdatingRequest userUpdatingRequest, String currentUser, Integer id) {
        User oldUser = repo.getById(id);
        Optional<User> userFindByRequest = repo.findByUsername(userUpdatingRequest.getUsername());
        // check if update current signined user
        if(oldUser.getUsername().equals(currentUser)){
            throw new IllegalArgumentException("Can't update current user");
        }

        if(userFindByRequest.isPresent() && (userFindByRequest.get() != oldUser)){
            throw new DataIntegrityViolationException("Duplicate username");
        }
        oldUser.setUsername(userUpdatingRequest.getUsername());
        if(userUpdatingRequest.getPassword() != null && !userUpdatingRequest.getPassword().equals(""))
        {
            oldUser.setPassword(passwordEncoder.encode(userUpdatingRequest.getPassword()));
        }
        if(userUpdatingRequest.getRole() != null && !userUpdatingRequest.getRole().equals(""))
        {
            Optional<Role> roleFindByUserRequest = roleRepo.findByName(userUpdatingRequest.getRole());
            if(roleFindByUserRequest.isPresent()){
                oldUser.setRole(roleFindByUserRequest.get());
            }
            else{
                throw new NoSuchElementException("Not found role");
            }
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
