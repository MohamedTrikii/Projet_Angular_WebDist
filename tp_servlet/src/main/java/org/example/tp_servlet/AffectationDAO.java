package org.example.tp_servlet;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO statique pour gérer les opérations CRUD sur les affectations.
 * Stockage en mémoire (liste statique).
 */
public class AffectationDAO {
    private static List<Affectation> affectations = new ArrayList<>();
    static int compteur = 0;

    /** Ajouter une affectation */
    public static void ajouter(Affectation a) {
        affectations.add(a);
        compteur++;
    }

    /** Récupérer toutes les affectations */
    public static List<Affectation> findAll() {
        return affectations;
    }

    /** Supprimer une affectation par son id */
    public static void supprimer(int id) {
        affectations.removeIf(a -> a.getId() == id);
    }

    /** Récupérer une affectation par son id */
    public static Affectation get(int id) {
        for (Affectation a : affectations) {
            if (a.getId() == id) return a;
        }
        return null;
    }

    /** Récupérer toutes les affectations d'un projet donné */
    public static List<Affectation> findByProjet(int projetId) {
        List<Affectation> result = new ArrayList<>();
        for (Affectation a : affectations) {
            if (a.getProjetId() == projetId) result.add(a);
        }
        return result;
    }

    /** Récupérer toutes les affectations d'un utilisateur donné */
    public static List<Affectation> findByUtilisateur(int utilisateurId) {
        List<Affectation> result = new ArrayList<>();
        for (Affectation a : affectations) {
            if (a.getUtilisateurId() == utilisateurId) result.add(a);
        }
        return result;
    }
}
