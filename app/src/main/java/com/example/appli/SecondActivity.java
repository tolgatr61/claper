package com.example.appli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    // Composants
    private TextView title;
    private ImageView imageView;
    private Button btDescription;
    private Button btHistorique;

    private String id;
    private String titre;
    private String description;
    private String filmURL;
    private ArrayList<String> filmActeurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialisation des attributs;
        imageView = findViewById(R.id.image_result);
        btDescription = findViewById(R.id.btn_description);
        title = findViewById(R.id.titre);
        btHistorique = findViewById(R.id.btn_historique);

        handleLoader();
        eventDescription();
        eventHistorique();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("id", id);
        savedInstanceState.putString("titre", titre);
        savedInstanceState.putString("description", description);
        savedInstanceState.putString("filmURL", filmURL);
        savedInstanceState.putStringArrayList("filmActeurs", filmActeurs);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        id = savedInstanceState.getString("id");
        titre = savedInstanceState.getString("titre");
        description = savedInstanceState.getString("description");
        filmURL = savedInstanceState.getString("filmURL");
        filmActeurs = savedInstanceState.getStringArrayList("filmActeurs");
    }

    public void eventDescription() {
        // Clicklistener sur le bouton qui redirige vers une 3ème activity.
        btDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ThirdActivity.class);
                intent.putExtra("filmId", id);
                intent.putExtra("filmName", titre);
                intent.putExtra("filmDescription", description);
                intent.putExtra("filmURL", filmURL);
                intent.putStringArrayListExtra("filmActeurs", filmActeurs);
                startActivity(intent);
                // onPause();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void handleLoader() {
        if (getIntent().getStringExtra("intentName") != null && getIntent().getStringExtra("intentName").equals("Loader")) {
            if (id == null || titre == null || description == null || filmURL == null) {
                id = getIntent().getStringExtra("filmId");
                titre = getIntent().getStringExtra("filmName");
                description = getIntent().getStringExtra("filmDescription");
                filmURL = getIntent().getStringExtra("filmURL");
                filmActeurs = getIntent().getStringArrayListExtra("filmActeurs");
            }

            try {
                InputStream ims = getAssets().open(filmURL);
                Drawable d = Drawable.createFromStream(ims, null);
                imageView.setImageDrawable(d);
                title.setText(titre);
                ims.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void eventHistorique() {
        btHistorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FourthActivity.class);
                startActivity(intent);
            }
        });
    }

}