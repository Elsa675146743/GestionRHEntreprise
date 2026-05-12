<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Détails de l'employé</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .detail-card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            margin: 20px 0;
        }
        .detail-row {
            display: flex;
            padding: 10px 0;
            border-bottom: 1px solid #eee;
        }
        .detail-label {
            width: 200px;
            font-weight: bold;
            color: #667eea;
        }
        .detail-value {
            flex: 1;
        }
        .photo {
            text-align: center;
            margin-bottom: 20px;
        }
        .photo img {
            border-radius: 50%;
            border: 3px solid #667eea;
            width: 150px;
            height: 150px;
            object-fit: cover;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Détails de l'employé</h1>
        
        <div class="detail-card">
            <c:if test="${employe.photoFilename != null && employe.photoFilename != ''}">
                <div class="photo">
                    <img src="uploads/${employe.photoFilename}" alt="Photo">
                </div>
            </c:if>
            
            <div class="detail-row">
                <div class="detail-label">Matricule :</div>
                <div class="detail-value">${employe.matricule}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Nom :</div>
                <div class="detail-value">${employe.nom}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Prénom :</div>
                <div class="detail-value">${employe.prenom}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Poste :</div>
                <div class="detail-value">${employe.poste}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Département :</div>
                <div class="detail-value">${employe.departementNom}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Date d'embauche :</div>
                <div class="detail-value">${employe.dateEmbauche}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Salaire base :</div>
                <div class="detail-value">${employe.salaireBase} FCFA</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Type contrat :</div>
                <div class="detail-value">${employe.typeContrat}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Téléphone :</div>
                <div class="detail-value">${employe.telephone}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Email :</div>
                <div class="detail-value">${employe.email}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">Solde congés :</div>
                <div class="detail-value">${employe.soldeCongesJours} jours</div>
            </div>
        </div>
        
        <div style="margin-top: 20px;">
            <a href="employe?action=edit&id=${employe.id}" class="btn">✏️ Modifier</a>
            <a href="employe?action=list" class="btn">⬅️ Retour à la liste</a>
        </div>
    </div>
</body>
</html>