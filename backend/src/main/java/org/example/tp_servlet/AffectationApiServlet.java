package org.example.tp_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * API REST pour les Affectations - compatible avec le frontend Angular.
 * URL : /api/affectations/*
 * JSON fields : id, userId, projectId, start, end
 */
@WebServlet("/api/affectations/*")
public class AffectationApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        setCorsHeaders(response);

        PrintWriter out = response.getWriter();
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Affectation> affectations = AffectationDAO.findAll();
            out.print(toJsonArray(affectations));
        } else {
            int id = Integer.parseInt(pathInfo.substring(1));
            Affectation a = AffectationDAO.get(id);
            if (a != null) {
                out.print(toJson(a));
            } else {
                response.setStatus(404);
                out.print("{\"error\":\"Affectation non trouvee\"}");
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
        Affectation a = new Affectation();
        a.setUtilisateurId(JsonHelper.getInt(body, "userId", 0));
        a.setProjetId(JsonHelper.getInt(body, "projectId", 0));
        a.setDateDebut(JsonHelper.getField(body, "start"));
        a.setDateFin(JsonHelper.getField(body, "end"));
        AffectationDAO.ajouter(a);

        response.setStatus(201);
        response.getWriter().print(toJson(a));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        setCorsHeaders(response);

        String pathInfo = request.getPathInfo();
        int id = Integer.parseInt(pathInfo.substring(1));
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
            response.getWriter().print("{\"error\":\"Affectation non trouvee\"}");
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
        AffectationDAO.supprimer(id);
        response.getWriter().print("{\"message\":\"Affectation supprimee\"}");
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

    private String toJson(Affectation a) {
        return String.format(
            "{\"id\":\"%d\",\"userId\":\"%d\",\"projectId\":\"%d\",\"start\":\"%s\",\"end\":\"%s\"}",
            a.getId(), a.getUtilisateurId(), a.getProjetId(),
            JsonHelper.escape(a.getDateDebut()), JsonHelper.escape(a.getDateFin()));
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
