package org.example.tp_servlet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.tp_servlet.DAO.CategorieDAO;
import org.example.tp_servlet.JsonHelper;
import org.example.tp_servlet.Model.Categorie;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Consolidated controller for Categorie (Categories).
 * Handles:
 * - API REST endpoints (/api/categories/*)
 * - Form submissions (/ajouter-categorie, /modifier-categorie, /supprimer-categorie)
 */
@WebServlet({"/api/categories/*", "/ajouter-categorie", "/modifier-categorie", "/supprimer-categorie", "/delete-categorie"})
public class CategorieController extends HttpServlet {

    // ===================== GET =====================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.contains("/api/categories/")) {
            handleApiGet(request, response);
        } else if (requestURI.contains("/modifier-categorie")) {
            handleFormModify(request, response);
        } else if (requestURI.contains("/delete-categorie")) {
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

        if (requestURI.contains("/api/categories/")) {
            handleApiPost(request, response);
        } else if (requestURI.contains("/ajouter-categorie")) {
            handleFormAdd(request, response);
        } else if (requestURI.contains("/modifier-categorie")) {
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
        Categorie c = CategorieDAO.get(id);

        if (c != null) {
            String body = JsonHelper.readBody(request);
            c.setNom(JsonHelper.getField(body, "name"));
            c.setDescription(JsonHelper.getField(body, "description"));
            CategorieDAO.modifier(c);
            response.getWriter().print(toJson(c));
        } else {
            response.setStatus(404);
            response.getWriter().print("{\"error\":\"Categorie non trouvee\"}");
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
        CategorieDAO.supprimer(id);
        response.getWriter().print("{\"message\":\"Categorie supprimee\"}");
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
            List<Categorie> categories = CategorieDAO.findAll();
            out.print(toJsonArray(categories));
        } else {
            int id = Integer.parseInt(pathInfo.substring(1));
            Categorie c = CategorieDAO.get(id);
            if (c != null) {
                out.print(toJson(c));
            } else {
                response.setStatus(404);
                out.print("{\"error\":\"Categorie non trouvee\"}");
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
        Categorie c = new Categorie();
        c.setNom(JsonHelper.getField(body, "name"));
        CategorieDAO.ajouter(c);

        response.setStatus(201);
        response.getWriter().print(toJson(c));
    }

    // =====================================================
    // ================= FORM HANDLERS =======================
    // =====================================================

    private void handleFormAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");

        Categorie c = new Categorie(0, nom, description);
        CategorieDAO.ajouter(c);

        response.sendRedirect("liste-categories.jsp");
    }

    private void handleFormModify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("ref"));
        Categorie c = CategorieDAO.get(id);

        request.setAttribute("categorie", c);
        request.getRequestDispatcher("edit-categorie.jsp").forward(request, response);
    }

    private void handleFormUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Categorie c = CategorieDAO.get(id);

        c.setNom(request.getParameter("nom"));
        c.setDescription(request.getParameter("description"));

        CategorieDAO.modifier(c);

        response.sendRedirect("liste-categories.jsp");
    }

    private void handleFormDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("ref"));
        CategorieDAO.supprimer(id);
        response.sendRedirect("liste-categories.jsp");
    }

    // =====================================================
    // ================= UTILS ==============================
    // =====================================================

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    private String toJson(Categorie c) {
        return String.format("{\"id\":\"%d\",\"name\":\"%s\"}",
            c.getId(), JsonHelper.escape(c.getNom()));
    }

    private String toJsonArray(List<Categorie> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(toJson(list.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }
}
