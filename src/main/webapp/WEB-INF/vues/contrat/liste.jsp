<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des contrats</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .btn-group {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
            margin-bottom: 20px;
        }
        .btn-small {
            display: inline-block;
            background: #17a2b8;
            color: white;
            padding: 5px 10px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 12px;
        }
        .btn-small:hover {
            background: #138496;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Liste des contrats</h1>
        
        <div class="btn-group">
            <a href="contrat?action=add" class="btn">➕ Ajouter un contrat</a>
        </div>
        
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Employé</th>
                    <th>Type contrat</th>
                    <th>Date début</th>
                    <th>Date fin</th>
                    <th>Salaire</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="c" items="${contrats}">
                <tr>
                    <td>${c.id}</td>
                    <td>${c.employeId}</td>
                    <td>${c.typeContrat}</td>
                    <td>${c.dateDebut}</td>
                    <td>${c.dateFin}</td>
                    <td>${c.salaire} FCFA</td>
                    <td class="action-links">
                        <a href="contrat?action=edit&id=${c.id}">✏️ Modifier</a>
                        <a href="contrat?action=delete&id=${c.id}" class="delete" onclick="return confirm('Supprimer ce contrat ?')">🗑️ Supprimer</a>
                        <a href="${pageContext.request.contextPath}/pdf/contrat?id=${c.id}" class="btn-small">📄 PDF Contrat</a>
                    </td>
                </tr>
                </c:forEach>
                
                <c:if test="${empty contrats}">
                    <tr>
                        <td colspan="7" style="text-align: center;">Aucun contrat trouvé</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        
        <br>
        <a href="dashboard">⬅️ Retour au dashboard</a>
    </div>
</body>
</html>