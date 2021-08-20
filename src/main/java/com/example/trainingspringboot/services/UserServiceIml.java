package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Role;
import com.example.trainingspringboot.entities.User;
import com.example.trainingspringboot.jwt.JwtUtils;
import com.example.trainingspringboot.model.request.UserCreatingRequest;
import com.example.trainingspringboot.model.request.UserUpdatingRequest;
import com.example.trainingspringboot.model.response.JwtResponse;
import com.example.trainingspringboot.model.response.UserResponse;
import com.example.trainingspringboot.repositories.RoleRepository;
import com.example.trainingspringboot.repositories.UserRepository;
import com.example.trainingspringboot.userDetail.MyUserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserServiceIml implements UserService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private UserRepository repo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Value("${rabbitmq.message.content.update.user}")
    private String contentUpdateUser;

    @Value("${rabbitmq.message.content.signup.user}")
    private String contentSignupUser;

    @Value("${rabbitmq.message.content.create.user}")
    private String contentCreateUser;

    @Value("${rabbitmq.message.content.delete.user}")
    private String contentDeleteUser;

    @Override
    public List<UserResponse> getListUser() {
        return repo.findAllByOrderByIdAsc().stream().map(user -> new UserResponse(user)).collect(Collectors.toList());
    }

    @Override
    public UserResponse createUser(UserCreatingRequest userCreatingRequest, String currentUser) {
        if (repo.findByUsername(userCreatingRequest.getUsername()).isPresent()) {
            throw new DataIntegrityViolationException("Duplicate username");
        }
        User newUser = new User();
        newUser.setUsername(userCreatingRequest.getUsername());
        Optional<Role> roleFindFromRequest = roleRepo.findByName(userCreatingRequest.getRole());
        if (roleFindFromRequest.isPresent()) {
            Role newUserRole = roleFindFromRequest.get();
            newUser.setRole(newUserRole);
        } else {
            throw new NoSuchElementException("Not found role");
        }
        newUser.setPassword(passwordEncoder.encode(userCreatingRequest.getPassword()));
        repo.save(newUser);

        if (StringUtils.isNotBlank(currentUser)) {
            rabbitMQSender.sendMessage(contentCreateUser, newUser, currentUser);
        } else {
            rabbitMQSender.sendMessage(contentSignupUser, newUser, newUser.getUsername());
        }
        return new UserResponse(newUser);
    }

    @Override
    public void deleteUser(Integer id, String currentUser) {
        Optional<User> userFindById = repo.findById(id);
        if (userFindById.isPresent()) {
            if (userFindById.get().getUsername().equals(currentUser)) {
                throw new IllegalArgumentException("Cannot delete current signined user");
            }
            User userGetById = userFindById.get();

            rabbitMQSender.sendMessage(contentDeleteUser, userGetById, currentUser);
            repo.deleteById(id);
        } else {
            throw new NoSuchElementException("Not found user");
        }
    }

    @Override
    public UserResponse updateUser(UserUpdatingRequest userUpdatingRequest, String currentUser, Integer id) {
        Optional<User> userFindById = repo.findById(id);
        if (!userFindById.isPresent()) {
            throw new NoSuchElementException("Not found user");
        }
        User oldUser = userFindById.get();

        Optional<User> userFindByRequest = repo.findByUsername(userUpdatingRequest.getUsername());
        // check if update current signined user
        if (oldUser.getUsername().equals(currentUser)) {
            throw new IllegalArgumentException("Can't update current user");
        }

        if (userFindByRequest.isPresent() && (!userFindByRequest.get().getUsername().equals(oldUser.getUsername()))) {
            throw new DataIntegrityViolationException("Duplicate username");
        }
        oldUser.setUsername(userUpdatingRequest.getUsername());

        if (StringUtils.isNotBlank(userUpdatingRequest.getPassword())) {
            oldUser.setPassword(passwordEncoder.encode(userUpdatingRequest.getPassword()));
        }
        if (StringUtils.isNotBlank(userUpdatingRequest.getRole())) {
            Optional<Role> roleFindByUserRequest = roleRepo.findByName(userUpdatingRequest.getRole());
            if (roleFindByUserRequest.isPresent()) {
                oldUser.setRole(roleFindByUserRequest.get());
            } else {
                throw new NoSuchElementException("Not found role");
            }
        }
        repo.save(oldUser);

        rabbitMQSender.sendMessage(contentUpdateUser, oldUser, currentUser);
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
