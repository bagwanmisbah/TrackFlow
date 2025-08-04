package com.misbah.issue_tracker.controller;

import com.misbah.issue_tracker.model.User;
import com.misbah.issue_tracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController
{

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user)
    {
        authService.register(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest)
    {
        String token= authService.login(authRequest.getUsername(),authRequest.getPassword());
        return ResponseEntity.ok(Map.of("token",token));
    }
}
