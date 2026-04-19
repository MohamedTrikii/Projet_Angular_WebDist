<%@ page import="org.example.tp_servlet.Personne" %>
<%@page contentType="text/html" %>

<%

    Personne p = (Personne) request.getAttribute("personne");

%>

<html>
<body>

<form action="modifier" method="post">

    <input type="hidden" name="id" value="<%= p.getId() %>">

    Nom :
    <input type="text" name="nom" value="<%= p.getNom() %>" required>
    <br/>

    Prenom :
    <input type="text" name="prenom" value="<%= p.getPrenom() %>" required>
    <br/>

    Email :
    <input type="text" name="email" value="<%= p.getEmail() %>" required>
    <br/>

    <input type="submit" value="modifier">

</form>

</body>
</html>