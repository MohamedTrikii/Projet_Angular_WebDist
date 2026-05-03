package org.example.tp_servlet.security;

import org.example.tp_servlet.Model.Utilisateur;
import org.example.tp_servlet.service.UtilisateurService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UtilisateurService utilisateurService;

    public UserDetailsServiceImpl(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));

        String role = utilisateur.getRole() != null ? utilisateur.getRole().toUpperCase() : "USER";
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

        return User.withUsername(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                .authorities(Collections.singleton(authority))
                .build();
    }
}
