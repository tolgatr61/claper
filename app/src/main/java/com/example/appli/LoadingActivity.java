package com.example.appli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.example.appli.db.Film;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.appli.db.Utils;

// Activité "invisible" (qui se doit de l'être) pour les utilisateurs qui va permettre de faire la transition entre les données de la db JSON et la 2ème activity.
public class LoadingActivity extends AppCompatActivity {

    private Film film;
    public static int compteur=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        film = parseRandomMovieFromJSON();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("intentName", "Loader");
        intent.putExtra("filmId", film.getId());
        intent.putExtra("filmName", film.getName());
        intent.putExtra("filmURL", film.getImageURL());
        intent.putExtra("filmDescription", film.getDescription());
        ArrayList<String> newList = new ArrayList<>(film.getActeurs().size());
        for (Integer myInt : film.getActeurs()) {
            newList.add(String.valueOf(myInt));
        }
        intent.putStringArrayListExtra("filmActeurs", newList);
        startActivity(intent);
        finish();
    }

    // Parsing de JSON en objet grace à la librairie Gson. (dépendance si besoin sur le fichier build.gradle)
    public Film parseRandomMovieFromJSON() {
        String jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "films.json");
        // Log.i("data", jsonFileString);

        Gson gson = new Gson();
        Type listFilmType = new TypeToken<List<Film>>() { }.getType();

        List<Film> films = gson.fromJson(jsonFileString, listFilmType);

        //Random r = new Random();
        //int randIndex = r.nextInt(films.size());
        compteur = compteur >= films.size()-1 ? 0 : compteur+1;

        String s = readFromFile(this);
        s += String.valueOf(compteur) + " ";
        writeToFile(s,this);
        return films.get(compteur);
    }

    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("save.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Save loader", "Fichier non trouvé: " + e.toString());
        } catch (IOException e) {
            Log.e("Save loader", "Ne peux pas le lire fichier: " + e.toString());
        }

        return ret;
    }

}