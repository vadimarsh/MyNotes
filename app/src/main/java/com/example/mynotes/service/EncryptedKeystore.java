package com.example.mynotes.service;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.mynotes.App;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class EncryptedKeystore implements Keystore {
    public static final String PINCODE = "pin";
    public static final String SALT = "salt";

    private SharedPreferences sharedPreferences;
    private MessageDigest messageDigest;

    public EncryptedKeystore() {
        sharedPreferences = App.getInstance().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private String encript(String pin, String salt) {
        pin += salt;
        byte[] data1 = pin.getBytes(StandardCharsets.UTF_8);
        byte[] digest = messageDigest.digest(data1);
        return new String(digest, StandardCharsets.UTF_8);
    }

    @Override
    public boolean hasPin() {
        String savedpin = sharedPreferences.getString(PINCODE, "");
        return !(savedpin.equals(""));
    }

    @Override
    public boolean checkPin(String pin) {
        String savedPin = sharedPreferences.getString(PINCODE, "");
        String savedSalt = sharedPreferences.getString(SALT, "somestaticsalt");
        pin = encript(pin, savedSalt);
        return pin.equals(savedPin);
    }

    private String generateSalt() {
        return Calendar.getInstance().getTime().toString();
    }

    @Override
    public void saveNew(String pin) {
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        String salt = generateSalt();
        pin = encript(pin, salt);
        myEditor.putString(PINCODE, pin);
        myEditor.putString(SALT, salt);
        myEditor.apply();
    }
}
