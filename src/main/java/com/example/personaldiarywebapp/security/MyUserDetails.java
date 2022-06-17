package com.example.personaldiarywebapp.security;

import com.example.personaldiarywebapp.domain.AppUser;
import com.example.personaldiarywebapp.repository.UserRepository;
import com.example.personaldiarywebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.example.personaldiarywebapp.domain.AppUserRole.ROLE_USER;

@Service
public class MyUserDetails implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public MyUserDetails( UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AppUser appUser = userService.findByUsername(username);

        if (appUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(appUser.getPassword())
                .authorities(Arrays.asList(ROLE_USER))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}