package org.example.tp_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * API REST pour les Categories - compatible avec le frontend Angular.
 * URL : /api/categories/*
 * JSON fields : id, name
 */
@WebServlet("/api/categories/*")
public class CategorieApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        setCorsHeaders(response);

        String body = JsonHelper.readBody(request);
        Categorie c = new Categorie();
        c.setId(CategorieDAO.compteur);
        c.setNom(JsonHelper.getField(body, "name"));
        CategorieDAO.ajouter(c);

        response.setStatus(201);
        response.getWriter().print(toJson(c));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        setCorsHeaders(response);

        String pathInfo = request.getPathInfo();
        int id = Integer.parseInt(pathInfo.substring(1));
        Categorie c = CategorieDAO.get(id);

        if (c != null) {
            String body = JsonHelper.readBody(request);
            c.setNom(JsonHelper.getField(body, "name"));
            response.getWriter().print(toJson(c));
        } else {
            response.setStatus(404);
            response.getWriter().print("{\"error\":\"Categorie non trouvee\"}");
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
        CategorieDAO.supprimer(id);
        response.getWriter().print("{\"message\":\"Categorie supprimee\"}");
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
