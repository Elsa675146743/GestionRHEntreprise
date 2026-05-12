<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord - Gestion RH</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h1>Bienvenue, ${user.login}</h1>
        <h3>Rôle : ${user.role}</h3>
        
        <div class="menu">
            <c:choose>
                <c:when test="${user.role == 'DIRECTEUR'}">
                    <a href="departement?action=list">📁 Départements</a>
                    <a href="employe?action=list">👥 Employés</a>
                    <a href="contrat?action=list">📄 Contrats</a>
                    <a href="conge?action=list">🏖️ Congés</a>
                    <a href="fichepaie?action=list">💰 Fiches de paie</a>
                    <a href="statistiques">📊 Statistiques</a>
                </c:when>
                <c:when test="${user.role == 'RH'}">
                    <a href="departement?action=list">📁 Départements</a>
                    <a href="employe?action=list">👥 Employés</a>
                    <a href="contrat?action=list">📄 Contrats</a>
                    <a href="conge?action=list">🏖️ Congés</a>
                    <a href="fichepaie?action=list">💰 Fiches de paie</a>
                </c:when>
                <c:when test="${user.role == 'EMPLOYE'}">
                    <a href="fichepaie?action=maFiche">💰 Ma fiche de paie</a>
                    <a href="conge?action=mesDemandes">🏖️ Mes congés</a>
                </c:when>
            </c:choose>
            <a href="logout" class="btn-danger">🔓 Déconnexion</a>
        </div>
    </div>
</body>
</html>