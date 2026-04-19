package org.example.tp_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * API REST pour les Projets - compatible avec le frontend Angular.
 * URL : /api/projects/*
 * JSON fields : id, name, description, status
 */
@WebServlet("/api/projects/*")
public class ProjetApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        setCorsHeaders(response);

        String pathInfo = request.getPathInfo();
        int id = Integer.parseInt(pathInfo.substring(1));
        ProjetDAO.supprimer(id);
        response.getWriter().print("{\"message\":\"Projet supprime\"}");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(200);
    }

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
