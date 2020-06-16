package com.example.ag_notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ag_notes.Model.Notes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.recyclerNotes);
        //Set verticall
        rv.setLayoutManager(new LinearLayoutManager(this));

        notesAdapter = new NotesAdapter(getNotes());
        rv.setAdapter(notesAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.new_note:
                Intent newNoteAct = new Intent(this,AddNoteActivity.class);
                startActivity(newNoteAct);

                break;
        }
        return true;
    }

    public List<Notes> getNotes()
    {
        List<Notes> notas = new ArrayList<>();
        notas.add(new Notes(1,"Nota 1","descdsfsfdf", new Date(),R.drawable.images));
        notas.add(new Notes(2,"Nota 1","descdsfsfdf", new Date(),R.drawable.images2));
        notas.add(new Notes(2,"Nota 1","descdsfsfdf", new Date(),R.drawable.images3));
        notas.add(new Notes(2,"Nota 1","descdsfsfdf", new Date(),R.drawable.images4));
        notas.add(new Notes(2,"Nota 1","descdsfsfdf", new Date(),R.drawable.images5));
        return notas;

    }

}
