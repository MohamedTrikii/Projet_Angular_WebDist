package org.example.tp_servlet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;
    private int categorieId;
    private String status;

    public Projet() {}

    public Projet(int id, String nom, String description, int categorieId) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.categorieId = categorieId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getCategorieId() { return categorieId; }
    public void setCategorieId(int categorieId) { this.categorieId = categorieId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
