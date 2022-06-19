package com.example.personaldiarywebapp.service;

import com.example.personaldiarywebapp.domain.AppUser;
import com.example.personaldiarywebapp.domain.Category;
import com.example.personaldiarywebapp.domain.Note;
import com.example.personaldiarywebapp.model.ActionRepose;
import com.example.personaldiarywebapp.model.NoteRequest;
import com.example.personaldiarywebapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    public static final String INVALID_NOTE_ID = "Invalid note id";

    private final NoteRepository noteRepository;
    private final CategoryService categoryService;
    private final AuthService authService;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, CategoryService categoryService, AuthService authService) {
        this.noteRepository = noteRepository;
        this.categoryService = categoryService;
        this.authService = authService;
    }

    @Override
    public List<Note> finaAll() {
        AppUser user = authService.getCurrentLoggedInUser();
        return noteRepository.findAllByUserId(user.getId());
    }

    @Override
    @Transactional
    public Note create(NoteRequest request) {
        AppUser user = authService.getCurrentLoggedInUser();

        Category category = categoryService.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id."));

        if (!category.getAppUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Not a validate category for this user.");
        }

        Note note = getNewNote(request, category, user);
        return noteRepository.save(note);
    }

    @Override
    public Note findById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_NOTE_ID));
        checkRequestValidity(note);
        return note;
    }

    @Override
    public ActionRepose delete(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_NOTE_ID));

        checkRequestValidity(note);
        noteRepository.delete(note);
        return new ActionRepose().result(true)
                .message("Note deleted successfully.");
    }

    @Override
    public Note update(Long id, NoteRequest request) {
        AppUser user = authService.getCurrentLoggedInUser();
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_NOTE_ID));

        checkRequestValidity(note);
        Category category = categoryService.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id."));

        if (!category.getAppUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Invalid category for this user.");
        }

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setCategory(category);
        note.setLastUpdatedTime(new Date());
        return noteRepository.save(note);
    }

    @Override
    public List<Note> searchByQuery(String query, String field) {
        AppUser user = authService.getCurrentLoggedInUser();
        return switch(field) {
            case "title" -> noteRepository.searchAtTitle(query, user.getId());
            case "content" -> noteRepository.searchAtContent(query, user.getId());
            default -> Collections.emptyList();
        };
    }

    @Override
    public List<Note> findNoteByCategory(Long categoryId) {
        checkValidRequestForCategoryFromValidUser(categoryId);
        return noteRepository.findNoteByCategory(categoryId);
    }

    @Override
    public List<Note> searchNotesByKeywordInCategory(Long categoryId, String query) {
        checkValidRequestForCategoryFromValidUser(categoryId);
        return noteRepository.searchNotesByKeywordInCategory(categoryId, query);
    }

    private Note getNewNote(NoteRequest request, Category category, AppUser user) {
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setCategory(category);
        note.setAppUser(user);
        note.setLastUpdatedTime(new Date());
        return note;
    }

    private void checkRequestValidity(Note note) {
        AppUser user = authService.getCurrentLoggedInUser();
        if (!note.getAppUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Invalid User Request");
        }
    }

    private void checkValidRequestForCategoryFromValidUser(Long categoryId) {
        AppUser user = authService.getCurrentLoggedInUser();
        Category category = categoryService.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id."));

        if (!category.getAppUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Invalid category for this user.");
        }
    }
}
