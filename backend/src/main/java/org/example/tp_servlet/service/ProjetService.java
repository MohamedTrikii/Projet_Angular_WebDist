package org.example.tp_servlet.service;

import org.example.tp_servlet.Model.Projet;
import org.example.tp_servlet.repository.ProjetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetService {
    private final ProjetRepository projetRepository;

    public ProjetService(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    public Projet save(Projet projet) {
        return projetRepository.save(projet);
    }

    public List<Projet> findAll() {
        return projetRepository.findAll();
    }

    public Optional<Projet> findById(int id) {
        return projetRepository.findById(id);
    }

    public void deleteById(int id) {
        projetRepository.deleteById(id);
    }
}
