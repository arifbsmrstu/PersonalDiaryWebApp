package com.example.personaldiarywebapp.repository;

import com.example.personaldiarywebapp.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUserName(String username);
}
