package com.example.personaldiarywebapp.service;

import com.example.personaldiarywebapp.domain.AppUser;
import com.example.personaldiarywebapp.domain.Category;
import com.example.personaldiarywebapp.domain.Note;
import com.example.personaldiarywebapp.model.ActionRepose;
import com.example.personaldiarywebapp.model.CategoryRequest;
import com.example.personaldiarywebapp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final AuthService authService;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, AuthService authService) {
        this.categoryRepository = categoryRepository;
        this.authService = authService;
    }

    @Override
    public List<Category> findAll() {
        AppUser user = authService.getCurrentLoggedInUser();
        return categoryRepository.findAllByUserId(user.getId());
    }

    @Override
    @Transactional
    public Category createCategory(CategoryRequest request) {
        AppUser user = authService.getCurrentLoggedInUser();
        Category category = new Category();
        category.setName(request.getName());
        category.setAppUser(user);
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public ActionRepose delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id"));

        AppUser user = authService.getCurrentLoggedInUser();
        if (!category.getAppUser().equals(user)) {
            throw new IllegalArgumentException("Invalid request from user.");
        }

        categoryRepository.delete(category);
        return new ActionRepose().result(true)
                .message("Category deleted successfully.");
    }

    @Override
    public Category update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id"));
        category.setName(request.getName());
        return categoryRepository.save(category);
    }
}
