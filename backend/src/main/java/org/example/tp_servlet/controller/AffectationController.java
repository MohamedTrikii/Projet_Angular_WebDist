package org.example.tp_servlet.controller;

// Spring REST controller for affectations
import org.example.tp_servlet.Model.Affectation;
import org.example.tp_servlet.service.AffectationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/affectations")
@CrossOrigin(origins = "http://localhost:4200")
public class AffectationController {

    private final AffectationService affectationService;

    public AffectationController(AffectationService affectationService) {
        this.affectationService = affectationService;
    }

    @GetMapping
    public List<Affectation> findAll() {
        return affectationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Affectation> findById(@PathVariable int id) {
        return affectationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Affectation> create(@RequestBody Affectation affectation) {
        affectation.setId(0);
        Affectation saved = affectationService.save(affectation);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Affectation> update(@PathVariable int id, @RequestBody Affectation affectation) {
        return affectationService.findById(id)
                .map(existing -> {
                    existing.setUtilisateurId(affectation.getUtilisateurId());
                    existing.setProjetId(affectation.getProjetId());
                    existing.setDateDebut(affectation.getDateDebut());
                    existing.setDateFin(affectation.getDateFin());
                    return ResponseEntity.ok(affectationService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        affectationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
