package com.example.mynotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.App;
import com.example.mynotes.NotesRecyclerAdapter;
import com.example.mynotes.R;
import com.example.mynotes.data.NotesRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class NotesListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private NotesRecyclerAdapter notesRecyclerAdapter;
    private long mlsecBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notesRecyclerAdapter.setNotes(App.getNotesRepository().getNotes());
        notesRecyclerAdapter.notifyDataSetChanged();
    }

    private void init() {
        fabAdd = findViewById(R.id.fabAddNote);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotesListActivity.this, EditNoteActivity.class));
            }
        });

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        recyclerView = findViewById(R.id.recycleView);
        NotesRepository notesRepository = App.getNotesRepository();
        notesRecyclerAdapter = new NotesRecyclerAdapter(this, notesRepository.getNotes());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(notesRecyclerAdapter);

        mlsecBackPressed = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes_list_activity, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.item_settings == item.getItemId()) {
            startActivity(new Intent(NotesListActivity.this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (mlsecBackPressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), R.string.msg_confirm_exit, Toast.LENGTH_SHORT).show();
        }
        mlsecBackPressed = System.currentTimeMillis();
    }
}
