package com.example.appli.filmRecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appli.R;

// La vue des items.
public class FilmViewHolder extends RecyclerView.ViewHolder {

    ImageView image;
    TextView title;
    TextView body;

    public FilmViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image);
        title = itemView.findViewById(R.id.title);
        body = itemView.findViewById(R.id.body);
    }
}

