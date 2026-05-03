package org.example.tp_servlet.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.tp_servlet.JsonHelper;
import org.example.tp_servlet.Model.Utilisateur;
import org.example.tp_servlet.security.TokenService;
import org.example.tp_servlet.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;

/**
 * API REST pour l'authentification.
 * POST /api/auth/login - Connexion avec username + password, retourne un token et un rôle.
 * Pour le TP : accepte admin/admin comme identifiants par défaut.
 */
@WebServlet("/api/auth/*")
public class AuthApiServlet extends HttpServlet {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private TokenService tokenService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        setCorsHeaders(response);

        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.equals("/login")) {
            String body = JsonHelper.readBody(request);
            String username = JsonHelper.getField(body, "username");
            String password = JsonHelper.getField(body, "password");

            // Vérifier les identifiants (admin/admin par défaut pour le TP)
            if ("admin".equals(username) && "admin".equals(password)) {
                response.getWriter().print("{\"token\":\"admin-token-" + System.currentTimeMillis() + "\",\"role\":\"ADMIN\"}");
                return;
            }

            boolean authenticated = false;
            if (utilisateurService.findByEmail(username).isPresent()) {
                Utilisateur u = utilisateurService.findByEmail(username).get();
                if (u.getPassword() != null && u.getPassword().equals(password)) {
                    String token = tokenService.createToken(u);
                    String role = u.getRole() != null ? u.getRole().toUpperCase() : "USER";
                    response.getWriter().print(
                            String.format("{\"token\":\"%s\",\"role\":\"%s\"}",
                                    JsonHelper.escape(token), JsonHelper.escape(role)));
                    authenticated = true;
                }
            }
            if (!authenticated) {
                response.setStatus(401);
                response.getWriter().print("{\"error\":\"Identifiants invalides\"}");
            }
        } else {
            response.setStatus(404);
            response.getWriter().print("{\"error\":\"Endpoint non trouvé\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(200);
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}
