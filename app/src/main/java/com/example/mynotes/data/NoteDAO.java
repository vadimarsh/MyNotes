package com.example.mynotes.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM note order by has_deadline desc, date_deadline asc")
    List<Note> getAllNotes();

    @Query("SELECT * FROM note WHERE id = :id")
    Note getById(int id);
}
