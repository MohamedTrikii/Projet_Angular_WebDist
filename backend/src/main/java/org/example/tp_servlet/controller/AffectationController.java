package org.example.tp_servlet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.tp_servlet.DAO.AffectationDAO;
import org.example.tp_servlet.DAO.ProjetDAO;
import org.example.tp_servlet.DAO.UtilisateurDAO;
import org.example.tp_servlet.Model.Affectation;
import org.example.tp_servlet.JsonHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Consolidated controller for Affectation (Assignments).
 * Handles:
 * - API REST endpoints (/api/affectations/*, /affectations/api/*)
 * - Form submissions (/affectations*, /ajouter-affectation, /modifier-affectation, /delete-affectation)
 */
@WebServlet({"/affectations/*", "/api/affectations/*", "/ajouter-affectation", "/modifier-affectation", "/delete-affectation"})
public class AffectationController extends HttpServlet {

    // ===================== GET =====================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String path = request.getPathInfo();

        // ===== API REST =====
        if (path != null && path.startsWith("/api")) {
            handleApiGet(request, response);
            return;
        }

        // ===== Form-based requests =====
        if (requestURI.contains("/modifier-affectation")) {
            handleFormModify(request, response);
            return;
        } else if (requestURI.contains("/delete-affectation")) {
            handleFormDelete(request, response);
            return;
        }

        // ===== JSP Actions (from /affectations?action=...) =====
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Affectation a = AffectationDAO.get(id);

            request.setAttribute("affectation", a);
            request.setAttribute("utilisateurs", UtilisateurDAO.findAll());
            request.setAttribute("projets", ProjetDAO.findAll());

            request.getRequestDispatcher("edit-affectation.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            AffectationDAO.supprimer(id);
            response.sendRedirect("liste-affectations.jsp");

        } else {
            response.sendError(400, "Action invalide");
        }
    }

    // ===================== POST =====================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String path = request.getPathInfo();

        // ===== API =====
        if (path != null && path.startsWith("/api")) {
            handleApiPost(request, response);
            return;
        }

        // ===== Form add request =====
        if (requestURI.contains("/ajouter-affectation")) {
            handleFormAdd(request, response);
            return;
        }

        // ===== Form update request =====
        if (requestURI.contains("/modifier-affectation")) {
            handleFormUpdate(request, response);
            return;
        }

        // ===== JSP form submit (from /affectations) =====
        int id = Integer.parseInt(request.getParameter("id"));
        Affectation a = AffectationDAO.get(id);

        a.setUtilisateurId(Integer.parseInt(request.getParameter("utilisateurId")));
        a.setProjetId(Integer.parseInt(request.getParameter("projetId")));
        a.setDateDebut(request.getParameter("dateDebut"));
        a.setDateFin(request.getParameter("dateFin"));

        AffectationDAO.modifier(a);

        response.sendRedirect("liste-affectations.jsp");
    }

    // ===================== PUT =====================
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        setCorsHeaders(response);

        int id = extractId(request);
        Affectation a = AffectationDAO.get(id);

        if (a != null) {
            String body = JsonHelper.readBody(request);

            a.setUtilisateurId(JsonHelper.getInt(body, "userId", a.getUtilisateurId()));
            a.setProjetId(JsonHelper.getInt(body, "projectId", a.getProjetId()));
            a.setDateDebut(JsonHelper.getField(body, "start"));
            a.setDateFin(JsonHelper.getField(body, "end"));

            AffectationDAO.modifier(a);
            response.getWriter().print(toJson(a));
        } else {
            response.setStatus(404);
            response.getWriter().print("{\"error\":\"Not found\"}");
        }
    }

    // ===================== DELETE =====================
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        setCorsHeaders(response);

        int id = extractId(request);
        AffectationDAO.supprimer(id);

        response.getWriter().print("{\"message\":\"Deleted\"}");
    }

    // ===================== OPTIONS =====================
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        setCorsHeaders(response);
        response.setStatus(200);
    }

    // =====================================================
    // ================= API HANDLERS =======================
    // =====================================================

    private void handleFormAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int utilisateurId = Integer.parseInt(request.getParameter("utilisateurId"));
        int projetId = Integer.parseInt(request.getParameter("projetId"));
        String dateDebut = request.getParameter("dateDebut");
        String dateFin = request.getParameter("dateFin");

        Affectation a = new Affectation(0, utilisateurId, projetId, dateDebut, dateFin);
        AffectationDAO.ajouter(a);

        response.sendRedirect("liste-affectations.jsp");
    }

    private void handleFormModify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("ref"));
        Affectation a = AffectationDAO.get(id);
        request.setAttribute("affectation", a);
        request.setAttribute("utilisateurs", UtilisateurDAO.findAll());
        request.setAttribute("projets", ProjetDAO.findAll());
        request.getRequestDispatcher("edit-affectation.jsp").forward(request, response);
    }

    private void handleFormUpdate(HttpServletRequest request, HttpServletResponse response)
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

    private void handleFormDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("ref"));
        AffectationDAO.supprimer(id);
        response.sendRedirect("liste-affectations.jsp");
    }

    private void handleApiGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        setCorsHeaders(response);

        PrintWriter out = response.getWriter();
        String path = request.getPathInfo();

        if (path.equals("/api") || path.equals("/api/")) {
            List<Affectation> list = AffectationDAO.findAll();
            out.print(toJsonArray(list));
        } else {
            int id = extractId(request);
            Affectation a = AffectationDAO.get(id);

            if (a != null) {
                out.print(toJson(a));
            } else {
                response.setStatus(404);
                out.print("{\"error\":\"Not found\"}");
            }
        }
    }

    private void handleApiPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        setCorsHeaders(response);

        String body = JsonHelper.readBody(request);

        Affectation a = new Affectation();
        a.setUtilisateurId(JsonHelper.getInt(body, "userId", 0));
        a.setProjetId(JsonHelper.getInt(body, "projectId", 0));
        a.setDateDebut(JsonHelper.getField(body, "start"));
        a.setDateFin(JsonHelper.getField(body, "end"));

        AffectationDAO.ajouter(a);

        response.setStatus(201);
        response.getWriter().print(toJson(a));
    }

    // =====================================================
    // ================= UTILS ==============================
    // =====================================================

    private int extractId(HttpServletRequest request) {
        String path = request.getPathInfo(); // /api/5
        return Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    private String toJson(Affectation a) {
        return String.format(
                "{\"id\":%d,\"userId\":%d,\"projectId\":%d,\"start\":\"%s\",\"end\":\"%s\"}",
                a.getId(),
                a.getUtilisateurId(),
                a.getProjetId(),
                JsonHelper.escape(a.getDateDebut()),
                JsonHelper.escape(a.getDateFin())
        );
    }

    private String toJsonArray(List<Affectation> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(toJson(list.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }
}