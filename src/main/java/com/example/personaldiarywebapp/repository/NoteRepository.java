package com.example.personaldiarywebapp.repository;

import com.example.personaldiarywebapp.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT note FROM Note note where note.title like %:query% AND note.appUser.id=:userId")
    List<Note> searchAtTitle(String query, Long userId);

    @Query("SELECT note FROM Note note where note.content like %:query% AND note.appUser.id=:userId")
    List<Note> searchAtContent(String query, Long userId);

    @Query("SELECT note FROM Note note where note.appUser.id=:id")
    List<Note> findAllByUserId(Long id);

    @Query("SELECT note FROM Note note where note.category.id=:categoryId")
    List<Note> findNoteByCategory(Long categoryId);

    @Query("SELECT note FROM Note note  where note.category.id=:categoryId AND " +
            "(note.title like %:query% OR note.content like %:query%)")
    List<Note> searchNotesByKeywordInCategory(Long categoryId, String query);
}
