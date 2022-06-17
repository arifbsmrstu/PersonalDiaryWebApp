package com.example.personaldiarywebapp.service;

import com.example.personaldiarywebapp.domain.Category;
import com.example.personaldiarywebapp.model.ActionRepose;
import com.example.personaldiarywebapp.model.CategoryRequest;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();

    Category createCategory(CategoryRequest request);

    Optional<Category> findById(Long categoryId);

    ActionRepose delete(Long id);

    Category update(Long id, CategoryRequest request);
}
