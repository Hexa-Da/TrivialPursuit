package com.example.trivialpursuit;

public class Question {
    private String question;
    private String[] reponses;
    private int bonneReponse;
    private String categorie;

    public Question(String question, String[] reponses, int bonneReponse, String categorie) {
        this.question = question;
        this.reponses = reponses;
        this.bonneReponse = bonneReponse;
        this.categorie = categorie;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getReponses() {
        return reponses;
    }

    public int getBonneReponse() {
        return bonneReponse;
    }

    public String getCategorie() {
        return categorie;
    }
} 