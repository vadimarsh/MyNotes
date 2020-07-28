package com.example.mynotes.service;

public interface Keystore {
    int PIN_LENGTH = 4;

    boolean hasPin();

    boolean checkPin(String pin);

    void saveNew(String pin);
}
