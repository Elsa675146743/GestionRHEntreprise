<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion - Gestion RH</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Connexion</h2>
        <form action="login" method="post">
            <label>Login :</label>
            <input type="text" name="login" required><br><br>
            <label>Mot de passe :</label>
            <input type="password" name="password" required><br><br>
            <button type="submit">Se connecter</button>
        </form>
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
    </div>
</body>
</html>