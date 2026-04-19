package org.example.tp_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet pour ajouter un projet (POST).
 * Le projet est lié à une catégorie via categorieId.
 */
@WebServlet("/ajouter-projet")
public class AjoutProjetServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        int categorieId = Integer.parseInt(request.getParameter("categorieId"));

        Projet p = new Projet(0, nom, description, categorieId);
        ProjetDAO.ajouter(p);

        response.sendRedirect("liste-projets.jsp");
    }
}
