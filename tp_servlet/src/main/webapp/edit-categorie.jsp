<%@ page import="org.example.tp_servlet.Categorie" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Categorie c = (Categorie) request.getAttribute("categorie");
%>
<html>
<head>
    <title>Modifier Catégorie</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f5f5f5; }
        h2 { color: #388E3C; }
        form { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); max-width: 400px; }
        form label { display: inline-block; width: 100px; font-weight: bold; }
        form input[type="text"] { padding: 8px; margin: 5px 0; width: 250px; border: 1px solid #ccc; border-radius: 4px; }
        form input[type="submit"] { padding: 10px 20px; background: #388E3C; color: white; border: none; border-radius: 4px; cursor: pointer; margin-top: 10px; }
        .back { display: inline-block; margin-bottom: 15px; color: #388E3C; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>

<a class="back" href="liste-categories.jsp">&larr; Retour à la liste</a>

<h2>Modifier Catégorie</h2>

<form action="modifier-categorie" method="post">
    <input type="hidden" name="id" value="<%= c.getId() %>"/>

    <label>Nom:</label>
    <input type="text" name="nom" value="<%= c.getNom() %>" required/> <br/>

    <label>Description:</label>
    <input type="text" name="description" value="<%= c.getDescription() %>" required/> <br/>

    <input type="submit" value="Modifier"/>
</form>

</body>
</html>
