<%@ page import="java.util.List" %>
<%@ page import="org.example.tp_servlet.Utilisateur" %>
<%@ page import="org.example.tp_servlet.UtilisateurDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Liste des Utilisateurs</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }
        h2 { color: #1976D2; }
        form { background: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        form label { display: inline-block; width: 80px; font-weight: bold; }
        form input[type="text"], form select { padding: 8px; margin: 5px 0; width: 200px; border: 1px solid #ccc; border-radius: 4px; }
        form input[type="submit"] { padding: 10px 20px; background: #1976D2; color: white; border: none; border-radius: 4px; cursor: pointer; margin-top: 10px; }
        form input[type="submit"]:hover { background: #1565C0; }
        table { border-collapse: collapse; width: 100%; background: white; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #1976D2; color: white; }
        tr:hover { background: #f1f1f1; }
        a { color: #d32f2f; text-decoration: none; margin-right: 10px; }
        a.edit { color: #1976D2; }
        .back { display: inline-block; margin-bottom: 15px; color: #1976D2; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>

<a class="back" href="index.jsp">&larr; Retour à l'accueil</a>

<h2>Liste des Utilisateurs</h2>

<!-- Formulaire d'ajout -->
<form action="ajouter-utilisateur" method="post">
    <label>Nom:</label> <input type="text" name="nom" required/> <br/>
    <label>Prénom:</label> <input type="text" name="prenom" required/> <br/>
    <label>Email:</label> <input type="text" name="email" required/> <br/>
    <label>Rôle:</label>
    <select name="role">
        <option value="admin">Admin</option>
        <option value="employe">Employé</option>
    </select> <br/>
    <input type="submit" value="Ajouter"/>
</form>

<!-- Tableau des utilisateurs -->
<table>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Email</th>
        <th>Rôle</th>
        <th>Actions</th>
    </tr>
    <%
        List<Utilisateur> utilisateurs = UtilisateurDAO.findAll();
        for (Utilisateur u : utilisateurs) {
    %>
    <tr>
        <td><%= u.getId() %></td>
        <td><%= u.getNom() %></td>
        <td><%= u.getPrenom() %></td>
        <td><%= u.getEmail() %></td>
        <td><%= u.getRole() %></td>
        <td>
            <a class="edit" href="modifier-utilisateur?ref=<%= u.getId() %>">Modifier</a>
            <a href="delete-utilisateur?ref=<%= u.getId() %>" onclick="return confirm('Confirmer la suppression ?')">Supprimer</a>
        </td>
    </tr>
    <% } %>
</table>

</body>
</html>
