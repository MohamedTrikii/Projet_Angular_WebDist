package org.example.tp_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/modifier")
public class ModifierServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("personne",
                PersonneDAO.get(Integer.parseInt(request.getParameter("ref"))));

        request.getRequestDispatcher("edit.jsp")
                .forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Personne p = PersonneDAO.get(
                Integer.parseInt(request.getParameter("id"))
        );

        p.setNom(request.getParameter("nom"));
        p.setPrenom(request.getParameter("prenom"));
        p.setEmail(request.getParameter("email"));
        PersonneDAO.modifier(p);
        response.sendRedirect("index.jsp");
    }
}