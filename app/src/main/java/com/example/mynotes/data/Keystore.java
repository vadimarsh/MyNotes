package com.example.mynotes.data;

public interface Keystore {
    boolean hasPin();

    boolean checkPin(String pin);

    void saveNew(String pin);
}
