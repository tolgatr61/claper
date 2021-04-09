package com.example.appli.filmRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appli.FourthActivity;
import com.example.appli.R;
import com.example.appli.db.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmViewHolder> {

    private ArrayList<Film> films;
    private Context context;
    public FilmAdapter(FourthActivity activity) {
        films = new ArrayList<Film>();
        context = activity;
    }

    public void setData(ArrayList<Film> f) {
        films = f;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View filmView = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new FilmViewHolder(filmView);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        Film film = films.get(position);
        Picasso.get().load("file:///android_asset/"+film.getImageURL()).into(holder.image);
        holder.title.setText(film.getName());
        holder.body.setText(film.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, film.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return films.size();
    }
}
