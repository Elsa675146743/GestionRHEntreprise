<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des congés</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h1>Liste des congés</h1>
        
        <a href="conge?action=add" class="btn">Ajouter une demande</a>
        <br><br>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Employé</th>
                    <th>Type</th>
                    <th>Date début</th>
                    <th>Date fin</th>
                    <th>Nb jours</th>
                    <th>Statut</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="c" items="${conges}">
                    <tr>
                        <td>${c.id}</td>
                        <td>${c.employeId}</td>
                        <td>${c.typeConge}</td>
                        <td>${c.dateDebut}</td>
                        <td>${c.dateFin}</td>
                        <td>${c.nbJours}</td>
                        <td>${c.statut}</td>
                        <td class="action-links">
                            <a href="conge?action=edit&id=${c.id}">Modifier</a>
                            <a href="conge?action=delete&id=${c.id}" class="delete" onclick="return confirm('Supprimer cette demande ?')">Supprimer</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <a href="dashboard">Retour au dashboard</a>
    </div>
</body>
</html>