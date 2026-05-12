<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire fiche de paie</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2><c:if test="${fiche == null}">Ajouter</c:if><c:if test="${fiche != null}">Modifier</c:if> une fiche de paie</h2>
        
        <form action="fichepaie" method="post">
            <input type="hidden" name="action" value="${fiche == null ? 'save' : 'update'}">
            <c:if test="${fiche != null}">
                <input type="hidden" name="id" value="${fiche.id}">
            </c:if>
            
            <label>Employé :</label>
            <select name="employeId" required>
                <c:forEach var="e" items="${employes}">
                    <option value="${e.id}" ${fiche.employeId == e.id ? 'selected' : ''}>${e.nom} ${e.prenom}</option>
                </c:forEach>
            </select>
            
            <label>Mois (AAAA-MM) :</label>
            <input type="month" name="mois" value="${fiche.mois}" required>
            
            <label>Salaire base :</label>
            <input type="number" step="0.01" name="salaireBase" value="${fiche.salaireBase}" required>
            
            <label>Heures supplémentaires :</label>
            <input type="number" step="0.5" name="heuresSup" value="${fiche.heuresSup}">
            
            <label>Primes :</label>
            <input type="number" step="0.01" name="primes" value="${fiche.primes}">
            
            <label>Retenues :</label>
            <input type="number" step="0.01" name="retenues" value="${fiche.retenues}">
            
            <button type="submit">Enregistrer</button>
            <a href="fichepaie?action=list">Annuler</a>
        </form>
    </div>
</body>
</html>