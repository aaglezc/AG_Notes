package com.example.ag_notes.Model;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteBaseHelper extends SQLiteOpenHelper {


    String table = "CREATE TABLE Category(id INTEGER PRIMARY KEY , name TEXT)";

    public SQLiteBaseHelper(Context context, String  name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory,version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE Category");
        db.execSQL(table);

    }
}
