<%@ page import="java.util.List" %>
<%@ page import="org.example.tp_servlet.Personne" %>
<%@ page import="org.example.tp_servlet.PersonneDAO" %>
<%@page contentType="text/html" %>

<html>
<body>

<form action="ajouter-servlet" method="post">

    Nom: <input type="text" name="nom"/> <br/>
    Prenom: <input type="text" name="prenom"/> <br/>
    Email: <input type="text" name="email"/> <br/>

    <input type="submit" value="envoyer">

</form>

<table border="1">

    <%

        List<Personne> personnes = PersonneDAO.findAll();

        for (Personne p : personnes){

    %>

    <tr>

        <td><%= p.getId()%></td>
        <td><%= p.getNom()%></td>
        <td><%= p.getPrenom()%></td>
        <td><%= p.getEmail()%></td>

        <td>
            <a href="delete?ref=<%= p.getId()%>">Supprimer</a>
        </td>

        <td>
            <a href="modifier?ref=<%= p.getId()%>">Modifier</a>
        </td>

    </tr>

    <%

        }

    %>

</table>

</body>
</html>