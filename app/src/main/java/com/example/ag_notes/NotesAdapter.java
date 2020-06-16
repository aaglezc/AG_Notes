package com.example.ag_notes;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ag_notes.Model.Notes;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {



    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDate;
        private ImageView ivPicure;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView)  itemView.findViewById(R.id.tvTitle);
            tvDate  = (TextView)  itemView.findViewById(R.id.tvDate);
            ivPicure = (ImageView) itemView.findViewById(R.id.imgNote);

        }
    }

    public List<Notes> notesLista;

    public NotesAdapter(List<Notes> notesLista) {
        this.notesLista = notesLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvTitle.setText(notesLista.get(i).getTitle());
        viewHolder.tvDate.setText(notesLista.get(i).getDesc());
        viewHolder.ivPicure.setImageResource(notesLista.get(i).getImage());

    }

    @Override
    public int getItemCount() {
        return notesLista.size();
    }


}
