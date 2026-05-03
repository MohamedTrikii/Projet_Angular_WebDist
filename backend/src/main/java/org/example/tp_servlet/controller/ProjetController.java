package org.example.tp_servlet.controller;

import org.example.tp_servlet.Model.Projet;
import org.example.tp_servlet.service.ProjetService;
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
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjetController {

    private final ProjetService projetService;

    public ProjetController(ProjetService projetService) {
        this.projetService = projetService;
    }

    @GetMapping
    public List<Projet> findAll() {
        return projetService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projet> findById(@PathVariable int id) {
        return projetService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Projet> create(@RequestBody Projet projet) {
        projet.setId(0);
        Projet saved = projetService.save(projet);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Projet> update(@PathVariable int id, @RequestBody Projet projet) {
        return projetService.findById(id)
                .map(existing -> {
                    existing.setNom(projet.getNom());
                    existing.setDescription(projet.getDescription());
                    existing.setStatus(projet.getStatus());
                    existing.setCategorieId(projet.getCategorieId());
                    return ResponseEntity.ok(projetService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        projetService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
