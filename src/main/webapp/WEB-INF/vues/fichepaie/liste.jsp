<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des fiches de paie</title>
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
            background: #28a745;
            color: white;
            padding: 5px 10px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 12px;
        }
        .btn-small:hover {
            background: #218838;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Liste des fiches de paie</h1>
        
        <div class="btn-group">
            <a href="fichepaie?action=add" class="btn">➕ Ajouter une fiche</a>
            <a href="${pageContext.request.contextPath}/pdf/rapport" class="btn">📊 PDF Rapport mensuel</a>
        </div>
        
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Employé</th>
                    <th>Mois</th>
                    <th>Salaire base</th>
                    <th>Heures sup</th>
                    <th>Primes</th>
                    <th>Retenues</th>
                    <th>Salaire net</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="f" items="${fiches}">
                <tr>
                    <td>${f.id}</td>
                    <td>${f.employeId}</td>
                    <td>${f.mois}</td>
                    <td>${f.salaireBase} FCFA</td>
                    <td>${f.heuresSup} h</td>
                    <td>${f.primes} FCFA</td>
                    <td>${f.retenues} FCFA</td>
                    <td>${f.salaireNet} FCFA</td>
                    <td class="action-links">
                        <a href="fichepaie?action=edit&id=${f.id}">✏️ Modifier</a>
                        <a href="fichepaie?action=delete&id=${f.id}" class="delete" onclick="return confirm('Supprimer cette fiche ?')">🗑️ Supprimer</a>
                        <a href="${pageContext.request.contextPath}/pdf/fichepaie?id=${f.id}" class="btn-small">📄 PDF Fiche</a>
                    </td>
                </tr>
                </c:forEach>
                
                <c:if test="${empty fiches}">
                    <tr>
                        <td colspan="9" style="text-align: center;">Aucune fiche de paie trouvée</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        
        <br>
        <a href="dashboard">⬅️ Retour au dashboard</a>
    </div>
</body>
</html>