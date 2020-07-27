package com.example.mynotes.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SimpleNotesRepository implements NotesRepository {
    private static int id_count = 0;
    private List<Note> notelist;

    public SimpleNotesRepository() {
        notelist = new ArrayList<>();

    }

    private void sortByDate() {
        Collections.sort(notelist, new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                return -o1.getDateDeadline().compareTo(o2.getDateDeadline());
            }
        });
        Collections.sort(notelist, new Comparator<Note>() {

            @Override
            public int compare(Note o1, Note o2) {
                int result = 0;
                result = (!o1.getIsDeadLine()) && (o2.getIsDeadLine()) ? 1 : -1;
                return result;
            }
        });
    }

    @Override
    public Note getNoteById(int id) {
        for (Note note : notelist
        ) {
            if (note.getId() == id) {
                return note;
            }
        }
        return null;
    }

    @Override
    public List<Note> getNotes() {
        return this.notelist;
    }

    @Override
    public void saveNote(Note note) {
        notelist.add(note);
        note.setId(id_count++);
        sortByDate();
    }

    @Override
    public void deleteById(int id) {
        Note deleted = null;
        for (Note note : notelist
        ) {
            if (note.getId() == id) {
                deleted = note;
            }
        }
        notelist.remove(deleted);
    }

    @Override
    public void deleteById(Note note) {
        notelist.remove(note);
    }

    @Override
    public void updateNote(Note note) {
        sortByDate();

    }
}
