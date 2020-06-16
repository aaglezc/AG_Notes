package com.example.ag_notes.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.Date;

public class SQLiteHelper extends SQLiteOpenHelper {


    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory,version);

    }

    public void queryData(String sql)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    //insert
    public void insertData(String title, String desc, String date, byte[] photo, String category, String audio)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO Notes VALUES(NULL,?,?,?,?,?,?)";
        SQLiteStatement st = db.compileStatement(sql);
        st.clearBindings();
        st.bindString(1,title);
        st.bindString(2,desc);
        st.bindString(3,date.toString());

        st.bindBlob(4,photo);
        st.bindString(5,category);
        st.bindString(6,audio);

        st.executeInsert();
    }

    //insert Category
    public void insertDataCat(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO Category VALUES(NULL,?)";
        SQLiteStatement st = db.compileStatement(sql);
        st.clearBindings();
        st.bindString(1,name);
        st.executeInsert();
    }
    //update
    public void updateData(String title, String desc , String date , byte[] photo , String category, String audio, int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE Notes SET title=?, desc=?, image=? WHERE id=?";
        SQLiteStatement st = db.compileStatement(sql);
        st.clearBindings();
        st.bindString(1,title);
        st.bindString(2,desc);
        st.bindBlob(3,photo);
        st.bindDouble(4,(double)id);
        st.execute();

        db.close();

    }

    //delete
    public void deleteData(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM Notes WHERE id = ?";
        SQLiteStatement st = db.compileStatement(sql);
        st.clearBindings();
        st.bindDouble(1,(double)id);
        st.execute();
        db.close();

    }

    //delete category
    public void deleteDataCat(int ids)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM Category WHERE id = ?";
        SQLiteStatement st = db.compileStatement(sql);
        st.clearBindings();
        st.bindDouble(1,(double)ids);
        st.execute();
        db.close();

    }

    public Cursor getData(String sql)
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
