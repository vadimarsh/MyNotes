package com.example.mynotes.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mynotes.App;
import com.example.mynotes.R;
import com.example.mynotes.data.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditNoteActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private EditText etTitle;
    private EditText etContent;
    private EditText etDeadline;
    private LinearLayout llDeadlineBlock;
    private CheckBox chbxDeadLine;
    private ImageButton imgBtnCal;
    private int editedNoteID;
    private Note editedNote;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        init();

        editedNoteID = getIntent().getIntExtra("edit_note_id", -1);
        if (editedNoteID >= 0) {
            fillViews(editedNoteID);
        }
    }

    private void fillViews(int noteid) {
        editedNote = App.getNotesRepository().getNoteById(noteid);
        etTitle.setText(editedNote.getTitle());
        etContent.setText(editedNote.getContent());
        if (editedNote.getIsDeadLine()) {
            llDeadlineBlock.setVisibility(View.VISIBLE);
            chbxDeadLine.setChecked(true);

            etDeadline.setText(simpleDateFormat.format(editedNote.getDateDeadline()));
        }
    }

    private void init() {
        editedNote = null;
        final Calendar cldr = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        etDeadline = findViewById(R.id.et_deadline);
        myToolbar = findViewById(R.id.my_toolbar);
        chbxDeadLine = findViewById(R.id.chbx_isdeadline);
        llDeadlineBlock = findViewById(R.id.date_block);
        imgBtnCal = findViewById(R.id.ibt_calendar);
        etDeadline.setText(simpleDateFormat.format(new Date()));
        imgBtnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog picker = new DatePickerDialog(EditNoteActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                cldr.set(year, monthOfYear, dayOfMonth);
                                Date date = cldr.getTime();
                                etDeadline.setText(simpleDateFormat.format(date));
                            }
                        }, year, month, day);
                picker.show();

            }
        });
        chbxDeadLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    llDeadlineBlock.setVisibility(View.VISIBLE);
                } else {
                    llDeadlineBlock.setVisibility(View.GONE);
                }
            }
        });
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.item_settings).setVisible(false);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.item_save == item.getItemId()) {
            saveNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    private void saveNote() {
        String titleNote = etTitle.getText().toString();
        String contentNote = etContent.getText().toString();
        String deadlineNote = etDeadline.getText().toString();
        Date dateDeadline = null;
        boolean isDeadline = chbxDeadLine.isChecked();

        try {
            dateDeadline = simpleDateFormat.parse(deadlineNote);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (titleNote.isEmpty() && contentNote.isEmpty()) {
            showMessage(getString(R.string.msg_note_empty_error));
        } else {
            if (editedNoteID >= 0) {
                editedNote.setTitle(titleNote);
                editedNote.setContent(contentNote);
                editedNote.setIsDeadLine(isDeadline);
                editedNote.setDateDeadline(dateDeadline);
                App.getNotesRepository().updateNote(editedNote);
                showMessage(getString(R.string.msg_note_succes));
            } else {
                Note newNote = new Note(App.getNotesRepository().getNotes().size(), titleNote, contentNote, dateDeadline, isDeadline);
                App.getNotesRepository().saveNote(newNote);


                showMessage(getString(R.string.msg_note_added));
            }

            finish();
        }
    }
}


