package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.Role;
import com.example.trainingspringboot.entities.User;
import com.example.trainingspringboot.jwt.JwtResponse;
import com.example.trainingspringboot.jwt.JwtUtils;
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
    public List<User> getListUser() {
        return repo.findAll();
    }

    @Override
    public User createUser(User user) {
        try{
            System.out.println(user.getRole());
            Role role = roleRepo.findById(user.getRole().getId()).get();
            user.setRole(role);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return repo.save(user);
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
    public User getUserById(int id) {
        return repo.getById(id);
    }

    @Override
    public User updateUser(User user, int id) {
        try{
            Optional<User> userById = repo.findById(id);
            if(userById.isPresent()){
                User oldUser = getUserById(id);
                user.setId(id);
                if(user.getPassword() == null)
                {
                    user.setPassword(oldUser.getPassword());
                } else {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                }
                user.setUpdatedDate(LocalDate.now(ZoneId.of("GMT+07:00")));
                return repo.save(user);
            }
            throw new Exception();
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
                role.getId());
    }
}
