package org.example.tp_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet pour ajouter un utilisateur (POST).
 * Récupère les paramètres du formulaire et crée un nouvel utilisateur.
 */
@WebServlet("/ajouter-utilisateur")
public class AjoutUtilisateurServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération des paramètres du formulaire
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String role = request.getParameter("role");

        // Création et ajout de l'utilisateur
        Utilisateur u = new Utilisateur(UtilisateurDAO.compteur, nom, prenom, email, role);
        UtilisateurDAO.ajouter(u);

        // Redirection vers la liste des utilisateurs
        response.sendRedirect("liste-utilisateurs.jsp");
    }
}
