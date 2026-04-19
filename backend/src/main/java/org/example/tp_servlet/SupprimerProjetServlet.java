package org.example.tp_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet pour supprimer un projet (GET).
 */
@WebServlet("/delete-projet")
public class SupprimerProjetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("ref"));
        ProjetDAO.supprimer(id);
        response.sendRedirect("liste-projets.jsp");
    }
}
