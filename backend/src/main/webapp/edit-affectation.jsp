<%@ page import="org.example.tp_servlet.Affectation" %>
<%@ page import="org.example.tp_servlet.Utilisateur" %>
<%@ page import="org.example.tp_servlet.UtilisateurDAO" %>
<%@ page import="org.example.tp_servlet.Projet" %>
<%@ page import="org.example.tp_servlet.ProjetDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Affectation a = (Affectation) request.getAttribute("affectation");
    List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
    List<Projet> projets = (List<Projet>) request.getAttribute("projets");
%>
<html>
<head>
    <title>Modifier Affectation</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f5f5f5; }
        h2 { color: #7B1FA2; }
        form { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); max-width: 450px; }
        form label { display: inline-block; width: 110px; font-weight: bold; }
        form input[type="date"], form select { padding: 8px; margin: 5px 0; width: 250px; border: 1px solid #ccc; border-radius: 4px; }
        form input[type="submit"] { padding: 10px 20px; background: #7B1FA2; color: white; border: none; border-radius: 4px; cursor: pointer; margin-top: 10px; }
        .back { display: inline-block; margin-bottom: 15px; color: #7B1FA2; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>

<a class="back" href="liste-affectations.jsp">&larr; Retour à la liste</a>

<h2>Modifier Affectation</h2>

<form action="modifier-affectation" method="post">
    <input type="hidden" name="id" value="<%= a.getId() %>"/>

    <label>Utilisateur:</label>
    <select name="utilisateurId">
        <%
            for (Utilisateur u : utilisateurs) {
        %>
        <option value="<%= u.getId() %>" <%= (u.getId() == a.getUtilisateurId()) ? "selected" : "" %>>
            <%= u.getNom() %> <%= u.getPrenom() %>
        </option>
        <% } %>
    </select> <br/>

    <label>Projet:</label>
    <select name="projetId">
        <%
            for (Projet p : projets) {
        %>
        <option value="<%= p.getId() %>" <%= (p.getId() == a.getProjetId()) ? "selected" : "" %>>
            <%= p.getNom() %>
        </option>
        <% } %>
    </select> <br/>

    <label>Date début:</label>
    <input type="date" name="dateDebut" value="<%= a.getDateDebut() %>" required/> <br/>

    <label>Date fin:</label>
    <input type="date" name="dateFin" value="<%= a.getDateFin() %>" required/> <br/>

    <input type="submit" value="Modifier"/>
</form>

</body>
</html>
