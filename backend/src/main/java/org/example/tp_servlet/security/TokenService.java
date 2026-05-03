package org.example.tp_servlet.security;

import org.example.tp_servlet.Model.Utilisateur;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    private final Map<String, String> tokenToUsername = new ConcurrentHashMap<>();

    public String createToken(Utilisateur utilisateur) {
        String token = UUID.randomUUID().toString();
        tokenToUsername.put(token, utilisateur.getEmail());
        return token;
    }

    public Optional<String> getUsernameFromToken(String token) {
        return Optional.ofNullable(tokenToUsername.get(token));
    }
}
