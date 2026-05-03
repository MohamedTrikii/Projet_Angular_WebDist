package org.example.tp_servlet.service;

import org.example.tp_servlet.Model.Categorie;
import org.example.tp_servlet.repository.CategorieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {
    private final CategorieRepository categorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public Categorie save(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    public Optional<Categorie> findById(int id) {
        return categorieRepository.findById(id);
    }

    public void deleteById(int id) {
        categorieRepository.deleteById(id);
    }
}
