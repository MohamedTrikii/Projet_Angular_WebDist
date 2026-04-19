package org.example.tp_servlet;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO statique pour gérer les opérations CRUD sur les catégories.
 * Stockage en mémoire (liste statique).
 */
public class CategorieDAO {
    private static List<Categorie> categories = new ArrayList<>();
    static int compteur = 0;

    /** Ajouter une catégorie */
    public static void ajouter(Categorie c) {
        categories.add(c);
        compteur++;
    }

    /** Récupérer toutes les catégories */
    public static List<Categorie> findAll() {
        return categories;
    }

    /** Supprimer une catégorie par son id */
    public static void supprimer(int id) {
        categories.removeIf(c -> c.getId() == id);
    }

    /** Récupérer une catégorie par son id */
    public static Categorie get(int id) {
        for (Categorie c : categories) {
            if (c.getId() == id) return c;
        }
        return null;
    }
}
