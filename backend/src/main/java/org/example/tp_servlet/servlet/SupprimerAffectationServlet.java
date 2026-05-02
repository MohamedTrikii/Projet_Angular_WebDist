package org.example.tp_servlet.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.tp_servlet.DAO.AffectationDAO;

import java.io.IOException;

/**
 * Servlet pour supprimer une affectation (GET).
 */
@WebServlet("/delete-affectation")
public class SupprimerAffectationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("ref"));
        AffectationDAO.supprimer(id);
        response.sendRedirect("liste-affectations.jsp");
    }
}
