package org.example.tp_servlet.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.tp_servlet.DAO.CategorieDAO;
import org.example.tp_servlet.DAO.ProjetDAO;
import org.example.tp_servlet.Model.Projet;

import java.io.IOException;

/**
 * Servlet pour modifier un projet.
 * GET : affiche le formulaire d'édition.
 * POST : applique les modifications.
 */
@WebServlet("/modifier-projet")
public class ModifierProjetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("ref"));
        Projet p = ProjetDAO.get(id);
        request.setAttribute("projet", p);
        // Passer aussi les catégories pour le select
        request.setAttribute("categories", CategorieDAO.findAll());
        request.getRequestDispatcher("edit-projet.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Projet p = ProjetDAO.get(id);
        p.setNom(request.getParameter("nom"));
        p.setDescription(request.getParameter("description"));
        p.setCategorieId(Integer.parseInt(request.getParameter("categorieId")));
        ProjetDAO.modifier(p);
        response.sendRedirect("liste-projets.jsp");
    }
}
