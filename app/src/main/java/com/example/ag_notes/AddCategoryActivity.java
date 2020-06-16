package com.example.ag_notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ag_notes.Model.SQLiteBaseHelper;
import com.example.ag_notes.Model.SQLiteHelper;

public class AddCategoryActivity extends AppCompatActivity {


    EditText edtName;

    public static SQLiteHelper mySqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        edtName = (EditText) findViewById(R.id.edtName);

        mySqliteHelper = new  SQLiteHelper(this,"RECORDDB.sqlite",null,1);

        //creating table notes
        mySqliteHelper.queryData("CREATE TABLE IF NOT EXISTS Category (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR)");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save_category:

                //if (edtName.getText().toString() != "Default") {
                    try {
                        mySqliteHelper.insertDataCat(edtName.getText().toString().trim());
                        Toast.makeText(AddCategoryActivity.this,"Category Added Successfully",Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    //Call ListCategory
                    Intent newCatActivity = new Intent(this,ListCategoryActivity.class);
                    startActivity(newCatActivity);
                //}

                break;

        }
        return true;
    }
}
