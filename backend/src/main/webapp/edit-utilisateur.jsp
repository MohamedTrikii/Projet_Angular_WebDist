<%@ page import="org.example.tp_servlet.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Utilisateur u = (Utilisateur) request.getAttribute("utilisateur");
%>
<html>
<head>
    <title>Modifier Utilisateur</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f5f5f5; }
        h2 { color: #1976D2; }
        form { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); max-width: 400px; }
        form label { display: inline-block; width: 80px; font-weight: bold; }
        form input[type="text"], form select { padding: 8px; margin: 5px 0; width: 200px; border: 1px solid #ccc; border-radius: 4px; }
        form input[type="submit"] { padding: 10px 20px; background: #1976D2; color: white; border: none; border-radius: 4px; cursor: pointer; margin-top: 10px; }
        .back { display: inline-block; margin-bottom: 15px; color: #1976D2; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>

<a class="back" href="liste-utilisateurs.jsp">&larr; Retour à la liste</a>

<h2>Modifier Utilisateur</h2>

<form action="modifier-utilisateur" method="post">
    <input type="hidden" name="id" value="<%= u.getId() %>"/>

    <label>Nom:</label>
    <input type="text" name="nom" value="<%= u.getNom() %>" required/> <br/>

    <label>Prénom:</label>
    <input type="text" name="prenom" value="<%= u.getPrenom() %>" required/> <br/>

    <label>Email:</label>
    <input type="text" name="email" value="<%= u.getEmail() %>" required/> <br/>

    <label>Rôle:</label>
    <select name="role">
        <option value="admin" <%= "admin".equals(u.getRole()) ? "selected" : "" %>>Admin</option>
        <option value="employe" <%= "employe".equals(u.getRole()) ? "selected" : "" %>>Employé</option>
    </select> <br/>

    <input type="submit" value="Modifier"/>
</form>

</body>
</html>
