package org.example.tp_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet pour supprimer une catégorie (GET).
 */
@WebServlet("/delete-categorie")
public class SupprimerCategorieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("ref"));
        CategorieDAO.supprimer(id);
        response.sendRedirect("liste-categories.jsp");
    }
}
