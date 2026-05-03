package org.example.tp_servlet.controller;

import org.example.tp_servlet.Model.Utilisateur;
import org.example.tp_servlet.service.UtilisateurService;
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
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public List<Utilisateur> findAll() {
        return utilisateurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> findById(@PathVariable int id) {
        return utilisateurService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Utilisateur> create(@RequestBody Utilisateur utilisateur) {
        utilisateur.setId(0);
        Utilisateur saved = utilisateurService.save(utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> update(@PathVariable int id, @RequestBody Utilisateur utilisateur) {
        return utilisateurService.findById(id)
                .map(existing -> {
                    existing.setNom(utilisateur.getNom());
                    existing.setPrenom(utilisateur.getPrenom());
                    existing.setEmail(utilisateur.getEmail());
                    if (utilisateur.getPassword() != null && !utilisateur.getPassword().isEmpty()) {
                        existing.setPassword(utilisateur.getPassword());
                    }
                    existing.setRole(utilisateur.getRole());
                    existing.setCategory(utilisateur.getCategory());
                    return ResponseEntity.ok(utilisateurService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        utilisateurService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
