package com.example.ordermanagement.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.ordermanagement.entity.User;
import com.example.ordermanagement.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User register(String username, String password,String role) {

        if (repo.existsByUsername(username)) {
        	throw new ResponseStatusException(
        	        HttpStatus.CONFLICT,
        	        "Username already exists"
        	);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password)); // 🔐 BCrypt
        user.setRole(role);
        return repo.save(user);
    }

    public boolean authenticate(String username, String rawPassword) {
        return repo.findByUsername(username)
                .map(u -> encoder.matches(rawPassword, u.getPassword()))
                .orElse(false);
    }
}
