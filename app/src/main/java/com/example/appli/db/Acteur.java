package com.example.appli.db;

public class Acteur {

    private String id;
    private String nom;
    private String description;
    private String imageURL;

    public Acteur(String id, String nom, String description, String imageURL) {
        id = id;
        nom = nom;
        description = description;
        imageURL = imageURL;
    }

    public String getId() { return id; }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }
}
