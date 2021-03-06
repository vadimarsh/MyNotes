package com.example.mynotes;

import android.app.Application;

import com.example.mynotes.data.DBNotesRepository;
import com.example.mynotes.data.NotesRepository;
import com.example.mynotes.service.EncryptedKeystore;
import com.example.mynotes.service.Keystore;

public class App extends Application {
    private static NotesRepository notesRepository;
    private static Keystore keystore;
    private static App instance;

    public static NotesRepository getNotesRepository() {
        return notesRepository;
    }

    public static Keystore getKeystore() {
        return keystore;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        notesRepository = new DBNotesRepository();
        keystore = new EncryptedKeystore();

    }
}
