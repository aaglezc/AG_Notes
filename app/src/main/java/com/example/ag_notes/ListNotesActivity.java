package com.example.ag_notes;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ag_notes.Model.Nota;
import com.example.ag_notes.Model.Notes;
import com.example.ag_notes.Model.SQLiteHelper;

import java.util.ArrayList;
import java.util.Date;

public class ListNotesActivity extends AppCompatActivity {

    public static SQLiteHelper mySqliteHelper;

    String catName = "Default";
    ListView myListView;
    ArrayList<Nota> myListNote;
    ListNotesAdapter myAdapter = null;
    ImageView imageViewIcon;

    String sort = "Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);

        Bundle b = getIntent().getExtras();
        if( b!=null) {
            String catB = "Default";
            if(b.containsKey("catName")) {
                catName= b.getString("catName");
                //edtTitle.setText(recId);

            }
        }
        else
        {
           // edtTitle.setText("No DATA");
        }
        Log.d("From CategoryList",catName);


        //creating db
        mySqliteHelper = new  SQLiteHelper(this,"RECORDDB.sqlite",null,1);

        //dropping db

        //mySqliteHelper.queryData("DROP TABLE Notes");

        mySqliteHelper.queryData("CREATE TABLE IF NOT EXISTS Notes(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title VARCHAR, desc VARCHAR, date DATE , image BLOB, category VARCHAR, audio VARCHAR)");


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Notes of "+ catName);



        myListView = findViewById(R.id.listView);
        myListNote = new ArrayList<>();
        myAdapter  = new ListNotesAdapter(this,R.layout.row_note,myListNote);

        myListView.setAdapter(myAdapter);

        // get data from SQLite

        //Cursor cursor = AddNoteActivity.mySqliteHelper.getData("SELECT * FROM Notes");
        String sqlstr = "SELECT * FROM Notes WHERE category = '"+ catName.trim()+"' ORDER BY date";
        //String sqlstr = "SELECT * FROM Notes";
        Cursor cursor = mySqliteHelper.getData(sqlstr);

        myListNote.clear();

        while(cursor.moveToNext())
        {
            int id       = cursor.getInt(0);
            String title = cursor.getString(1);
            String desc  = cursor.getString(2);
            String date  = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            String category = cursor.getString(5);
            String audio   = cursor.getString(6);

            // add to list
            myListNote.add(new Nota(id,title,desc,date,image,category,audio));


        }
        myAdapter.notifyDataSetChanged();

        if (myListNote.size() == 0 )
        {
            Toast.makeText(this,"No Notes found",Toast.LENGTH_SHORT).show();
        }

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                // code later
                final CharSequence[] items = {"Delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(ListNotesActivity.this);
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i==0)   //delete
                        {
                            //Cursor c = AddNoteActivity.mySqliteHelper.getData("SELECT id FROM Notes");
                           // Cursor c = mySqliteHelper.getData("SELECT id FROM Notes");
                            //ArrayList<Integer> arrayID = new ArrayList<Integer>();
                            //while(c.moveToNext())
                            //{
                             //   arrayID.add(c.getInt(0));
                            //}


                            Nota nota = myListNote.get(position);

                            Integer id  = nota.getId();

                            Log.d("Name:",catName);
                            Log.d("Deleting ID:",id.toString());

                            showDialogDelete(id);

                        }
                    }
                });
                dialog.show();

                return true;
            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Nota nota = myListNote.get(i);

                String id  = nota.getId().toString();

                Log.d("Name:",catName);
                Log.d("ID:",id);


                Intent intent = new Intent(getApplicationContext(), UpdateNoteActivity.class);
                //String id = String.valueOf(i+1);
                intent.putExtra("idRec",id);
                intent.putExtra("catName",catName);


                startActivity(intent);
            }
        });



    }

    private void showDialogDelete(final int idRecord)
    {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ListNotesActivity.this);
        dialogDelete.setTitle("Warning!");
        dialogDelete.setMessage("Are you sure to delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try
                {
                    //AddNoteActivity.mySqliteHelper.deleteData(idRecord);
                    mySqliteHelper.deleteData(idRecord);
                    Toast.makeText(ListNotesActivity.this,"Delete Success",Toast.LENGTH_SHORT).show();

                }
                catch (Exception e)
                {
                    Log.d("error",e.getMessage());

                }
                updateRecordList(sort);
                myAdapter.notifyDataSetChanged();

            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        dialogDelete.show();

    }


    private void updateRecordList(String s)
    {
        //Cursor cursor = AddNoteActivity.mySqliteHelper.getData("SELECT * FROM Notes");
        String sqlOrder ="";
        if (s == "Name")
        {
            sqlOrder = " ORDER BY title";
            sort = "Date";
        }

        if (s == "Date")
        {
            sqlOrder = " ORDER BY date";
            sort = "Name";
        }

        String sqlstr = "SELECT * FROM Notes WHERE category = '"+ catName.trim()+"'" + sqlOrder;
        Cursor cursor = mySqliteHelper.getData(sqlstr);


        Log.d("SQL=",sqlstr);

        myListNote.clear();

        while(cursor.moveToNext())
        {
            int id       = cursor.getInt(0);
            String title = cursor.getString(1);
            String desc  = cursor.getString(2);
            String date  = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            String category = cursor.getString(5);
            String audio   = cursor.getString(6);

            // add to list
            myListNote.add(new Nota(id,title,desc,date,image,category,audio));

            myAdapter.notifyDataSetChanged();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.new_note:

                Intent intent = new Intent(this,AddNoteActivity.class);
                intent.putExtra("catName",catName);
                startActivity(intent);
                break;
            case R.id.categories:
                Intent intent2 = new Intent(this,ListCategoryActivity.class);
                startActivity(intent2);
                break;
            case R.id.sort:
                updateRecordList(sort);

        }
        return true;
    }
}
