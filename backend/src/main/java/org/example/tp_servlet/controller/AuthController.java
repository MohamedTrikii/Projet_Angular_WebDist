package org.example.tp_servlet.controller;

import org.example.tp_servlet.security.TokenService;
import org.example.tp_servlet.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UtilisateurService utilisateurService;
    private final TokenService tokenService;

    public AuthController(UtilisateurService utilisateurService, TokenService tokenService) {
        this.utilisateurService = utilisateurService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if ("admin".equals(username) && "admin".equals(password)) {
            return ResponseEntity.ok(Map.of(
                    "token", "admin-token-" + System.currentTimeMillis(),
                    "role", "ADMIN"
            ));
        }

        return utilisateurService.findByEmail(username)
                .filter(u -> password != null && password.equals(u.getPassword()))
                .map(u -> {
                    String token = tokenService.createToken(u);
                    String role = u.getRole() != null ? u.getRole().toUpperCase() : "USER";
                    return ResponseEntity.ok(Map.of(
                            "token", token,
                            "role", role
                    ));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Identifiants invalides")));
    }
}
