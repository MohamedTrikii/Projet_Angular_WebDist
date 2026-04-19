package org.example.tp_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * API REST pour les Utilisateurs - compatible avec le frontend Angular.
 * URL : /api/users/*
 * JSON fields : id, name, email, password, role, category
 */
@WebServlet("/api/users/*")
public class UtilisateurApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        setCorsHeaders(response);

        String body = JsonHelper.readBody(request);
        Utilisateur u = new Utilisateur();
        u.setId(UtilisateurDAO.compteur);
        u.setNom(JsonHelper.getField(body, "name"));
        u.setEmail(JsonHelper.getField(body, "email"));
        u.setPassword(JsonHelper.getField(body, "password"));
        u.setRole(JsonHelper.getField(body, "role"));
        u.setCategory(JsonHelper.getField(body, "category"));
        UtilisateurDAO.ajouter(u);

        response.setStatus(201);
        response.getWriter().print(toJson(u));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
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
            response.getWriter().print(toJson(u));
        } else {
            response.setStatus(404);
            response.getWriter().print("{\"error\":\"Utilisateur non trouve\"}");
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
        UtilisateurDAO.supprimer(id);
        response.getWriter().print("{\"message\":\"Utilisateur supprime\"}");
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
