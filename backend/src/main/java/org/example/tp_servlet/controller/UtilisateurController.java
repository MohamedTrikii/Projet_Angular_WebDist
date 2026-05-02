package org.example.tp_servlet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.tp_servlet.DAO.UtilisateurDAO;
import org.example.tp_servlet.JsonHelper;
import org.example.tp_servlet.Model.Utilisateur;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Consolidated controller for Utilisateur (Users).
 * Handles:
 * - API REST endpoints (/api/users/*)
 * - Form submissions (/ajouter-utilisateur, /modifier-utilisateur, /supprimer-utilisateur)
 */
@WebServlet({"/api/users/*", "/ajouter-utilisateur", "/modifier-utilisateur", "/supprimer-utilisateur", "/delete-utilisateur"})
public class UtilisateurController extends HttpServlet {

    // ===================== GET =====================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // API endpoint check
        if (requestURI.contains("/api/users/")) {
            handleApiGet(request, response);
        } else if (requestURI.contains("/modifier-utilisateur")) {
            handleFormModify(request, response);
        } else if (requestURI.contains("/delete-utilisateur")) {
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

        if (requestURI.contains("/api/users/")) {
            handleApiPost(request, response);
        } else if (requestURI.contains("/ajouter-utilisateur")) {
            handleFormAdd(request, response);
        } else if (requestURI.contains("/modifier-utilisateur")) {
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
        Utilisateur u = UtilisateurDAO.get(id);

        if (u != null) {
            String body = JsonHelper.readBody(request);
            u.setNom(JsonHelper.getField(body, "name"));
            u.setEmail(JsonHelper.getField(body, "email"));
            String pwd = JsonHelper.getField(body, "password");
            if (pwd != null && !pwd.isEmpty()) u.setPassword(pwd);
            u.setRole(JsonHelper.getField(body, "role"));
            u.setCategory(JsonHelper.getField(body, "category"));
            UtilisateurDAO.modifier(u);
            response.getWriter().print(toJson(u));
        } else {
            response.setStatus(404);
            response.getWriter().print("{\"error\":\"Utilisateur non trouve\"}");
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
        UtilisateurDAO.supprimer(id);
        response.getWriter().print("{\"message\":\"Utilisateur supprime\"}");
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
            List<Utilisateur> utilisateurs = UtilisateurDAO.findAll();
            out.print(toJsonArray(utilisateurs));
        } else {
            int id = Integer.parseInt(pathInfo.substring(1));
            Utilisateur u = UtilisateurDAO.get(id);
            if (u != null) {
                out.print(toJson(u));
            } else {
                response.setStatus(404);
                out.print("{\"error\":\"Utilisateur non trouve\"}");
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
        Utilisateur u = new Utilisateur();
        u.setNom(JsonHelper.getField(body, "name"));
        u.setEmail(JsonHelper.getField(body, "email"));
        u.setPassword(JsonHelper.getField(body, "password"));
        u.setRole(JsonHelper.getField(body, "role"));
        u.setCategory(JsonHelper.getField(body, "category"));
        UtilisateurDAO.ajouter(u);

        response.setStatus(201);
        response.getWriter().print(toJson(u));
    }

    // =====================================================
    // ================= FORM HANDLERS =======================
    // =====================================================

    private void handleFormAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String role = request.getParameter("role");

        Utilisateur u = new Utilisateur(0, nom, prenom, email, role);
        UtilisateurDAO.ajouter(u);

        response.sendRedirect("liste-utilisateurs.jsp");
    }

    private void handleFormModify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("ref"));
        Utilisateur u = UtilisateurDAO.get(id);

        request.setAttribute("utilisateur", u);
        request.getRequestDispatcher("edit-utilisateur.jsp").forward(request, response);
    }

    private void handleFormUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Utilisateur u = UtilisateurDAO.get(id);

        u.setNom(request.getParameter("nom"));
        u.setPrenom(request.getParameter("prenom"));
        u.setEmail(request.getParameter("email"));
        u.setRole(request.getParameter("role"));

        UtilisateurDAO.modifier(u);

        response.sendRedirect("liste-utilisateurs.jsp");
    }

    private void handleFormDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("ref"));
        UtilisateurDAO.supprimer(id);
        response.sendRedirect("liste-utilisateurs.jsp");
    }

    // =====================================================
    // ================= UTILS ==============================
    // =====================================================

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    private String toJson(Utilisateur u) {
        return String.format(
            "{\"id\":\"%d\",\"name\":\"%s\",\"email\":\"%s\",\"password\":\"%s\",\"role\":\"%s\",\"category\":\"%s\"}",
            u.getId(), JsonHelper.escape(u.getNom()), JsonHelper.escape(u.getEmail()),
            JsonHelper.escape(u.getPassword()), JsonHelper.escape(u.getRole()),
            JsonHelper.escape(u.getCategory()));
    }

    private String toJsonArray(List<Utilisateur> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(toJson(list.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }
}
