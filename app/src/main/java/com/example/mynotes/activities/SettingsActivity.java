package com.example.mynotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mynotes.App;
import com.example.mynotes.R;
import com.example.mynotes.service.Keystore;

public class SettingsActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private EditText etPin;
    private Button btnSave;
    private ImageButton btnShowPin;
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
        btnShowPin = findViewById(R.id.btn_show_pin);
        myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setTitle(getString(R.string.toolbar_title_settings));
        setSupportActionBar(myToolbar);

        btnShowPin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) { // нажатие
                    etPin.setInputType(InputType.TYPE_CLASS_NUMBER);
                    btnShowPin.setImageResource(R.drawable.ic_visibility);
                } else {
                    etPin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    btnShowPin.setImageResource(R.drawable.ic_visibility_off);
                }
                return true;
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPin = etPin.getText().toString();
                if (Keystore.PIN_LENGTH != enteredPin.length()) {
                    showToast("Пин должен состоять из 4 цифр ");
                    etPin.setText("");
                } else {
                    keyStore.saveNew(enteredPin);
                    Intent intent = new Intent(SettingsActivity.this, NotesListActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
