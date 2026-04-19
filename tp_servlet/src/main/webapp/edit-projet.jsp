<%@ page import="org.example.tp_servlet.Projet" %>
<%@ page import="org.example.tp_servlet.Categorie" %>
<%@ page import="org.example.tp_servlet.CategorieDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Projet p = (Projet) request.getAttribute("projet");
    List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
%>
<html>
<head>
    <title>Modifier Projet</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f5f5f5; }
        h2 { color: #E64A19; }
        form { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); max-width: 400px; }
        form label { display: inline-block; width: 100px; font-weight: bold; }
        form input[type="text"], form select { padding: 8px; margin: 5px 0; width: 250px; border: 1px solid #ccc; border-radius: 4px; }
        form input[type="submit"] { padding: 10px 20px; background: #E64A19; color: white; border: none; border-radius: 4px; cursor: pointer; margin-top: 10px; }
        .back { display: inline-block; margin-bottom: 15px; color: #E64A19; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>

<a class="back" href="liste-projets.jsp">&larr; Retour à la liste</a>

<h2>Modifier Projet</h2>

<form action="modifier-projet" method="post">
    <input type="hidden" name="id" value="<%= p.getId() %>"/>

    <label>Nom:</label>
    <input type="text" name="nom" value="<%= p.getNom() %>" required/> <br/>

    <label>Description:</label>
    <input type="text" name="description" value="<%= p.getDescription() %>" required/> <br/>

    <label>Catégorie:</label>
    <select name="categorieId">
        <%
            for (Categorie cat : categories) {
        %>
        <option value="<%= cat.getId() %>" <%= (cat.getId() == p.getCategorieId()) ? "selected" : "" %>>
            <%= cat.getNom() %>
        </option>
        <% } %>
    </select> <br/>

    <input type="submit" value="Modifier"/>
</form>

</body>
</html>
