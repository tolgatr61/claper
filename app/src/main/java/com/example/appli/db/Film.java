package com.example.appli.db;

import com.example.appli.db.Acteur;

import java.util.HashSet;
import java.util.List;

public class Film {

    private String id;
    private String name;
    private String imageURL;
    private String description;
    private List<Integer> acteurs;

    public Film(String id, String name, String imageURL, String description, List<Integer> acteurs) {
        id = id;
        name = name;
        imageURL = imageURL;
        description = description;
        acteurs = acteurs;
    }

    public String getId() { return id; }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDescription() {
        return description;
    }

    public List<Integer> getActeurs() {
        return acteurs;
    }
}
