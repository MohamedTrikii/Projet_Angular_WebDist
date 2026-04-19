package org.example.tp_servlet;

/**
 * Entité Affectation représentant l'affectation d'un employé à un projet.
 * Contient les dates de début et de fin d'affectation.
 */
public class Affectation {
    private int id;
    private int utilisateurId; // clé étrangère vers Utilisateur (employé)
    private int projetId;      // clé étrangère vers Projet
    private String dateDebut;
    private String dateFin;

    public Affectation() {
    }

    public Affectation(int id, int utilisateurId, int projetId, String dateDebut, String dateFin) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.projetId = projetId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public int getProjetId() {
        return projetId;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public void setProjetId(int projetId) {
        this.projetId = projetId;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }
}
