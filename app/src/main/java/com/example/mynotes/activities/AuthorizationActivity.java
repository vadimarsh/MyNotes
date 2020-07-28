package com.example.mynotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mynotes.App;
import com.example.mynotes.R;
import com.example.mynotes.service.Keystore;

import static com.example.mynotes.service.Keystore.PIN_LENGTH;

public class AuthorizationActivity extends AppCompatActivity {
    private Keystore keystore;
    private Toolbar myToolbar;
    private Button[] numbButton = new Button[10];
    private Button bckspcButton;
    private String enteredPin;
    private View[] placeholder = new View[PIN_LENGTH];
    private int indexPlaceholder = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        keystore = App.getKeystore();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (keystore.hasPin()) {
            init();
        } else {
            Intent intent = new Intent(AuthorizationActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    }

    private void init() {

        enteredPin = "";
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        numbButton[0] = findViewById(R.id.btn_0);
        numbButton[1] = findViewById(R.id.btn_1);
        numbButton[2] = findViewById(R.id.btn_2);
        numbButton[3] = findViewById(R.id.btn_3);
        numbButton[4] = findViewById(R.id.btn_4);
        numbButton[5] = findViewById(R.id.btn_5);
        numbButton[6] = findViewById(R.id.btn_6);
        numbButton[7] = findViewById(R.id.btn_7);
        numbButton[8] = findViewById(R.id.btn_8);
        numbButton[9] = findViewById(R.id.btn_9);
        bckspcButton = findViewById(R.id.btn_backspace);
        bckspcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNumbFromPin();
            }
        });

        placeholder[0] = findViewById(R.id.plcHolder1);
        placeholder[1] = findViewById(R.id.plcHolder2);
        placeholder[2] = findViewById(R.id.plcHolder3);
        placeholder[3] = findViewById(R.id.plcHolder4);
        for (Button but : numbButton
        ) {
            final String numb = String.valueOf(but.getText());
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    putNumbToPin(numb);
                    checkPin();
                }
            });
        }

    }

    private void putNumbToPin(String numb) {
        if (enteredPin.length() < PIN_LENGTH) {
            enteredPin += numb;
            setPlaceholderImage(true);
            indexPlaceholder++;

        }
    }

    private void deleteNumbFromPin() {
        if (enteredPin.length() > 0) {
            enteredPin = enteredPin.substring(0, enteredPin.length() - 1);
            indexPlaceholder--;
            setPlaceholderImage(false);
        }
    }

    private void checkPin() {

        if (enteredPin.length() == PIN_LENGTH) {
            if (keystore.checkPin(enteredPin)) {
                Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                showToast(getString(R.string.auth_invalid_pin));
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    private void setPlaceholderImage(boolean filled) {
        if (filled) {
            placeholder[indexPlaceholder].setBackground(getResources().getDrawable(R.drawable.pin_placeholder_filled));
        } else {
            placeholder[indexPlaceholder].setBackground(getResources().getDrawable(R.drawable.pin_placeholder_empty));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
