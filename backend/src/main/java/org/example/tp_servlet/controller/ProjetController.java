package org.example.tp_servlet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.tp_servlet.DAO.ProjetDAO;
import org.example.tp_servlet.JsonHelper;
import org.example.tp_servlet.Model.Projet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Consolidated controller for Projet (Projects).
 * Handles:
 * - API REST endpoints (/api/projects/*)
 * - Form submissions (/ajouter-projet, /modifier-projet, /supprimer-projet)
 */
@WebServlet({"/api/projects/*", "/ajouter-projet", "/modifier-projet", "/supprimer-projet", "/delete-projet"})
public class ProjetController extends HttpServlet {

    // ===================== GET =====================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.contains("/api/projects/")) {
            handleApiGet(request, response);
        } else if (requestURI.contains("/modifier-projet")) {
            handleFormModify(request, response);
        } else if (requestURI.contains("/delete-projet")) {
            handleFormDelete(request, response);
        } else {
            response.sendError(400, "Invalid GET request");
        }
    }

    // ===================== POST =====================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.contains("/api/projects/")) {
            handleApiPost(request, response);
        } else if (requestURI.contains("/ajouter-projet")) {
            handleFormAdd(request, response);
        } else if (requestURI.contains("/modifier-projet")) {
            handleFormUpdate(request, response);
        } else {
            response.sendError(400, "Invalid POST request");
        }
    }

    // ===================== PUT =====================
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        setCorsHeaders(response);

        String pathInfo = request.getPathInfo();
        int id = Integer.parseInt(pathInfo.substring(1));
        Projet p = ProjetDAO.get(id);

        if (p != null) {
            String body = JsonHelper.readBody(request);
            p.setNom(JsonHelper.getField(body, "name"));
            p.setDescription(JsonHelper.getField(body, "description"));
            p.setStatus(JsonHelper.getField(body, "status"));
            ProjetDAO.modifier(p);
            response.getWriter().print(toJson(p));
        } else {
            response.setStatus(404);
            response.getWriter().print("{\"error\":\"Projet non trouve\"}");
        }
    }

    // ===================== DELETE =====================
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        setCorsHeaders(response);

        String pathInfo = request.getPathInfo();
        int id = Integer.parseInt(pathInfo.substring(1));
        ProjetDAO.supprimer(id);
        response.getWriter().print("{\"message\":\"Projet supprime\"}");
    }

    // ===================== OPTIONS =====================
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(200);
    }

    // =====================================================
    // ================= API HANDLERS =======================
    // =====================================================

    private void handleApiGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        setCorsHeaders(response);

        PrintWriter out = response.getWriter();
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Projet> projets = ProjetDAO.findAll();
            out.print(toJsonArray(projets));
        } else {
            int id = Integer.parseInt(pathInfo.substring(1));
            Projet p = ProjetDAO.get(id);
            if (p != null) {
                out.print(toJson(p));
            } else {
                response.setStatus(404);
                out.print("{\"error\":\"Projet non trouve\"}");
            }
        }
        out.flush();
    }

    private void handleApiPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        setCorsHeaders(response);

        String body = JsonHelper.readBody(request);
        Projet p = new Projet();
        p.setNom(JsonHelper.getField(body, "name"));
        p.setDescription(JsonHelper.getField(body, "description"));
        p.setStatus(JsonHelper.getField(body, "status"));
        ProjetDAO.ajouter(p);

        response.setStatus(201);
        response.getWriter().print(toJson(p));
    }

    // =====================================================
    // ================= FORM HANDLERS =======================
    // =====================================================

    private void handleFormAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        int categorieId = Integer.parseInt(request.getParameter("categorieId"));

        Projet p = new Projet(0, nom, description, categorieId);
        ProjetDAO.ajouter(p);

        response.sendRedirect("liste-projets.jsp");
    }

    private void handleFormModify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("ref"));
        Projet p = ProjetDAO.get(id);

        request.setAttribute("projet", p);
        request.getRequestDispatcher("edit-projet.jsp").forward(request, response);
    }

    private void handleFormUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Projet p = ProjetDAO.get(id);

        p.setNom(request.getParameter("nom"));
        p.setDescription(request.getParameter("description"));
        p.setCategorieId(Integer.parseInt(request.getParameter("categorieId")));

        ProjetDAO.modifier(p);

        response.sendRedirect("liste-projets.jsp");
    }

    private void handleFormDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("ref"));
        ProjetDAO.supprimer(id);
        response.sendRedirect("liste-projets.jsp");
    }

    // =====================================================
    // ================= UTILS ==============================
    // =====================================================

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    private String toJson(Projet p) {
        return String.format(
            "{\"id\":\"%d\",\"name\":\"%s\",\"description\":\"%s\",\"status\":\"%s\"}",
            p.getId(), JsonHelper.escape(p.getNom()), JsonHelper.escape(p.getDescription()),
            JsonHelper.escape(p.getStatus()));
    }

    private String toJsonArray(List<Projet> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(toJson(list.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }
}
