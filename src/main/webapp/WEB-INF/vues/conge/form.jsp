<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire congé</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2><c:if test="${conge == null}">Ajouter</c:if><c:if test="${conge != null}">Modifier</c:if> une demande de congé</h2>
        
        <form action="conge" method="post">
            <input type="hidden" name="action" value="${conge == null ? 'save' : 'update'}">
            <c:if test="${conge != null}">
                <input type="hidden" name="id" value="${conge.id}">
            </c:if>
            
            <label>Employé :</label>
            <select name="employeId" required>
                <c:forEach var="e" items="${employes}">
                    <option value="${e.id}" ${conge.employeId == e.id ? 'selected' : ''}>${e.nom} ${e.prenom}</option>
                </c:forEach>
            </select>
            
            <label>Type congé :</label>
            <select name="typeConge" required>
                <option value="ANNUEL" ${conge.typeConge == 'ANNUEL' ? 'selected' : ''}>Annuel</option>
                <option value="MALADIE" ${conge.typeConge == 'MALADIE' ? 'selected' : ''}>Maladie</option>
                <option value="MATERNITE" ${conge.typeConge == 'MATERNITE' ? 'selected' : ''}>Maternité</option>
                <option value="PATERNITE" ${conge.typeConge == 'PATERNITE' ? 'selected' : ''}>Paternité</option>
                <option value="EXCEPTIONNEL" ${conge.typeConge == 'EXCEPTIONNEL' ? 'selected' : ''}>Exceptionnel</option>
            </select>
            
            <label>Date début :</label>
            <input type="date" name="dateDebut" value="${conge.dateDebut}" required>
            
            <label>Date fin :</label>
            <input type="date" name="dateFin" value="${conge.dateFin}" required>
            
            <label>Motif :</label>
            <textarea name="motif" rows="3">${conge.motif}</textarea>
            
            <c:if test="${conge != null}">
                <label>Statut :</label>
                <select name="statut">
                    <option value="DEMANDE" ${conge.statut == 'DEMANDE' ? 'selected' : ''}>Demande</option>
                    <option value="APPROUVE" ${conge.statut == 'APPROUVE' ? 'selected' : ''}>Approuvé</option>
                    <option value="REFUSE" ${conge.statut == 'REFUSE' ? 'selected' : ''}>Refusé</option>
                </select>
                
                <label>Approuvé par :</label>
                <input type="text" name="approuvePar" value="${conge.approuvePar}">
            </c:if>
            
            <button type="submit">Enregistrer</button>
            <a href="conge?action=list">Annuler</a>
        </form>
    </div>
</body>
</html>