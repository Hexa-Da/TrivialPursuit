package com.example.trivialpursuit;

public class Categorie {
    private String nom;
    private int couleur;

    public static final Categorie GEOGRAPHIE = new Categorie("Géographie", 0xFF1E88E5); // Bleu vif
    public static final Categorie HISTOIRE = new Categorie("Histoire", 0xFFD32F2F); // Rouge profond
    public static final Categorie DIVERTISSEMENT = new Categorie("Divertissement", 0xFFFFD600); // Jaune doré
    public static final Categorie ART_LITTERATURE = new Categorie("Art et Littérature", 0xFF9C27B0); // Violet vif
    public static final Categorie SCIENCES_NATURE = new Categorie("Sciences et Nature", 0xFF43A047); // Vert émeraude
    public static final Categorie SPORTS_LOISIRS = new Categorie("Sports et Loisirs", 0xFFFF6D00); // Orange vif

    public static final Categorie[] TOUTES_CATEGORIES = {
        GEOGRAPHIE, HISTOIRE, DIVERTISSEMENT, ART_LITTERATURE, SCIENCES_NATURE, SPORTS_LOISIRS
    };

    private Categorie(String nom, int couleur) {
        this.nom = nom;
        this.couleur = couleur;
    }

    public String getNom() {
        return nom;
    }

    public int getCouleur() {
        return couleur;
    }
} 