package org.example.tp_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet pour ajouter une catégorie (POST).
 */
@WebServlet("/ajouter-categorie")
public class AjoutCategorieServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");

        Categorie c = new Categorie(0, nom, description);
        CategorieDAO.ajouter(c);

        response.sendRedirect("liste-categories.jsp");
    }
}
