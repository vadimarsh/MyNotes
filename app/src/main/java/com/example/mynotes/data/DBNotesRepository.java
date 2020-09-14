package com.example.mynotes.data;

import androidx.room.Room;

import com.example.mynotes.App;

import java.util.List;

public class DBNotesRepository implements NotesRepository {

    private NoteDatabase noteDatabase;

    public DBNotesRepository() {
        noteDatabase = Room.databaseBuilder(App.getInstance(), NoteDatabase.class, "notes")
                .allowMainThreadQueries()
                .build();
    }

    @Override
    public Note getNoteById(int id) {
        return noteDatabase.NoteDao().getById(id);
    }

    @Override
    public List<Note> getNotes() {
        return noteDatabase.NoteDao().getAllNotes();
    }

    @Override
    public void saveNote(Note note) {
        noteDatabase.NoteDao().insert(note);
    }

    @Override
    public void deleteNote(Note note) {
        noteDatabase.NoteDao().delete(note);
    }

    @Override
    public void updateNote(Note note) {
        noteDatabase.NoteDao().update(note);
    }


}
