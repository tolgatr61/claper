package com.example.appli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.appli.acteurRecyclerView.ActeurAdapter;
import com.example.appli.db.Acteur;
import com.example.appli.db.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    private TextView desc;
    private ArrayList<Acteur> listActeurs;

    private RecyclerView recyclerView;
    private ActeurAdapter adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        desc = (TextView) findViewById(R.id.textView2);
        desc.setText(getIntent().getStringExtra("filmDescription"));
        recyclerView = findViewById(R.id.recyclerV);
        adaptItems();

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

    // Parsing de JSON en objet grace à la librairie Gson. (dépendance si besoin sur le fichier build.gradle)
    public ArrayList<Acteur> parseActeurs() {
        String jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "acteurs.json");
        // Log.i("data", jsonFileString);

        Gson gson = new Gson();
        Type listActeurType = new TypeToken<List<Acteur>>() { }.getType();

        List<Acteur> allActeurs = gson.fromJson(jsonFileString, listActeurType);

        ArrayList<Acteur> acteursParsed = new ArrayList<Acteur>();
        for (int i = 0; i < getIntent().getStringArrayListExtra("filmActeurs").size(); i++) {
            acteursParsed.add(allActeurs.get(Integer.parseInt(getIntent().getStringArrayListExtra("filmActeurs").get(i))));
        }
        return acteursParsed;
    }

    public void adaptItems() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptor = new ActeurAdapter();
        recyclerView.setAdapter(adaptor);
        adaptor.setData(parseActeurs());
        adaptor.notifyDataSetChanged();
    }

}