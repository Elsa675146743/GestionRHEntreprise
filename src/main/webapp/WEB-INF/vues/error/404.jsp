<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>404 - Page non trouvée</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container" style="text-align: center;">
        <h1 style="font-size: 80px; color: #dc3545;">404</h1>
        <h2>Page non trouvée</h2>
        <p>La page que vous recherchez n'existe pas ou a été déplacée.</p>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn">Retour au tableau de bord</a>
    </div>
</body>
</html>