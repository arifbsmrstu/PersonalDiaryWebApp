package com.example.personaldiarywebapp.repository;

import com.example.personaldiarywebapp.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT category FROM Category category where category.appUser.id=:id")
    List<Category> findAllByUserId(Long id);
}
