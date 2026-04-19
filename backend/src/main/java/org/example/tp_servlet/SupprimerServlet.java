package org.example.tp_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/delete")
public class SupprimerServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PersonneDAO.supprimer(Integer.parseInt(request.getParameter("ref")));

        request.setAttribute("personnes", PersonneDAO.findAll());

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}