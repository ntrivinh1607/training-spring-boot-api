package com.example.trainingspringboot.userDetail;

import com.example.trainingspringboot.entities.User;
import com.example.trainingspringboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user;
        Optional<User> userFindByName = userRepository.findByUsername(username);
        if (userFindByName.isPresent()) {
            user = userFindByName.get();
        } else {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
}
