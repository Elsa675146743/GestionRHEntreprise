<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire Département</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2><c:if test="${departement == null}">Ajouter</c:if><c:if test="${departement != null}">Modifier</c:if> un département</h2>
        
        <form action="departement" method="post">
            <input type="hidden" name="action" value="${departement == null ? 'save' : 'update'}">
            <c:if test="${departement != null}">
                <input type="hidden" name="id" value="${departement.id}">
            </c:if>
            
            <label>Nom :</label>
            <input type="text" name="nom" value="${departement.nom}" required><br><br>
            
            <label>Responsable :</label>
            <input type="text" name="responsable" value="${departement.responsable}" required><br><br>
            
            <label>Budget Salaire :</label>
            <input type="number" step="0.01" name="budgetSalaire" value="${departement.budgetSalaire}" required><br><br>
            
            <button type="submit">Enregistrer</button>
            <a href="departement?action=list">Annuler</a>
        </form>
    </div>
</body>
</html>