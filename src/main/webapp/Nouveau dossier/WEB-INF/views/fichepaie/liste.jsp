<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Fiches de Paie</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f4f4f4; }
        h2 { color: #333; }
        table { width: 100%; border-collapse: collapse; background: white; }
        th { background: #9C27B0; color: white; padding: 10px; }
        td { padding: 10px; border: 1px solid #ddd; text-align: center; }
        tr:hover { background: #f1f1f1; }
        .btn { padding: 6px 12px; border-radius: 4px; text-decoration: none; color: white; }
        .btn-add { background: #4CAF50; }
        .btn-edit { background: #2196F3; }
        .btn-delete { background: #f44336; }
        .btn-stats { background: #FF9800; }
    </style>
</head>
<body>
    <h2>Liste des Fiches de Paie</h2>
    <a href="fichepaie?action=new" class="btn btn-add">+ Nouvelle Fiche</a>
    <a href="fichepaie?action=stats" class="btn btn-stats">📊 Statistiques</a>
    <br/><br/>
    <table>
        <tr>
            <th>ID</th>
            <th>Employé ID</th>
            <th>Mois</th>
            <th>Salaire Base</th>
            <th>Heures Sup</th>
            <th>Primes</th>
            <th>Retenues</th>
            <th>Salaire Brut</th>
            <th>Salaire Net</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="f" items="${fiches}">
        <tr>
            <td>${f.id}</td>
            <td>${f.employeId}</td>
            <td>${f.mois}</td>
            <td>${f.salaireBase}</td>
            <td>${f.heuresSup}</td>
            <td>${f.primes}</td>
            <td>${f.retenues}</td>
            <td>${f.salaireBrut}</td>
            <td><strong>${f.salaireNet}</strong></td>
            <td>
                <a href="fichepaie?action=edit&id=${f.id}" class="btn btn-edit">Modifier</a>
                <a href="fichepaie?action=delete&id=${f.id}" class="btn btn-delete"
                   onclick="return confirm('Supprimer ?')">Supprimer</a>
            </td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>