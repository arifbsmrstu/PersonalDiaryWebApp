package com.example.personaldiarywebapp.service;

import com.example.personaldiarywebapp.domain.AppUser;
import com.example.personaldiarywebapp.domain.AppUserRole;
import com.example.personaldiarywebapp.exception.InvalidJWTTokenException;
import com.example.personaldiarywebapp.model.AuthenticationResponse;
import com.example.personaldiarywebapp.model.LoginRequest;
import com.example.personaldiarywebapp.model.RegisterRequest;
import com.example.personaldiarywebapp.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

import static com.example.personaldiarywebapp.domain.AppUserRole.ROLE_USER;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        AppUser user = new AppUser();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        try {
            userService.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid request");
        }
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            String token = jwtTokenProvider.createToken(loginRequest.getUsername(), Arrays.asList(ROLE_USER));
            return new AuthenticationResponse(token, loginRequest.getUsername());
        } catch (AuthenticationException e) {
            throw new InvalidJWTTokenException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }

    public AppUser getCurrentLoggedInUser() {
        User loggedInUser = getCurrentUser()
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        return userService.findByUsername(loggedInUser.getUsername());
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
