<%@ page import="java.util.List" %>
<%@ page import="org.example.tp_servlet.Affectation" %>
<%@ page import="org.example.tp_servlet.AffectationDAO" %>
<%@ page import="org.example.tp_servlet.Utilisateur" %>
<%@ page import="org.example.tp_servlet.UtilisateurDAO" %>
<%@ page import="org.example.tp_servlet.Projet" %>
<%@ page import="org.example.tp_servlet.ProjetDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Liste des Affectations</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }
        h2 { color: #7B1FA2; }
        form { background: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        form label { display: inline-block; width: 110px; font-weight: bold; }
        form input[type="text"], form input[type="date"], form select { padding: 8px; margin: 5px 0; width: 250px; border: 1px solid #ccc; border-radius: 4px; }
        form input[type="submit"] { padding: 10px 20px; background: #7B1FA2; color: white; border: none; border-radius: 4px; cursor: pointer; margin-top: 10px; }
        form input[type="submit"]:hover { background: #6A1B9A; }
        table { border-collapse: collapse; width: 100%; background: white; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #7B1FA2; color: white; }
        tr:hover { background: #f1f1f1; }
        a { color: #d32f2f; text-decoration: none; margin-right: 10px; }
        a.edit { color: #7B1FA2; }
        .back { display: inline-block; margin-bottom: 15px; color: #7B1FA2; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>

<a class="back" href="index.jsp">&larr; Retour à l'accueil</a>

<h2>Liste des Affectations</h2>

<!-- Formulaire d'ajout -->
<form action="ajouter-affectation" method="post">
    <label>Utilisateur:</label>
    <select name="utilisateurId">
        <%
            List<Utilisateur> utilisateurs = UtilisateurDAO.findAll();
            for (Utilisateur u : utilisateurs) {
        %>
        <option value="<%= u.getId() %>"><%= u.getNom() %> <%= u.getPrenom() %></option>
        <% } %>
    </select> <br/>

    <label>Projet:</label>
    <select name="projetId">
        <%
            List<Projet> projets = ProjetDAO.findAll();
            for (Projet pr : projets) {
        %>
        <option value="<%= pr.getId() %>"><%= pr.getNom() %></option>
        <% } %>
    </select> <br/>

    <label>Date début:</label> <input type="date" name="dateDebut" required/> <br/>
    <label>Date fin:</label> <input type="date" name="dateFin" required/> <br/>
    <input type="submit" value="Ajouter"/>
</form>

<!-- Tableau des affectations -->
<table>
    <tr>
        <th>ID</th>
        <th>Utilisateur</th>
        <th>Projet</th>
        <th>Date Début</th>
        <th>Date Fin</th>
        <th>Actions</th>
    </tr>
    <%
        List<Affectation> affectations = AffectationDAO.findAll();
        for (Affectation a : affectations) {
            Utilisateur user = UtilisateurDAO.get(a.getUtilisateurId());
            Projet proj = ProjetDAO.get(a.getProjetId());
            String userName = (user != null) ? user.getNom() + " " + user.getPrenom() : "N/A";
            String projName = (proj != null) ? proj.getNom() : "N/A";
    %>
    <tr>
        <td><%= a.getId() %></td>
        <td><%= userName %></td>
        <td><%= projName %></td>
        <td><%= a.getDateDebut() %></td>
        <td><%= a.getDateFin() %></td>
        <td>
            <a class="edit" href="modifier-affectation?ref=<%= a.getId() %>">Modifier</a>
            <a href="delete-affectation?ref=<%= a.getId() %>" onclick="return confirm('Confirmer la suppression ?')">Supprimer</a>
        </td>
    </tr>
    <% } %>
</table>

</body>
</html>
