<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire contrat</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2><c:if test="${contrat == null}">Ajouter</c:if><c:if test="${contrat != null}">Modifier</c:if> un contrat</h2>
        
        <form action="contrat" method="post">
            <input type="hidden" name="action" value="${contrat == null ? 'save' : 'update'}">
            <c:if test="${contrat != null}">
                <input type="hidden" name="id" value="${contrat.id}">
            </c:if>
            
            <label>Employé :</label>
            <select name="employeId" required>
                <c:forEach var="e" items="${employes}">
                    <option value="${e.id}" ${contrat.employeId == e.id ? 'selected' : ''}>${e.nom} ${e.prenom}</option>
                </c:forEach>
            </select>
            
            <label>Type contrat :</label>
            <select name="typeContrat" required>
                <option value="CDI" ${contrat.typeContrat == 'CDI' ? 'selected' : ''}>CDI</option>
                <option value="CDD" ${contrat.typeContrat == 'CDD' ? 'selected' : ''}>CDD</option>
                <option value="STAGE" ${contrat.typeContrat == 'STAGE' ? 'selected' : ''}>STAGE</option>
            </select>
            
            <label>Date début :</label>
            <input type="date" name="dateDebut" value="${contrat.dateDebut}" required>
            
            <label>Date fin :</label>
            <input type="date" name="dateFin" value="${contrat.dateFin}">
            
            <label>Salaire :</label>
            <input type="number" step="0.01" name="salaire" value="${contrat.salaire}" required>
            
            <label>Avantages :</label>
            <textarea name="avantages" rows="3">${contrat.avantages}</textarea>
            
            <button type="submit">Enregistrer</button>
            <a href="contrat?action=list">Annuler</a>
        </form>
    </div>
</body>
</html>