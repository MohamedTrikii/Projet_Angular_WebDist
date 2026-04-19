package org.example.tp_servlet;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO statique pour gérer les opérations CRUD sur les projets.
 * Stockage en mémoire (liste statique).
 */
public class ProjetDAO {
    private static List<Projet> projets = new ArrayList<>();
    static int compteur = 0;

    /** Ajouter un projet */
    public static void ajouter(Projet p) {
        projets.add(p);
        compteur++;
    }

    /** Récupérer tous les projets */
    public static List<Projet> findAll() {
        return projets;
    }

    /** Supprimer un projet par son id */
    public static void supprimer(int id) {
        projets.removeIf(p -> p.getId() == id);
    }

    /** Récupérer un projet par son id */
    public static Projet get(int id) {
        for (Projet p : projets) {
            if (p.getId() == id) return p;
        }
        return null;
    }
}
