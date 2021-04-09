package com.example.appli.acteurRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appli.R;
import com.example.appli.db.Acteur;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActeurAdapter extends RecyclerView.Adapter<ViewHolder> {

    ArrayList<Acteur> acteurs;
    public ActeurAdapter() {
        acteurs = new ArrayList<Acteur>();
    }

    public void setData(ArrayList<Acteur> ac) {
        acteurs = ac;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View acteurView = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(acteurView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Acteur acteur = acteurs.get(position);
        Picasso.get().load("file:///android_asset/"+acteur.getImageURL()).into(holder.image);
        holder.title.setText(acteur.getNom());
        holder.body.setText(acteur.getDescription());

    }

    @Override
    public int getItemCount() {
        return acteurs.size();
    }
}
