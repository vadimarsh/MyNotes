package com.example.mynotes.service;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.mynotes.App;

public class SimpleKeystore implements Keystore {
    public static final String PINCODE = "pin";
    private SharedPreferences sharedPreferences;
    private Context context;

    public SimpleKeystore() {
        context = App.getInstance();
        sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);

    }

    @Override
    public boolean hasPin() {
        String savedpin = sharedPreferences.getString(PINCODE, "");
        return !(savedpin.equals(""));
    }

    @Override
    public boolean checkPin(String pin) {
        String savedpin = sharedPreferences.getString(PINCODE, "");
        return pin.equals(savedpin);
    }

    @Override
    public void saveNew(String pin) {
        SharedPreferences.Editor myEditor = sharedPreferences.edit();
        myEditor.putString(PINCODE, pin);
        myEditor.apply();
    }
}
