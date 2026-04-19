<%@ page import="java.util.List" %>
<%@ page import="org.example.tp_servlet.Categorie" %>
<%@ page import="org.example.tp_servlet.CategorieDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Liste des Catégories</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }
        h2 { color: #388E3C; }
        form { background: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        form label { display: inline-block; width: 100px; font-weight: bold; }
        form input[type="text"] { padding: 8px; margin: 5px 0; width: 250px; border: 1px solid #ccc; border-radius: 4px; }
        form input[type="submit"] { padding: 10px 20px; background: #388E3C; color: white; border: none; border-radius: 4px; cursor: pointer; margin-top: 10px; }
        form input[type="submit"]:hover { background: #2E7D32; }
        table { border-collapse: collapse; width: 100%; background: white; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #388E3C; color: white; }
        tr:hover { background: #f1f1f1; }
        a { color: #d32f2f; text-decoration: none; margin-right: 10px; }
        a.edit { color: #388E3C; }
        .back { display: inline-block; margin-bottom: 15px; color: #388E3C; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>

<a class="back" href="index.jsp">&larr; Retour à l'accueil</a>

<h2>Liste des Catégories</h2>

<!-- Formulaire d'ajout -->
<form action="ajouter-categorie" method="post">
    <label>Nom:</label> <input type="text" name="nom" required/> <br/>
    <label>Description:</label> <input type="text" name="description" required/> <br/>
    <input type="submit" value="Ajouter"/>
</form>

<!-- Tableau des catégories -->
<table>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Description</th>
        <th>Actions</th>
    </tr>
    <%
        List<Categorie> categories = CategorieDAO.findAll();
        for (Categorie c : categories) {
    %>
    <tr>
        <td><%= c.getId() %></td>
        <td><%= c.getNom() %></td>
        <td><%= c.getDescription() %></td>
        <td>
            <a class="edit" href="modifier-categorie?ref=<%= c.getId() %>">Modifier</a>
            <a href="delete-categorie?ref=<%= c.getId() %>" onclick="return confirm('Confirmer la suppression ?')">Supprimer</a>
        </td>
    </tr>
    <% } %>
</table>

</body>
</html>
