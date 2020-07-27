package com.example.mynotes;

import android.app.Application;

import com.example.mynotes.data.Keystore;
import com.example.mynotes.data.NotesRepository;
import com.example.mynotes.data.SimpleNotesRepository;

public class App extends Application {
    private static NotesRepository notesRepository;
    private static Keystore keystore;

    public static NotesRepository getNotesRepository() {
        return notesRepository;
    }

    public static Keystore getKeystore() {
        return keystore;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notesRepository = new SimpleNotesRepository();
    }
}
