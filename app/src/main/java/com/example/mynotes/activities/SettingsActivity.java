package com.example.mynotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mynotes.App;
import com.example.mynotes.R;
import com.example.mynotes.service.Keystore;

public class SettingsActivity extends AppCompatActivity {
    private EditText etPin;
    private Button btnSave;
    private Keystore keyStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    private void init() {
        keyStore = App.getKeystore();
        etPin = findViewById(R.id.et_pin);
        btnSave = findViewById(R.id.btn_save_pin);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setTitle(getString(R.string.toolbar_title_settings));
        setSupportActionBar(myToolbar);
        etPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > Keystore.PIN_LENGTH) {
                    etPin.setText(etPin.getText().subSequence(0, etPin.getText().length() - 1));
                    Toast.makeText(SettingsActivity.this, getString(R.string.msg_pin_length_err), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPin = etPin.getText().toString();
                if (Keystore.PIN_LENGTH != enteredPin.length()) {
                    Toast.makeText(SettingsActivity.this, getString(R.string.msg_pin_length_err), Toast.LENGTH_SHORT).show();
                    etPin.setText("");
                } else {
                    keyStore.saveNew(enteredPin);
                    Intent intent = new Intent(SettingsActivity.this, NotesListActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (App.getKeystore().hasPin()) {
            super.onBackPressed();
        }
    }
}
