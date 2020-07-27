package com.example.mynotes.data;

import java.util.List;

public interface NotesRepository {
    Note getNoteById(int id);

    List<Note> getNotes();

    void saveNote(Note note);

    void deleteById(int id);

    void deleteById(Note note);

    void updateNote(Note note);
}
