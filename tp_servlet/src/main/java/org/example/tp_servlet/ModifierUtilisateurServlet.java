package org.example.tp_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet pour modifier un utilisateur.
 * GET : affiche le formulaire d'édition pré-rempli.
 * POST : applique les modifications et redirige vers la liste.
 */
@WebServlet("/modifier-utilisateur")
public class ModifierUtilisateurServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupérer l'utilisateur à modifier
        int id = Integer.parseInt(request.getParameter("ref"));
        Utilisateur u = UtilisateurDAO.get(id);

        // Passer l'utilisateur à la JSP d'édition
        request.setAttribute("utilisateur", u);
        request.getRequestDispatcher("edit-utilisateur.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupérer l'utilisateur existant et mettre à jour ses champs
        int id = Integer.parseInt(request.getParameter("id"));
        Utilisateur u = UtilisateurDAO.get(id);

        u.setNom(request.getParameter("nom"));
        u.setPrenom(request.getParameter("prenom"));
        u.setEmail(request.getParameter("email"));
        u.setRole(request.getParameter("role"));

        // Redirection vers la liste
        response.sendRedirect("liste-utilisateurs.jsp");
    }
}
