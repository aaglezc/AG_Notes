package com.example.ag_notes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ag_notes.Model.Nota;

import java.util.ArrayList;

public class ListNotesAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Nota> noteList;


    public ListNotesAdapter(Context context, int layout, ArrayList<Nota> notesList) {
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
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);

            holder.txtTitle = row.findViewById(R.id.txtTitle);
            holder.txtDesc  = row.findViewById(R.id.txtDesc);
            holder.imageView= row.findViewById(R.id.imgIcon);

            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }

        Nota nota = noteList.get(i);

        holder.txtTitle.setText(nota.getTitle());
        holder.txtDesc.setText(nota.getDate());

        byte[] noteImage = nota.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(noteImage,0,noteImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView  txtTitle;
        TextView  txtDesc;

    }
}
