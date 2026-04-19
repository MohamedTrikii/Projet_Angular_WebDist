<%@ page import="java.util.List" %>
<%@ page import="org.example.tp_servlet.Projet" %>
<%@ page import="org.example.tp_servlet.ProjetDAO" %>
<%@ page import="org.example.tp_servlet.Categorie" %>
<%@ page import="org.example.tp_servlet.CategorieDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Liste des Projets</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }
        h2 { color: #E64A19; }
        form { background: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        form label { display: inline-block; width: 100px; font-weight: bold; }
        form input[type="text"], form select { padding: 8px; margin: 5px 0; width: 250px; border: 1px solid #ccc; border-radius: 4px; }
        form input[type="submit"] { padding: 10px 20px; background: #E64A19; color: white; border: none; border-radius: 4px; cursor: pointer; margin-top: 10px; }
        form input[type="submit"]:hover { background: #D84315; }
        table { border-collapse: collapse; width: 100%; background: white; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #E64A19; color: white; }
        tr:hover { background: #f1f1f1; }
        a { color: #d32f2f; text-decoration: none; margin-right: 10px; }
        a.edit { color: #E64A19; }
        .back { display: inline-block; margin-bottom: 15px; color: #E64A19; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>

<a class="back" href="index.jsp">&larr; Retour à l'accueil</a>

<h2>Liste des Projets</h2>

<!-- Formulaire d'ajout -->
<form action="ajouter-projet" method="post">
    <label>Nom:</label> <input type="text" name="nom" required/> <br/>
    <label>Description:</label> <input type="text" name="description" required/> <br/>
    <label>Catégorie:</label>
    <select name="categorieId">
        <%
            List<Categorie> categories = CategorieDAO.findAll();
            for (Categorie cat : categories) {
        %>
        <option value="<%= cat.getId() %>"><%= cat.getNom() %></option>
        <% } %>
    </select> <br/>
    <input type="submit" value="Ajouter"/>
</form>

<!-- Tableau des projets -->
<table>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Description</th>
        <th>Catégorie</th>
        <th>Actions</th>
    </tr>
    <%
        List<Projet> projets = ProjetDAO.findAll();
        for (Projet p : projets) {
            Categorie cat = CategorieDAO.get(p.getCategorieId());
            String catNom = (cat != null) ? cat.getNom() : "N/A";
    %>
    <tr>
        <td><%= p.getId() %></td>
        <td><%= p.getNom() %></td>
        <td><%= p.getDescription() %></td>
        <td><%= catNom %></td>
        <td>
            <a class="edit" href="modifier-projet?ref=<%= p.getId() %>">Modifier</a>
            <a href="delete-projet?ref=<%= p.getId() %>" onclick="return confirm('Confirmer la suppression ?')">Supprimer</a>
        </td>
    </tr>
    <% } %>
</table>

</body>
</html>
