package org.example.tp_servlet;

import java.util.ArrayList;
import java.util.List;

public class PersonneDAO {

    private static List<Personne> personnes = new ArrayList<>();

    static int compteur =0;

    public static void ajouter(Personne p) {
        personnes.add(p);
        compteur++;
    }



    public static List<Personne> findAll() {
        return personnes;
    }

    public static void supprimer(int id) {

        personnes.removeIf(p -> p.getId() == id);

    }

    public static Personne get(int id) {

        for (Personne p : personnes) {

            if (p.getId() == id) {
                return p;
            }

        }

        return null;
    }
}