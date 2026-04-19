package org.example.tp_servlet;

/**
 * Entité Projet représentant un projet de l'entreprise.
 * Chaque projet est lié à une catégorie via categorieId.
 */
public class Projet {
    private int id;
    private String nom;
    private String description;
    private int categorieId; // clé étrangère vers Categorie

    public Projet() {
    }

    public Projet(int id, String nom, String description, int categorieId) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.categorieId = categorieId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public int getCategorieId() {
        return categorieId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    // Champ supplémentaire pour l'API Angular
    private String status;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
