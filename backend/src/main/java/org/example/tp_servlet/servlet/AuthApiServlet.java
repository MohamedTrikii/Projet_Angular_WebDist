package org.example.tp_servlet.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.tp_servlet.DAO.UtilisateurDAO;
import org.example.tp_servlet.JsonHelper;
import org.example.tp_servlet.Model.Utilisateur;

import java.io.IOException;

/**
 * API REST pour l'authentification.
 * POST /api/auth/login - Connexion avec username + password, retourne un token et un rôle.
 * Pour le TP : accepte admin/admin comme identifiants par défaut.
 */
@WebServlet("/api/auth/*")
public class AuthApiServlet extends HttpServlet {

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

            // Vérifier aussi dans la liste des utilisateurs (par email)
            for (Utilisateur u : UtilisateurDAO.findAll()) {
                if (u.getEmail() != null && u.getEmail().equals(username) &&
                    u.getPassword() != null && u.getPassword().equals(password)) {
                    String role = u.getRole() != null ? u.getRole().toUpperCase() : "USER";
                    response.getWriter().print(
                        String.format("{\"token\":\"user-token-%d-%d\",\"role\":\"%s\"}",
                            u.getId(), System.currentTimeMillis(), JsonHelper.escape(role)));
                    return;
                }
            }

            // Identifiants invalides
            response.setStatus(401);
            response.getWriter().print("{\"error\":\"Identifiants invalides\"}");
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
