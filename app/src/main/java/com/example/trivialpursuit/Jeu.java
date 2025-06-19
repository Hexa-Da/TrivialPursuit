package com.example.trivialpursuit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class Jeu {
    private Map<String, List<Question>> questions;
    private List<Question> questionsUtilisees;
    private List<Joueur> joueurs;
    private int joueurActuel;
    private static final String[] CATEGORIES = {
        "Géographie", "Histoire", "Divertissement",
        "Art et Littérature", "Sciences et Nature", "Sports et Loisirs"
    };

    public Jeu() {
        this.questions = new HashMap<>();
        this.questionsUtilisees = new ArrayList<>();
        this.joueurs = new ArrayList<>();
        this.joueurActuel = 0;
        initialiserQuestions();
    }

    private void initialiserQuestions() {
        // Géographie
        questions.put("Géographie", Arrays.asList(
            new Question("Quelle est la capitale de la France ?", "Paris", "Géographie"),
            new Question("Quel est le plus grand océan du monde ?", "Océan Pacifique", "Géographie"),
            new Question("Quelle est la plus haute montagne du monde ?", "Mont Everest", "Géographie"),
            new Question("Quel est le plus long fleuve du monde ?", "Nil", "Géographie"),
            new Question("Quelle est la plus grande île du monde ?", "Groenland", "Géographie"),
            new Question("Quel est le plus grand désert du monde ?", "Sahara", "Géographie"),
            new Question("Quelle est la capitale du Japon ?", "Tokyo", "Géographie"),
            new Question("Quel est le plus grand pays du monde en superficie ?", "Russie", "Géographie"),
            new Question("Quelle est la capitale de l'Australie ?", "Canberra", "Géographie"),
            new Question("Quel est le plus grand lac d'eau douce du monde ?", "Lac Supérieur", "Géographie"),
            new Question("Quelle est la capitale du Brésil ?", "Brasilia", "Géographie"),
            new Question("Quel est le plus grand canyon du monde ?", "Grand Canyon", "Géographie"),
            new Question("Quelle est la capitale de l'Inde ?", "New Delhi", "Géographie"),
            new Question("Quel est le plus grand volcan actif du monde ?", "Mauna Loa", "Géographie"),
            new Question("Quelle est la capitale de l'Égypte ?", "Le Caire", "Géographie"),
            new Question("Quel est le plus grand glacier du monde ?", "Glacier Lambert", "Géographie"),
            new Question("Quelle est la capitale de la Chine ?", "Pékin", "Géographie"),
            new Question("Quel est le plus grand delta du monde ?", "Delta du Gange", "Géographie"),
            new Question("Quelle est la capitale de l'Afrique du Sud ?", "Pretoria", "Géographie"),
            new Question("Quel est le plus grand récif corallien du monde ?", "Grande Barrière de Corail", "Géographie")
        ));

        // Histoire
        questions.put("Histoire", Arrays.asList(
            new Question("En quelle année a eu lieu la Révolution française ?", "1789", "Histoire"),
            new Question("Qui était le premier empereur romain ?", "Auguste", "Histoire"),
            new Question("Quand a eu lieu la Première Guerre mondiale ?", "1914", "Histoire"),
            new Question("Qui a découvert l'Amérique en 1492 ?", "Christophe Colomb", "Histoire"),
            new Question("Quand a eu lieu la chute du mur de Berlin ?", "1989", "Histoire"),
            new Question("Qui était le pharaon qui a fait construire les plus grandes pyramides ?", "Khéops", "Histoire"),
            new Question("En quelle année a eu lieu la bataille de Waterloo ?", "1815", "Histoire"),
            new Question("Qui était le premier président des États-Unis ?", "George Washington", "Histoire"),
            new Question("Quand a eu lieu la Révolution industrielle ?", "1760", "Histoire"),
            new Question("Qui était le roi de France pendant la Révolution ?", "Louis XVI", "Histoire"),
            new Question("En quelle année a eu lieu la découverte de la pénicilline ?", "1928", "Histoire"),
            new Question("Qui était le premier empereur de Chine ?", "Qin Shi Huang", "Histoire"),
            new Question("Quand a eu lieu la construction du premier ordinateur ?", "1946", "Histoire"),
            new Question("Qui était le premier roi de France ?", "Clovis", "Histoire"),
            new Question("En quelle année a eu lieu la première mission Apollo sur la Lune ?", "1969", "Histoire"),
            new Question("Qui était le premier empereur du Japon ?", "Jimmu", "Histoire"),
            new Question("Quand a eu lieu la découverte de l'Amérique par les Vikings ?", "1000", "Histoire"),
            new Question("Qui était le premier tsar de Russie ?", "Ivan le Terrible", "Histoire"),
            new Question("En quelle année a eu lieu la première guerre mondiale ?", "1914", "Histoire"),
            new Question("Qui était le premier calife de l'islam ?", "Abou Bakr", "Histoire")
        ));

        // Divertissement
        questions.put("Divertissement", Arrays.asList(
            new Question("Qui a créé le personnage de Mickey Mouse ?", "Walt Disney", "Divertissement"),
            new Question("Quel est le film le plus vu au monde ?", "Avatar", "Divertissement"),
            new Question("Qui a composé la musique de Star Wars ?", "John Williams", "Divertissement"),
            new Question("Quel est le premier film d'animation de Disney ?", "Blanche-Neige", "Divertissement"),
            new Question("Qui a créé le personnage de Superman ?", "Jerry Siegel", "Divertissement"),
            new Question("Quel est le plus grand parc d'attractions du monde ?", "Magic Kingdom", "Divertissement"),
            new Question("Qui a créé le personnage de Spider-Man ?", "Stan Lee", "Divertissement"),
            new Question("Quel est le film le plus long de l'histoire ?", "La Fin de Evangelion", "Divertissement"),
            new Question("Qui a composé la musique de Titanic ?", "James Horner", "Divertissement"),
            new Question("Quel est le premier film en couleur ?", "Becky Sharp", "Divertissement"),
            new Question("Qui a créé le personnage de Batman ?", "Bob Kane", "Divertissement"),
            new Question("Quel est le plus grand studio de cinéma du monde ?", "Universal Studios", "Divertissement"),
            new Question("Qui a composé la musique de Harry Potter ?", "John Williams", "Divertissement"),
            new Question("Quel est le premier film parlant ?", "Le Chanteur de Jazz", "Divertissement"),
            new Question("Qui a créé le personnage de Wonder Woman ?", "William Moulton Marston", "Divertissement"),
            new Question("Quel est le plus grand festival de cinéma du monde ?", "Festival de Cannes", "Divertissement"),
            new Question("Qui a composé la musique de Pirates des Caraïbes ?", "Hans Zimmer", "Divertissement"),
            new Question("Quel est le premier film en 3D ?", "L'Homme au masque de cire", "Divertissement"),
            new Question("Qui a créé le personnage de Iron Man ?", "Stan Lee", "Divertissement"),
            new Question("Quel est le plus grand musée du cinéma du monde ?", "Academy Museum of Motion Pictures", "Divertissement")
        ));

        // Art et Littérature
        questions.put("Art et Littérature", Arrays.asList(
            new Question("Qui a peint la Joconde ?", "Léonard de Vinci", "Art et Littérature"),
            new Question("Qui a écrit 'Les Misérables' ?", "Victor Hugo", "Art et Littérature"),
            new Question("Qui a peint la Nuit étoilée ?", "Vincent van Gogh", "Art et Littérature"),
            new Question("Qui a écrit 'Le Petit Prince' ?", "Saint-Exupéry", "Art et Littérature"),
            new Question("Qui a sculpté la Vénus de Milo ?", "Alexandros d'Antioche", "Art et Littérature"),
            new Question("Qui a écrit 'Don Quichotte' ?", "Miguel de Cervantes", "Art et Littérature"),
            new Question("Qui a peint la Cène ?", "Léonard de Vinci", "Art et Littérature"),
            new Question("Qui a écrit 'Hamlet' ?", "William Shakespeare", "Art et Littérature"),
            new Question("Qui a sculpté le Penseur ?", "Auguste Rodin", "Art et Littérature"),
            new Question("Qui a écrit 'Les Fables' ?", "Jean de La Fontaine", "Art et Littérature"),
            new Question("Qui a peint Guernica ?", "Pablo Picasso", "Art et Littérature"),
            new Question("Qui a écrit 'Crime et Châtiment' ?", "Fiodor Dostoïevski", "Art et Littérature"),
            new Question("Qui a sculpté David ?", "Michel-Ange", "Art et Littérature"),
            new Question("Qui a écrit 'Madame Bovary' ?", "Gustave Flaubert", "Art et Littérature"),
            new Question("Qui a peint les Nymphéas ?", "Claude Monet", "Art et Littérature"),
            new Question("Qui a écrit 'L'Étranger' ?", "Albert Camus", "Art et Littérature"),
            new Question("Qui a sculpté la Victoire de Samothrace ?", "Pythocritos", "Art et Littérature"),
            new Question("Qui a écrit 'Les Raisins de la colère' ?", "John Steinbeck", "Art et Littérature"),
            new Question("Qui a peint la Création d'Adam ?", "Michel-Ange", "Art et Littérature"),
            new Question("Qui a écrit 'Cent ans de solitude' ?", "Gabriel García Márquez", "Art et Littérature")
        ));

        // Sciences et Nature
        questions.put("Sciences et Nature", Arrays.asList(
            new Question("Quelle est la planète la plus proche du Soleil ?", "Mercure", "Sciences et Nature"),
            new Question("Quel est l'élément chimique le plus abondant dans l'univers ?", "Hydrogène", "Sciences et Nature"),
            new Question("Quel est le plus grand mammifère terrestre ?", "Éléphant d'Afrique", "Sciences et Nature"),
            new Question("Quelle est la vitesse de la lumière ?", "300000 km/s", "Sciences et Nature"),
            new Question("Quel est le plus grand poisson du monde ?", "Requin baleine", "Sciences et Nature"),
            new Question("Quelle est la formule de l'eau ?", "H2O", "Sciences et Nature"),
            new Question("Quel est le plus grand oiseau du monde ?", "Autruche", "Sciences et Nature"),
            new Question("Quelle est la température d'ébullition de l'eau ?", "100°C", "Sciences et Nature"),
            new Question("Quel est le plus grand reptile du monde ?", "Crocodile marin", "Sciences et Nature"),
            new Question("Quelle est la formule de l'acide sulfurique ?", "H2SO4", "Sciences et Nature"),
            new Question("Quel est le plus grand insecte du monde ?", "Phasme géant", "Sciences et Nature"),
            new Question("Quelle est la constante de gravitation universelle ?", "6.67 x 10^-11", "Sciences et Nature"),
            new Question("Quel est le plus grand arbre du monde ?", "Séquoia géant", "Sciences et Nature"),
            new Question("Quelle est la forme de l'ADN ?", "Double hélice", "Sciences et Nature"),
            new Question("Quel est le plus grand mammifère marin ?", "Baleine bleue", "Sciences et Nature"),
            new Question("Quelle est la constante de Planck ?", "6.63 x 10^-34", "Sciences et Nature"),
            new Question("Quel est le plus grand dinosaure connu ?", "Argentinosaurus", "Sciences et Nature"),
            new Question("Quelle est la formule de la relativité générale ?", "E=mc²", "Sciences et Nature"),
            new Question("Quel est le plus grand volcan du système solaire ?", "Olympus Mons", "Sciences et Nature"),
            new Question("Quelle est la formule de la photosynthèse ?", "6CO2 + 6H2O → C6H12O6 + 6O2", "Sciences et Nature")
        ));

        // Sports et Loisirs
        questions.put("Sports et Loisirs", Arrays.asList(
            new Question("Quel pays a remporté le plus de Coupes du Monde de football ?", "Brésil", "Sports et Loisirs"),
            new Question("Quel est le sport le plus pratiqué au monde ?", "Football", "Sports et Loisirs"),
            new Question("Qui a remporté le plus de titres de Grand Chelem en tennis ?", "Margaret Court", "Sports et Loisirs"),
            new Question("Quel est le plus grand stade du monde ?", "Stade de Pyongyang", "Sports et Loisirs"),
            new Question("Qui a remporté le plus de médailles d'or olympiques ?", "Michael Phelps", "Sports et Loisirs"),
            new Question("Quel est le sport national du Japon ?", "Sumo", "Sports et Loisirs"),
            new Question("Qui a remporté le plus de Tours de France ?", "Eddy Merckx, Jacques Anquetil, Bernard Hinault ou Miguel Indurain", "Sports et Loisirs"),
            new Question("Quel est le plus grand circuit de Formule 1 ?", "Circuit de Spa-Francorchamps", "Sports et Loisirs"),
            new Question("Quel joueur a remporté le plus de titres NBA ?", "Bill Russell", "Sports et Loisirs"),
            new Question("Quel est le plus grand tournoi de tennis ?", "Wimbledon", "Sports et Loisirs"),
            new Question("Qui a remporté le plus de titres de champion du monde de boxe ?", "Manny Pacquiao", "Sports et Loisirs"),
            new Question("Quel est le plus grand marathon du monde ?", "Marathon de New York", "Sports et Loisirs"),
            new Question("Qui a remporté le plus de titres de Grand Chelem en golf ?", "Jack Nicklaus", "Sports et Loisirs"),
            new Question("Quel est le plus grand stade de baseball ?", "Yankee Stadium", "Sports et Loisirs"),
            new Question("Qui a remporté le plus de titres de champion du monde d'échecs ?", "Garry Kasparov", "Sports et Loisirs"),
            new Question("Quel est le plus grand tournoi de poker ?", "World Series of Poker", "Sports et Loisirs"),
            new Question("Quel est le record du monde du 100m en athlétisme ?", "9.58 secondes", "Sports et Loisirs"),
            new Question("Quel est le plus grand stade de cricket ?", "Melbourne Cricket Ground", "Sports et Loisirs"),
            new Question("Qui a remporté le plus de titres de champion du monde de rugby ?", "Nouvelle-Zélande", "Sports et Loisirs"),
            new Question("Quel est le plus grand tournoi de badminton ?", "All England Open", "Sports et Loisirs")
        ));
    }

    public void ajouterJoueur(Joueur joueur) {
        joueurs.add(joueur);
    }

    public Question getQuestionAleatoire(String categorie) {
        List<Question> questionsCategorie = questions.get(categorie);
        if (questionsCategorie == null) return null;
        
        List<Question> questionsDisponibles = new ArrayList<>();
        for (Question q : questionsCategorie) {
            if (!questionsUtilisees.contains(q)) {
                questionsDisponibles.add(q);
            }
        }
        
        if (questionsDisponibles.isEmpty()) {
            // Si toutes les questions de la catégorie ont été utilisées, réinitialiser
            for (Question q : questionsCategorie) {
                questionsUtilisees.remove(q);
                questionsDisponibles.add(q);
            }
        }
        
        if (questionsDisponibles.isEmpty()) return null;
        
        Random random = new Random();
        Question question = questionsDisponibles.get(random.nextInt(questionsDisponibles.size()));
        questionsUtilisees.add(question);
        return question;
    }

    public Joueur getJoueurActuel() {
        return joueurs.get(joueurActuel);
    }

    public void setJoueurActuel(int index) {
        if (index >= 0 && index < joueurs.size()) {
            joueurActuel = index;
        }
    }

    public void passerAuJoueurSuivant() {
        joueurActuel = (joueurActuel + 1) % joueurs.size();
    }

    public String[] getCategories() {
        return CATEGORIES;
    }

    public boolean verifierReponse(Question question, String reponse) {
        return question.verifierReponse(reponse);
    }

    public int getNombreJoueurs() {
        return joueurs.size();
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public boolean toutesQuestionsUtilisees() {
        // Calculer le nombre total de questions dans toutes les catégories
        int nombreTotalQuestions = 0;
        for (List<Question> questionsCategorie : questions.values()) {
            nombreTotalQuestions += questionsCategorie.size();
        }
        return questionsUtilisees.size() == nombreTotalQuestions;
    }

    public boolean categorieDejaGagnee(String categorie, Joueur joueur) {
        return joueur.getCategoriesGagnees().contains(categorie);
    }

    public String[] getCategoriesDisponibles(Joueur joueur) {
        List<String> categoriesDisponibles = new ArrayList<>();
        for (String categorie : CATEGORIES) {
            if (!categorieDejaGagnee(categorie, joueur)) {
                categoriesDisponibles.add(categorie);
            }
        }
        return categoriesDisponibles.toArray(new String[0]);
    }

    public boolean joueurAGagne(Joueur joueur) {
        return joueur.getCategoriesGagnees().size() == 6;
    }
} 