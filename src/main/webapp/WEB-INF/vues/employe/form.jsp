<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire Employé</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2><c:if test="${employe == null}">Ajouter</c:if><c:if test="${employe != null}">Modifier</c:if> un employé</h2>
        
        <c:if test="${error != null}">
            <div class="error">${error}</div>
        </c:if>
        
        <!-- L'action est dans l'URL, pas dans un champ caché -->
        <c:choose>
            <c:when test="${employe == null}">
                <form action="employe?action=save" method="post" enctype="multipart/form-data">
            </c:when>
            <c:otherwise>
                <form action="employe?action=update&id=${employe.id}" method="post" enctype="multipart/form-data">
            </c:otherwise>
        </c:choose>
        
            <c:if test="${employe != null}">
                <input type="hidden" name="id" value="${employe.id}">
            </c:if>
            
            <label>Matricule :</label>
            <input type="text" name="matricule" value="${employe.matricule}" required>
            
            <label>Nom :</label>
            <input type="text" name="nom" value="${employe.nom}" required>
            
            <label>Prénom :</label>
            <input type="text" name="prenom" value="${employe.prenom}" required>
            
            <label>Poste :</label>
            <input type="text" name="poste" value="${employe.poste}" required>
            
            <label>Département :</label>
            <select name="departementId" required>
                <c:forEach var="d" items="${departements}">
                    <option value="${d.id}" ${employe.departementId == d.id ? 'selected' : ''}>${d.nom}</option>
                </c:forEach>
            </select>
            
            <label>Date d'embauche :</label>
            <input type="date" name="dateEmbauche" value="${employe.dateEmbauche}" required>
            
            <label>Salaire base :</label>
            <input type="number" step="0.01" name="salaireBase" value="${employe.salaireBase}" required>
            
            <label>Type contrat :</label>
            <select name="typeContrat" required>
                <option value="CDI" ${employe.typeContrat == 'CDI' ? 'selected' : ''}>CDI</option>
                <option value="CDD" ${employe.typeContrat == 'CDD' ? 'selected' : ''}>CDD</option>
                <option value="STAGE" ${employe.typeContrat == 'STAGE' ? 'selected' : ''}>STAGE</option>
                <option value="CONSULTANT" ${employe.typeContrat == 'CONSULTANT' ? 'selected' : ''}>CONSULTANT</option>
            </select>
            
            <label>Téléphone :</label>
            <input type="text" name="telephone" value="${employe.telephone}">
            
            <label>Email :</label>
            <input type="email" name="email" value="${employe.email}" required>
            
            <label>Photo :</label>
            <input type="file" name="photo" accept="image/jpeg,image/png">
            <c:if test="${employe.photoFilename != null && employe.photoFilename != ''}">
                <div>
                    <img src="uploads/${employe.photoFilename}" width="80" style="border-radius: 50%; margin-top: 10px;">
                </div>
            </c:if>
            
            <button type="submit">Enregistrer</button>
            <a href="employe?action=list">Annuler</a>
        </form>
    </div>
</body>
</html>