<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>500 - Erreur interne</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container" style="text-align: center;">
        <h1 style="font-size: 80px; color: #dc3545;">500</h1>
        <h2>Erreur interne du serveur</h2>
        <p>Une erreur inattendue s'est produite. Veuillez réessayer plus tard.</p>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn">Retour au tableau de bord</a>
    </div>
</body>
</html>