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

        // Si la réponse contient des alternatives (séparées par des virgules ou "ou")
        if (reponseNormalisee.contains(",") || reponseNormalisee.contains(" ou ")) {
            return verifierReponsesMultiples(reponseNormalisee, reponseJoueurNormalisee);
        }
        
        // Vérifier les cas spéciaux avant la vérification normale
        if (verifierCasSpeciaux(reponseNormalisee, reponseJoueurNormalisee)) {
            return true;
        }
        
        // Vérifier si les réponses sont identiques après normalisation
        if (reponseNormalisee.equals(reponseJoueurNormalisee)) {
            return true;
        }

        // Calculer la similarité entre les réponses
        double similarite = calculerSimilarite(reponseNormalisee, reponseJoueurNormalisee);
        
        // Ajuster le seuil de similarité en fonction de la longueur de la réponse
        double seuilSimilarite;
        if (reponseNormalisee.length() <= 5) {
            seuilSimilarite = 1.0;
        } else if (reponseNormalisee.length() <= 10) {
            seuilSimilarite = 0.9;
        } else {
            seuilSimilarite = 0.85;
        }
        
        return similarite >= seuilSimilarite;
    }

    private boolean verifierReponsesMultiples(String reponseNormalisee, String reponseJoueurNormalisee) {
        // Séparer les réponses alternatives
        String[] alternatives = reponseNormalisee.split(",|\\s+ou\\s+");
        
        // Pour chaque alternative
        for (String alternative : alternatives) {
            String alternativeNormalisee = alternative.trim();
            
            // Vérifier si la réponse du joueur correspond à cette alternative
            if (verifierCasSpeciaux(alternativeNormalisee, reponseJoueurNormalisee)) {
                return true;
            }
            
            // Vérifier la similarité directe
            if (alternativeNormalisee.equals(reponseJoueurNormalisee)) {
                return true;
            }
            
            // Calculer la similarité
            double similarite = calculerSimilarite(alternativeNormalisee, reponseJoueurNormalisee);
            double seuilSimilarite = alternativeNormalisee.length() <= 5 ? 1.0 : 
                                   alternativeNormalisee.length() <= 10 ? 0.9 : 0.85;
            
            if (similarite >= seuilSimilarite) {
                return true;
            }
        }
        
        return false;
    }

    private boolean verifierCasSpeciaux(String reponseNormalisee, String reponseJoueurNormalisee) {
        // Cas 1: Versions courtes des noms
        if (verifierVersionsCourtes(reponseNormalisee, reponseJoueurNormalisee)) {
            return true;
        }

        // Cas 2: Chiffres romains
        if (verifierChiffresRomains(reponseNormalisee, reponseJoueurNormalisee)) {
            return true;
        }

        // Cas 3: Unités de mesure
        if (verifierUnitesDeMesure(reponseNormalisee, reponseJoueurNormalisee)) {
            return true;
        }

        // Cas 4: Acronymes
        if (verifierAcronymes(reponseNormalisee, reponseJoueurNormalisee)) {
            return true;
        }

        // Cas 5: Notation scientifique
        if (verifierNotationScientifique(reponseNormalisee, reponseJoueurNormalisee)) {
            return true;
        }

        return false;
    }

    private boolean verifierVersionsCourtes(String reponseNormalisee, String reponseJoueurNormalisee) {
        // Liste des équivalences nom court -> nom complet
        String[][] equivalences = {
            // Lieux et monuments
            {"spa", "circuit de spa francorchamps"},
            {"everest", "mont everest"},
            {"nil", "le nil"},
            {"sahara", "le sahara"},
            {"grand canyon", "le grand canyon"},
            {"mauna loa", "le mauna loa"},
            {"glacier lambert", "le glacier lambert"},
            {"pekin", "beijing"},
            {"le caire", "cairo"},
            {"moscou", "moscow"},
            {"pacific", "ocean pacifique"},
            {"pacifique", "ocean pacifique"},
            {"keops", "kheops"},
            
            // Personnes
            {"de vinci", "leonard de vinci"},
            {"van gogh", "vincent van gogh"},
            {"saint exupery", "antoine de saint exupery"},
            {"cervantes", "miguel de cervantes"},
            {"shakespeare", "william shakespeare"},
            {"la fontaine", "jean de la fontaine"},
            {"picasso", "pablo picasso"},
            {"dostoievski", "fiodor dostoievski"},
            {"michel ange", "michel-ange"},
            {"flaubert", "gustave flaubert"},
            {"monet", "claude monet"},
            {"camus", "albert camus"},
            {"steinbeck", "john steinbeck"},
            {"marquez", "gabriel garcia marquez"},
            
            // Sports et compétitions
            {"wimbledon", "tournoi de wimbledon"},
            {"roland garros", "internationaux de france"},
            {"tour de france", "le tour de france"},
            {"jo", "jeux olympiques"},
            {"f1", "formule 1"},
            {"schumacher", "michael schumacher"},
            {"hamilton", "lewis hamilton"},
            {"merckx", "eddy merckx"},
            {"hinault", "bernard hinault"},
            {"anquetil", "jacques anquetil"},
            {"indurain", "miguel indurain"},
            
            // Sciences
            {"adn", "acide desoxyribonucleique"},
            {"e mc2", "e = mc2"},
            {"e=mc2", "e = mc2"},
            {"e mc²", "e = mc²"},
            {"e=mc²", "e = mc²"},
            {"emc2", "e = mc2"},
            {"emc²", "e = mc²"},
            {"mc2", "e = mc2"},
            {"mc²", "e = mc²"}
        };

        // Vérifier si la réponse du joueur est une version courte valide
        for (String[] paire : equivalences) {
            String versionCourte = paire[0];
            String versionLongue = paire[1];

            // Si la réponse officielle est la version longue et que le joueur donne la version courte
            if (reponseNormalisee.equals(versionLongue) && reponseJoueurNormalisee.equals(versionCourte)) {
                return true;
            }

            // Si la réponse officielle est la version courte et que le joueur donne la version longue
            if (reponseNormalisee.equals(versionCourte) && reponseJoueurNormalisee.equals(versionLongue)) {
                return true;
            }

            // Vérifier si la réponse du joueur est contenue dans la réponse officielle
            // mais seulement si elle fait au moins 3 caractères pour éviter les faux positifs
            if (reponseJoueurNormalisee.length() >= 3 && 
                (reponseNormalisee.contains(reponseJoueurNormalisee) || 
                 reponseJoueurNormalisee.contains(reponseNormalisee))) {
                
                // Vérifier que la correspondance est sur un mot complet
                String[] motsReponse = reponseNormalisee.split("\\s+");
                String[] motsJoueur = reponseJoueurNormalisee.split("\\s+");
                
                for (String motReponse : motsReponse) {
                    for (String motJoueur : motsJoueur) {
                        if (motReponse.length() >= 3 && motJoueur.length() >= 3) {
                            if (motReponse.contains(motJoueur) || motJoueur.contains(motReponse)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean verifierChiffresRomains(String reponseNormalisee, String reponseJoueurNormalisee) {
        // Extraire les parties non-numériques (noms, texte, etc.)
        String[] partsReponse = reponseNormalisee.split("\\s+");
        String[] partsJoueur = reponseJoueurNormalisee.split("\\s+");

        if (partsReponse.length != partsJoueur.length) return false;

        for (int i = 0; i < partsReponse.length; i++) {
            String partReponse = partsReponse[i];
            String partJoueur = partsJoueur[i];

            // Si les parties sont identiques, continuer
            if (partReponse.equals(partJoueur)) continue;

            // Essayer de convertir les chiffres romains
            try {
                // Vérifier si l'une des parties contient un chiffre romain
                if (estChiffreRomain(partReponse) || estChiffreRomain(partJoueur)) {
                    int nombreReponse = convertirEnNombreArabe(partReponse);
                    int nombreJoueur = convertirEnNombreArabe(partJoueur);
                    
                    if (nombreReponse != nombreJoueur) return false;
                } else {
                    // Si ce ne sont pas des chiffres romains et qu'ils sont différents
                    if (!partReponse.equals(partJoueur)) return false;
                }
            } catch (IllegalArgumentException e) {
                // Si la conversion échoue, comparer les chaînes directement
                if (!partReponse.equals(partJoueur)) return false;
            }
        }
        return true;
    }

    private boolean estChiffreRomain(String texte) {
        // Vérifier si c'est un chiffre arabe
        if (texte.matches("\\d+")) return true;
        // Vérifier si c'est un chiffre romain
        return texte.toUpperCase().matches("^[IVXLCDM]+$");
    }

    private int convertirEnNombreArabe(String texte) {
        // Si c'est déjà un nombre arabe
        if (texte.matches("\\d+")) {
            return Integer.parseInt(texte);
        }

        // Conversion des chiffres romains
        texte = texte.toUpperCase();
        int resultat = 0;
        int precedent = 0;

        for (int i = texte.length() - 1; i >= 0; i--) {
            int actuel = valeurChiffreRomain(texte.charAt(i));
            if (actuel >= precedent) {
                resultat += actuel;
            } else {
                resultat -= actuel;
            }
            precedent = actuel;
        }
        return resultat;
    }

    private int valeurChiffreRomain(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: throw new IllegalArgumentException("Caractère invalide: " + c);
        }
    }

    private boolean verifierUnitesDeMesure(String reponseNormalisee, String reponseJoueurNormalisee) {
        // Liste des unités courantes et leurs variations
        String[][] unites = {
            {"°c", "c", "degres", "degres celsius"},
            {"°f", "f", "degres fahrenheit"},
            {"km/h", "kmh", "kilometres par heure"},
            {"km/s", "kms", "kilometres par seconde"},
            {"m/s", "ms", "metres par seconde"},
            {"m/h", "mh", "metres par heure"}
        };

        // Extraire les nombres des réponses
        String nombreReponse = reponseNormalisee.replaceAll("[^0-9.]", "");
        String nombreJoueur = reponseJoueurNormalisee.replaceAll("[^0-9.]", "");

        // Si les nombres sont identiques
        if (!nombreReponse.isEmpty() && nombreReponse.equals(nombreJoueur)) {
            // Pour chaque groupe d'unités
            for (String[] groupeUnites : unites) {
                boolean reponseContientUnite = false;
                boolean joueurContientUnite = false;

                // Vérifier si la réponse et la réponse du joueur contiennent des unités du même groupe
                for (String unite : groupeUnites) {
                    if (reponseNormalisee.contains(unite)) reponseContientUnite = true;
                    if (reponseJoueurNormalisee.contains(unite)) joueurContientUnite = true;
                }

                // Si l'une contient une unité mais pas l'autre, on accepte quand même
                if (reponseContientUnite || joueurContientUnite) {
                    return true;
                }
            }
        }

        // Vérifier les conversions entre unités de vitesse
        if (verifierConversionVitesse(reponseNormalisee, reponseJoueurNormalisee)) {
            return true;
        }

        return false;
    }

    private boolean verifierConversionVitesse(String reponseNormalisee, String reponseJoueurNormalisee) {
        try {
            // Extraire les valeurs numériques
            double valeurReponse = extraireValeurNumerique(reponseNormalisee);
            double valeurJoueur = extraireValeurNumerique(reponseJoueurNormalisee);

            // Identifier les unités
            String uniteReponse = identifierUniteVitesse(reponseNormalisee);
            String uniteJoueur = identifierUniteVitesse(reponseJoueurNormalisee);

            // Si les unités sont différentes, convertir pour comparer
            if (!uniteReponse.equals(uniteJoueur)) {
                double valeurConvertie = convertirVitesse(valeurJoueur, uniteJoueur, uniteReponse);
                // Tolérance de 1% pour les erreurs d'arrondi
                return Math.abs(valeurReponse - valeurConvertie) / valeurReponse < 0.01;
            }

            // Si les unités sont identiques, comparer directement
            return Math.abs(valeurReponse - valeurJoueur) / Math.max(valeurReponse, valeurJoueur) < 0.01;

        } catch (Exception e) {
            return false;
        }
    }

    private double extraireValeurNumerique(String texte) {
        // Extraire le premier nombre trouvé
        String nombre = texte.replaceAll("[^0-9.]", "");
        if (nombre.isEmpty()) return 0;
        
        // Gérer les notations scientifiques
        if (nombre.contains("e") || nombre.contains("E")) {
            return Double.parseDouble(nombre);
        }
        
        return Double.parseDouble(nombre);
    }

    private String identifierUniteVitesse(String texte) {
        if (texte.contains("km/s") || texte.contains("kms")) return "km/s";
        if (texte.contains("km/h") || texte.contains("kmh")) return "km/h";
        if (texte.contains("m/s") || texte.contains("ms")) return "m/s";
        if (texte.contains("m/h") || texte.contains("mh")) return "m/h";
        return "km/s"; // Par défaut
    }

    private double convertirVitesse(double valeur, String uniteSource, String uniteCible) {
        // Convertir d'abord en m/s
        double valeurMS = 0;
        switch (uniteSource) {
            case "km/s":
                valeurMS = valeur * 1000;
                break;
            case "km/h":
                valeurMS = valeur / 3.6;
                break;
            case "m/s":
                valeurMS = valeur;
                break;
            case "m/h":
                valeurMS = valeur / 3600;
                break;
        }

        // Puis convertir vers l'unité cible
        switch (uniteCible) {
            case "km/s":
                return valeurMS / 1000;
            case "km/h":
                return valeurMS * 3.6;
            case "m/s":
                return valeurMS;
            case "m/h":
                return valeurMS * 3600;
            default:
                return valeurMS;
        }
    }

    private boolean verifierAcronymes(String reponseNormalisee, String reponseJoueurNormalisee) {
        // Liste des acronymes connus et leurs formes complètes
        String[][] acronymes = {
            {"world series of poker", "wsop"},
            {"most valuable player", "mvp"},
            {"new york", "ny"},
            {"united states", "us", "usa"},
            {"united kingdom", "uk"},
            {"los angeles", "la"}
        };

        // Pour chaque paire d'acronyme
        for (String[] paire : acronymes) {
            boolean reponseEstDansPaire = false;
            boolean joueurEstDansPaire = false;

            // Vérifier si les réponses correspondent à l'une des formes
            for (String forme : paire) {
                if (reponseNormalisee.equals(forme)) reponseEstDansPaire = true;
                if (reponseJoueurNormalisee.equals(forme)) joueurEstDansPaire = true;
            }

            // Si les deux réponses sont des formes valides du même acronyme
            if (reponseEstDansPaire && joueurEstDansPaire) {
                return true;
            }
        }

        return false;
    }

    private boolean verifierNotationScientifique(String reponseNormalisee, String reponseJoueurNormalisee) {
        try {
            // Convertir les notations scientifiques en nombres
            double valeurReponse = convertirEnNombre(reponseNormalisee);
            double valeurJoueur = convertirEnNombre(reponseJoueurNormalisee);

            // Comparer les valeurs avec une tolérance pour les erreurs d'arrondi
            return Math.abs(valeurReponse - valeurJoueur) / Math.max(Math.abs(valeurReponse), Math.abs(valeurJoueur)) < 0.001;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double convertirEnNombre(String texte) {
        // Supprimer tous les espaces
        texte = texte.replaceAll("\\s+", "");
        
        // Gérer la notation avec *10^
        if (texte.contains("*10^") || texte.contains("x10^")) {
            String[] parts = texte.split("\\*10\\^|x10\\^");
            if (parts.length == 2) {
                double base = Double.parseDouble(parts[0]);
                int exposant = Integer.parseInt(parts[1]);
                return base * Math.pow(10, exposant);
            }
        }
        
        // Gérer la notation E
        if (texte.contains("e") || texte.contains("E")) {
            return Double.parseDouble(texte);
        }
        
        // Essayer de convertir directement
        return Double.parseDouble(texte.replaceAll("[^0-9.-]", ""));
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
        
        // Supprimer les caractères spéciaux et la ponctuation, mais garder certains caractères importants
        texte = texte.replaceAll("[^a-z0-9\\s°/\\-.*^]", "");
        
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