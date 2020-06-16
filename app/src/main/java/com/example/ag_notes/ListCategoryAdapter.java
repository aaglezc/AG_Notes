package com.example.ag_notes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ag_notes.Model.Category;


import java.util.ArrayList;

public class ListCategoryAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Category> noteList;


    public ListCategoryAdapter(Context context, int layout, ArrayList<Category> notesList) {
        this.context = context;
        this.layout = layout;
        this.noteList = notesList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int i) {
        return noteList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ListCategoryAdapter.ViewHolder holder = new ListCategoryAdapter.ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);

            holder.txtTitle = row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (ListCategoryAdapter.ViewHolder) row.getTag();
        }

        Category nota = noteList.get(i);

        holder.txtTitle.setText(nota.getName());

        return row;
    }

    private class ViewHolder {

        TextView txtTitle;

    }
}
