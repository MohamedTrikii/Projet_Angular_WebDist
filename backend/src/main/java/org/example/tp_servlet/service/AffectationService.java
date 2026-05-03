package org.example.tp_servlet.service;

import org.example.tp_servlet.Model.Affectation;
import org.example.tp_servlet.repository.AffectationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AffectationService {
    private final AffectationRepository affectationRepository;

    public AffectationService(AffectationRepository affectationRepository) {
        this.affectationRepository = affectationRepository;
    }

    public Affectation save(Affectation affectation) {
        return affectationRepository.save(affectation);
    }

    public List<Affectation> findAll() {
        return affectationRepository.findAll();
    }

    public Optional<Affectation> findById(int id) {
        return affectationRepository.findById(id);
    }

    public void deleteById(int id) {
        affectationRepository.deleteById(id);
    }
}
