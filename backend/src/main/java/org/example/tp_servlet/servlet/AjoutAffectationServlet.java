package org.example.tp_servlet.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.tp_servlet.DAO.AffectationDAO;
import org.example.tp_servlet.Model.Affectation;

import java.io.IOException;

/**
 * Servlet pour ajouter une affectation (POST).
 * Lie un employé à un projet avec des dates de début et fin.
 */
@WebServlet("/ajouter-affectation")
public class AjoutAffectationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int utilisateurId = Integer.parseInt(request.getParameter("utilisateurId"));
        int projetId = Integer.parseInt(request.getParameter("projetId"));
        String dateDebut = request.getParameter("dateDebut");
        String dateFin = request.getParameter("dateFin");

        Affectation a = new Affectation(0, utilisateurId, projetId, dateDebut, dateFin);
        AffectationDAO.ajouter(a);

        response.sendRedirect("liste-affectations.jsp");
    }
}
