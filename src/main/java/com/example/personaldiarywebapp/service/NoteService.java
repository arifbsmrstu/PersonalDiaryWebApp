package com.example.personaldiarywebapp.service;

import com.example.personaldiarywebapp.domain.Note;
import com.example.personaldiarywebapp.model.ActionRepose;
import com.example.personaldiarywebapp.model.NoteRequest;

import java.util.List;

public interface NoteService {

    List<Note> finaAll();

    Note create(NoteRequest request);

    Note findById(Long id);

    ActionRepose delete(Long id);

    Note update(Long id, NoteRequest request);

    List<Note> searchByQuery(String query, String field);

    List<Note> findNoteByCategory(Long categoryId);

    List<Note> searchNotesByKeywordInCategory(Long categoryId, String query);
}
