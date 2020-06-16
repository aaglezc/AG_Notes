package com.example.ag_notes;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ag_notes.Model.Category;
import com.example.ag_notes.Model.Nota;
import com.example.ag_notes.Model.SQLiteBaseHelper;
import com.example.ag_notes.Model.SQLiteHelper;

import java.util.ArrayList;

public class ListCategoryActivity extends AppCompatActivity {

    public static SQLiteHelper mySqliteHelper;

    String catName = "Default";
    ListView myListView;
    ArrayList<Category> myListNote;
    ListCategoryAdapter myAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);



        //creating db
        mySqliteHelper = new  SQLiteHelper(this,"RECORDDB.sqlite",null,1);

        //dropping db

        // mySqliteHelper.queryData("DROP TABLE Category");

        //creating table notes
        mySqliteHelper.queryData("CREATE TABLE IF NOT EXISTS Category (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR)");


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Categories");


        myListView = findViewById(R.id.listView);
        myListNote = new ArrayList<>();
        myAdapter  = new ListCategoryAdapter(this,R.layout.row_category,myListNote);

        myListView.setAdapter(myAdapter);

        // get data from SQLite

        //Cursor cursor = AddNoteActivity.mySqliteHelper.getData("SELECT * FROM Notes");
        Cursor cursor = mySqliteHelper.getData("SELECT * FROM Category");

        myListNote.clear();

        while(cursor.moveToNext())
        {
            int id       = cursor.getInt(0);
            String title = cursor.getString(1);

            // add to list
            myListNote.add(new Category(id,title));


        }
        myAdapter.notifyDataSetChanged();

        if (myListNote.size() == 0 )
        {
            Toast.makeText(this,"No Categories found",Toast.LENGTH_SHORT).show();
        }

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                // code later
                final CharSequence[] items = {"Delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(ListCategoryActivity.this);
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i==0)   //delete
                        {
                            //Cursor c = AddNoteActivity.mySqliteHelper.getData("SELECT id FROM Notes");
                           // Cursor c = mySqliteHelper.getData("SELECT id FROM Category");
                            //ArrayList<Integer> arrayID = new ArrayList<Integer>();
                            //while(c.moveToNext())
                            //{
                             //   arrayID.add(c.getInt(0));
                            //}

                            //showDialogDelete(arrayID.get(position));

                            /////
                           Category category= myListNote.get(position);

                           Integer ids  = category.getId();

                            Log.d("Name:",catName);
                            Log.d("Deleting ID:",ids.toString());

                            showDialogDelete(ids);





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
                Log.d("Click","Clicked on :");
                Category c = myListNote.get(i);

                String cat = c.getName();
                String ids  = c.getId().toString();

                Log.d("Name:",cat);
                Log.d("ID:",ids);

                Intent intent = new Intent(getApplicationContext(),ListNotesActivity.class);
                intent.putExtra("catName",cat);
                startActivity(intent);

            }
        });

    }

    private void showDialogDelete(final int idRecord)
    {

        //

        ///

        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ListCategoryActivity.this);
        dialogDelete.setTitle("Warning!");
        dialogDelete.setMessage("Are you sure to delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try {
                    //AddNoteActivity.mySqliteHelper.deleteData(idRecord);
                    mySqliteHelper.deleteDataCat(idRecord);
                    Toast.makeText(ListCategoryActivity.this, "Delete Success " + idRecord, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Log.d("error", e.getMessage());

                }
                updateRecordList();

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        dialogDelete.show();

    }


    private void updateRecordList()
    {
        //Cursor cursor = AddNoteActivity.mySqliteHelper.getData("SELECT * FROM Notes");
        Cursor cursor = mySqliteHelper.getData("SELECT * FROM Category");

        myListNote.clear();

        while(cursor.moveToNext())
        {
            int id       = cursor.getInt(0);
            String title = cursor.getString(1);

            // add to list
            myListNote.add(new Category(id,title));

            myAdapter.notifyDataSetChanged();


        }
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.new_category:
                Intent newCatActivity = new Intent(this,AddCategoryActivity.class);
                startActivity(newCatActivity);
                break;

        }
        return true;
    }
}
