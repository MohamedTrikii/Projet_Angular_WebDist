package org.example.tp_servlet.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.tp_servlet.DAO.UtilisateurDAO;

import java.io.IOException;

/**
 * Servlet pour supprimer un utilisateur (GET).
 * Récupère l'id depuis le paramètre "ref" et supprime l'utilisateur.
 */
@WebServlet("/delete-utilisateur")
public class SupprimerUtilisateurServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération de l'id et suppression
        int id = Integer.parseInt(request.getParameter("ref"));
        UtilisateurDAO.supprimer(id);

        // Redirection vers la liste des utilisateurs
        response.sendRedirect("liste-utilisateurs.jsp");
    }
}
