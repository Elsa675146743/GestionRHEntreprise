<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des départements</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h1>Liste des départements</h1>
        
        <!-- Bouton Ajouter -->
        <a href="departement?action=add" class="btn">Ajouter un département</a>
        <br><br>
        
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Responsable</th>
                <th>Budget Salaire</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="d" items="${departements}">
            <tr>
                <td>${d.id}</td>
                <td>${d.nom}</td>
                <td>${d.responsable}</td>
                <td>${d.budgetSalaire}</td>
                <td>
                    <a href="departement?action=edit&id=${d.id}">Modifier</a>
                    <a href="departement?action=delete&id=${d.id}" onclick="return confirm('Supprimer ?')">Supprimer</a>
                </td>
            </tr>
            </c:forEach>
        </table>
        
        <br>
        <a href="dashboard">Retour au dashboard</a>
    </div>
</body>
</html>