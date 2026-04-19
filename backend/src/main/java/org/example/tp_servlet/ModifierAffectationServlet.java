package org.example.tp_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet pour modifier une affectation.
 * GET : affiche le formulaire d'édition.
 * POST : applique les modifications.
 */
@WebServlet("/modifier-affectation")
public class ModifierAffectationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("ref"));
        Affectation a = AffectationDAO.get(id);
        request.setAttribute("affectation", a);
        // Passer les utilisateurs et projets pour les selects
        request.setAttribute("utilisateurs", UtilisateurDAO.findAll());
        request.setAttribute("projets", ProjetDAO.findAll());
        request.getRequestDispatcher("edit-affectation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Affectation a = AffectationDAO.get(id);
        a.setUtilisateurId(Integer.parseInt(request.getParameter("utilisateurId")));
        a.setProjetId(Integer.parseInt(request.getParameter("projetId")));
        a.setDateDebut(request.getParameter("dateDebut"));
        a.setDateFin(request.getParameter("dateFin"));
        AffectationDAO.modifier(a);
        response.sendRedirect("liste-affectations.jsp");
    }
}
