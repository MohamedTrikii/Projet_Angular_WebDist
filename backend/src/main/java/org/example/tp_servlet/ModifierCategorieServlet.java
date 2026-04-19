package org.example.tp_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet pour modifier une catégorie.
 * GET : affiche le formulaire d'édition.
 * POST : applique les modifications.
 */
@WebServlet("/modifier-categorie")
public class ModifierCategorieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("ref"));
        Categorie c = CategorieDAO.get(id);
        request.setAttribute("categorie", c);
        request.getRequestDispatcher("edit-categorie.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Categorie c = CategorieDAO.get(id);
        c.setNom(request.getParameter("nom"));
        c.setDescription(request.getParameter("description"));
        CategorieDAO.modifier(c);
        response.sendRedirect("liste-categories.jsp");
    }
}
