package org.example.tp_servlet;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO statique pour gérer les opérations CRUD sur les utilisateurs.
 * Stockage en mémoire (liste statique).
 */
public class UtilisateurDAO {
    private static List<Utilisateur> utilisateurs = new ArrayList<>();
    static int compteur = 0;

    /** Ajouter un utilisateur à la liste */
    public static void ajouter(Utilisateur u) {
        utilisateurs.add(u);
        compteur++;
    }

    /** Récupérer tous les utilisateurs */
    public static List<Utilisateur> findAll() {
        return utilisateurs;
    }

    /** Supprimer un utilisateur par son id */
    public static void supprimer(int id) {
        utilisateurs.removeIf(u -> u.getId() == id);
    }

    /** Récupérer un utilisateur par son id */
    public static Utilisateur get(int id) {
        for (Utilisateur u : utilisateurs) {
            if (u.getId() == id) return u;
        }
        return null;
    }
}
