<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Congés</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f4f4f4; }
        h2 { color: #333; }
        table { width: 100%; border-collapse: collapse; background: white; }
        th { background: #2196F3; color: white; padding: 10px; }
        td { padding: 10px; border: 1px solid #ddd; }
        tr:hover { background: #f1f1f1; }
        .btn { padding: 6px 12px; border-radius: 4px; text-decoration: none; color: white; }
        .btn-add { background: #4CAF50; }
        .btn-edit { background: #2196F3; }
        .btn-delete { background: #f44336; }
        .DEMANDE { color: orange; font-weight: bold; }
        .APPROUVE { color: green; font-weight: bold; }
        .REFUSE { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <h2>Liste des Congés</h2>
    <a href="conge?action=new" class="btn btn-add">+ Nouveau Congé</a>
    <br/><br/>
    <table>
        <tr>
            <th>ID</th>
            <th>Employé ID</th>
            <th>Type</th>
            <th>Date Début</th>
            <th>Date Fin</th>
            <th>Nb Jours</th>
            <th>Motif</th>
            <th>Statut</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="c" items="${conges}">
        <tr>
            <td>${c.id}</td>
            <td>${c.employeId}</td>
            <td>${c.typeConge}</td>
            <td>${c.dateDebut}</td>
            <td>${c.dateFin}</td>
            <td>${c.nbJours}</td>
            <td>${c.motif}</td>
            <td class="${c.statut}">${c.statut}</td>
            <td>
                <a href="conge?action=edit&id=${c.id}" class="btn btn-edit">Modifier</a>
                <a href="conge?action=delete&id=${c.id}" class="btn btn-delete"
                   onclick="return confirm('Supprimer ?')">Supprimer</a>
            </td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>