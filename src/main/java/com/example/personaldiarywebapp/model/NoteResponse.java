package com.example.personaldiarywebapp.model;

import com.example.personaldiarywebapp.domain.Category;
import com.example.personaldiarywebapp.domain.Note;

import java.util.Date;

public class NoteResponse {

    private Long id;
    private String title;
    private String content;
    private Date lastUpdatedTime;
    private Category category;

    public NoteResponse(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.lastUpdatedTime = note.getLastUpdatedTime();
        this.category = note.getCategory();
    }
}
