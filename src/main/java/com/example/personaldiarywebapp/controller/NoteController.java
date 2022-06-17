package com.example.personaldiarywebapp.controller;

import com.example.personaldiarywebapp.domain.Note;
import com.example.personaldiarywebapp.model.ActionRepose;
import com.example.personaldiarywebapp.model.NoteRequest;
import com.example.personaldiarywebapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> showAllNotes() {
        return new ResponseEntity<>(noteService.finaAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody NoteRequest request) {
        return new ResponseEntity<>(noteService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getByNoteId(@PathVariable Long id) {
        return new ResponseEntity<>(noteService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateByNoteId(@PathVariable Long id, @RequestBody NoteRequest request) {
        return new ResponseEntity<>(noteService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ActionRepose> deleteByNoteId(@PathVariable Long id) {
        return new ResponseEntity<>(noteService.delete(id), HttpStatus.OK);
    }

    @GetMapping(params = "query")
    public ResponseEntity<List<Note>> searchNotesByKeyword(@RequestParam("query") String query,
                                                           @RequestParam("fi") String fi) {
        return new ResponseEntity<>(noteService.searchByQuery(query, fi), HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<List<Note>> getNoteByCategoryId(@PathVariable Long id) {
        return new ResponseEntity<>(noteService.findNoteByCategory(id), HttpStatus.OK);
    }

    @GetMapping(value = "/categories/{id}", params = "query")
    public ResponseEntity<List<Note>> searchNotesByKeywordInCategory(@PathVariable Long id,
                                                                     @RequestParam("query") String query) {
        return new ResponseEntity<>(noteService.searchNotesByKeywordInCategory(id, query), HttpStatus.OK);
    }
}
