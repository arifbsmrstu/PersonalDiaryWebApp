package com.example.personaldiarywebapp.service;

import com.example.personaldiarywebapp.domain.AppUser;

public interface UserService {
    void save(AppUser user);
    AppUser findByUsername(String username);
}
