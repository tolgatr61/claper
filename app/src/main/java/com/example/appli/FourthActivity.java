package com.example.appli;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.appli.filmRecyclerView.FilmAdapter;
import com.example.appli.db.Film;
import com.example.appli.db.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class FourthActivity extends AppCompatActivity {

    private Integer[] indexes;
    private RecyclerView recyclerView;
    private FilmAdapter adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        File file = new File(getApplicationContext().getFilesDir(),"save.txt");
        if (file.exists()) {
            indexes = readIndexes();
            recyclerView = findViewById(R.id.recyclerView);
            adaptItems();
        }

    }

    private Integer[] readIndexes() {
        String sIndexes = readFromFile(this);
        String[] result = sIndexes.split(" ");
        Integer[] res = new Integer[result.length];

        for (int i = 0; i < result.length; i++){
            res[i] = Integer.parseInt(result[i]);
        }
        return res;
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("save.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Activité de film", "Fichier non trouvé " + e.toString());
        } catch (IOException e) {
            Log.e("Activité de film", "Ne peux pas lire le fichier: " + e.toString());
        }

        return ret;
    }

    public ArrayList<Film> parseMovieFromJSON(Integer[] indexes) {
        String jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "films.json");
        Gson gson = new Gson();
        Type listFilmType = new TypeToken<List<Film>>() { }.getType();
        List<Film> films = gson.fromJson(jsonFileString, listFilmType);

        ArrayList<Film> indexedMovies = new ArrayList<Film>();
        for (int i = 0; i < indexes.length; i++) {
            indexedMovies.add(films.get(indexes[i]));
        }
        return indexedMovies;
    }

    public void adaptItems() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptor = new FilmAdapter(this);
        recyclerView.setAdapter(adaptor);
        adaptor.setData(reverseList(parseMovieFromJSON(indexes)));
        adaptor.notifyDataSetChanged();
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

    public ArrayList<Film> reverseList(ArrayList<Film> films) {
        ArrayList<Film> mithian = new ArrayList<Film>();
        for (int i = films.size() - 1; i >= 0; i--) {
            mithian.add(films.get(i));
        }
        return mithian;
    }
}