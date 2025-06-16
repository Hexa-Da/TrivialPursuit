package com.example.trivialpursuit;

public class Question {
    private String question;
    private String reponse;
    private String categorie;

    public Question(String question, String reponse, String categorie) {
        this.question = question;
        this.reponse = reponse;
        this.categorie = categorie;
    }

    public String getQuestion() {
        return question;
    }

    public String getReponse() {
        return reponse;
    }

    public String getCategorie() {
        return categorie;
    }

    public boolean verifierReponse(String reponseJoueur) {
        if (reponseJoueur == null) return false;
        
        // Normaliser les réponses
        String reponseNormalisee = normaliserTexte(reponse);
        String reponseJoueurNormalisee = normaliserTexte(reponseJoueur);
        
        // Vérifier si les réponses sont identiques
        if (reponseNormalisee.equals(reponseJoueurNormalisee)) {
            return true;
        }

        // Vérifier si la réponse du joueur contient la réponse correcte
        if (reponseJoueurNormalisee.contains(reponseNormalisee)) {
            return true;
        }

        // Vérifier si la réponse correcte contient la réponse du joueur
        if (reponseNormalisee.contains(reponseJoueurNormalisee)) {
            return true;
        }

        // Calculer la similarité entre les réponses
        double similarite = calculerSimilarite(reponseNormalisee, reponseJoueurNormalisee);
        return similarite >= 0.8; // 80% de similarité minimum
    }

    private String normaliserTexte(String texte) {
        if (texte == null) return "";
        
        // Convertir en minuscules
        texte = texte.toLowerCase();
        
        // Supprimer les accents
        texte = texte.replace("é", "e")
                    .replace("è", "e")
                    .replace("ê", "e")
                    .replace("ë", "e")
                    .replace("à", "a")
                    .replace("â", "a")
                    .replace("ä", "a")
                    .replace("î", "i")
                    .replace("ï", "i")
                    .replace("ô", "o")
                    .replace("ö", "o")
                    .replace("ù", "u")
                    .replace("û", "u")
                    .replace("ü", "u")
                    .replace("ç", "c")
                    .replace("œ", "oe")
                    .replace("æ", "ae");
        
        // Supprimer les caractères spéciaux et la ponctuation
        texte = texte.replaceAll("[^a-z0-9\\s]", "");
        
        // Supprimer les espaces superflus
        texte = texte.trim().replaceAll("\\s+", " ");
        
        return texte;
    }

    private double calculerSimilarite(String s1, String s2) {
        if (s1 == null || s2 == null) return 0.0;
        if (s1.equals(s2)) return 1.0;
        if (s1.isEmpty() || s2.isEmpty()) return 0.0;

        // Calculer la distance de Levenshtein
        int distance = distanceLevenshtein(s1, s2);
        
        // Calculer la similarité en fonction de la longueur maximale
        int maxLength = Math.max(s1.length(), s2.length());
        return 1.0 - ((double) distance / maxLength);
    }

    private int distanceLevenshtein(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= s2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }
} 