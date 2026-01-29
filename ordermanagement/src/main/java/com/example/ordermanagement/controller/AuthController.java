package com.example.ordermanagement.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ordermanagement.dto.LoginRequest;
import com.example.ordermanagement.dto.RegisterRequest;
import com.example.ordermanagement.entity.User;
import com.example.ordermanagement.service.UserService;
import com.example.ordermanagement.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {

        User user = userService.register(
                req.getUsername(),
                req.getPassword(),
                req.getRole()
        );

        return ResponseEntity.ok("User registered with role " + user.getRole());
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        boolean valid = userService.authenticate(
                req.getUsername(),
                req.getPassword()
        );

        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(req.getUsername());

        return ResponseEntity.ok(
                Map.of("token", token)
        );
    }

}
