package com.example.personaldiarywebapp.controller;

import com.example.personaldiarywebapp.model.ActionRepose;
import com.example.personaldiarywebapp.model.AuthenticationResponse;
import com.example.personaldiarywebapp.model.LoginRequest;
import com.example.personaldiarywebapp.model.RegisterRequest;
import com.example.personaldiarywebapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ActionRepose> signUp(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>(new ActionRepose().result(true).message("Registered Successfully"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}