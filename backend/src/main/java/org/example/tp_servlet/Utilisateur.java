package org.example.tp_servlet;

/**
 * Entité Utilisateur représentant un utilisateur du système.
 * Peut avoir le rôle "admin" ou "employe".
 */
public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String role; // "admin" ou "employe"

    public Utilisateur() {
    }

    public Utilisateur(int id, String nom, String prenom, String email, String role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Champs supplémentaires pour l'API Angular
    private String password;
    private String category;

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
