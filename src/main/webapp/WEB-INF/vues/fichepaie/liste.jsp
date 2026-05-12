<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des fiches de paie</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h1>Liste des fiches de paie</h1>
        
        <!-- Bouton Ajouter visible seulement pour RH et DIRECTEUR -->
        <c:if test="${user.role == 'RH' || user.role == 'DIRECTEUR'}">
            <a href="fichepaie?action=add" class="btn">Ajouter une fiche</a>
            <br><br>
        </c:if>
        
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
                    <!-- Pour EMPLOYE, afficher seulement ses fiches -->
                    <c:if test="${user.role != 'EMPLOYE' || f.employeId == user.employeId}">
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
                                <c:if test="${user.role == 'RH' || user.role == 'DIRECTEUR'}">
                                    <a href="fichepaie?action=edit&id=${f.id}">✏️ Modifier</a>
                                    <a href="fichepaie?action=delete&id=${f.id}" class="delete" onclick="return confirm('Supprimer ?')">🗑️ Supprimer</a>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/pdf/fichepaie?id=${f.id}" class="btn-small">📄 PDF</a>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                
                <c:if test="${empty fiches}">
                    <tr>
                        <td colspan="9" style="text-align: center;">Aucune fiche de paie trouvée</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        
        <br>
        <a href="dashboard">Retour au dashboard</a>
    </div>
</body>
</html>