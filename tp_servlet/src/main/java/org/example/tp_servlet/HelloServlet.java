package org.example.tp_servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/hello-servlet")
public class HelloServlet extends HttpServlet {

    private List<Personne> listePersonnes = new ArrayList<>();

    public void init(){
        getServletContext().setAttribute("personnes", listePersonnes);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("personnes", listePersonnes);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");

        if (nom != null && prenom != null && email != null) {

            int id = listePersonnes.size() + 1;

            Personne p = new Personne(id, nom, prenom, email);

            listePersonnes.add(p);
        }

        response.sendRedirect("hello-servlet");
    }
}