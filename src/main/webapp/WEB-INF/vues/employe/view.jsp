<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profil de l'employé</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            background: linear-gradient(135deg, #eef2f7 0%, #dce3ec 100%);
            font-family: 'Inter', 'Segoe UI', system-ui, sans-serif;
            min-height: 100vh;
            padding: 40px 20px;
        }
        
        .profile-container {
            max-width: 900px;
            margin: 0 auto;
        }
        
        /* Header Card */
        .profile-header {
            background: white;
            border-radius: 24px;
            margin-bottom: 24px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.08);
            overflow: hidden;
            text-align: center;
            padding-bottom: 24px;
        }
        
        .header-bg {
            background: linear-gradient(135deg, #0a2540 0%, #1e3a5f 100%);
            height: 100px;
        }
        
        .profile-avatar {
            width: 130px;
            height: 130px;
            background: white;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: -65px auto 15px auto;
            border: 4px solid white;
            box-shadow: 0 5px 20px rgba(0,0,0,0.15);
            overflow: hidden;
        }
        
        .profile-avatar img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        
        .avatar-initial {
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 48px;
            font-weight: 600;
            color: white;
        }
        
        .profile-name {
            text-align: center;
            margin-top: 15px;
        }
        
        .profile-name h1 {
            font-size: 26px;
            font-weight: 700;
            color: #1e293b;
            margin-bottom: 5px;
        }
        
        .profile-badge {
            display: inline-block;
            background: #e2e8f0;
            color: #475569;
            padding: 4px 16px;
            border-radius: 30px;
            font-size: 13px;
            font-weight: 500;
            margin-top: 8px;
        }
        
        /* Info Cards */
        .info-card {
            background: white;
            border-radius: 20px;
            padding: 24px;
            margin-bottom: 20px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.05);
            transition: transform 0.2s, box-shadow 0.2s;
        }
        
        .info-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }
        
        .card-title {
            font-size: 16px;
            font-weight: 600;
            color: #0a2540;
            margin-bottom: 20px;
            padding-bottom: 12px;
            border-bottom: 2px solid #eef2f7;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        
        .info-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
        }
        
        .info-item {
            display: flex;
            flex-direction: column;
            gap: 4px;
        }
        
        .info-label {
            font-size: 11px;
            font-weight: 600;
            color: #64748b;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        
        .info-value {
            font-size: 15px;
            font-weight: 500;
            color: #1e293b;
        }
        
        /* Actions */
        .action-buttons {
            display: flex;
            gap: 12px;
            justify-content: center;
            margin-top: 20px;
            flex-wrap: wrap;
        }
        
        .btn {
            padding: 12px 28px;
            border-radius: 12px;
            font-weight: 500;
            font-size: 14px;
            text-decoration: none;
            transition: all 0.2s;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }
        
        .btn-primary {
            background: #0a2540;
            color: white;
        }
        
        .btn-primary:hover {
            background: #1e3a5f;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(10,37,64,0.3);
        }
        
        .btn-secondary {
            background: #f1f5f9;
            color: #1e293b;
        }
        
        .btn-secondary:hover {
            background: #e2e8f0;
            transform: translateY(-2px);
        }
        
        @media (max-width: 640px) {
            .info-grid {
                grid-template-columns: 1fr;
            }
            .profile-avatar {
                width: 100px;
                height: 100px;
                margin-top: -50px;
            }
            .action-buttons {
                flex-direction: column;
                align-items: center;
            }
            .btn {
                width: 100%;
                text-align: center;
                justify-content: center;
            }
        }
    </style>
</head>
<body>
    <div class="profile-container">
        <!-- Header avec photo -->
        <div class="profile-header">
            <div class="header-bg"></div>
            <div class="profile-avatar">
                <c:choose>
                    <c:when test="${employe.photoFilename != null && employe.photoFilename != ''}">
                        <img src="uploads/${employe.photoFilename}" alt="Photo">
                    </c:when>
                    <c:otherwise>
                        <div class="avatar-initial">
                            ${fn:toUpperCase(fn:substring(employe.prenom, 0, 1))}${fn:toUpperCase(fn:substring(employe.nom, 0, 1))}
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="profile-name">
                <h1>${employe.prenom} ${employe.nom}</h1>
                <span class="profile-badge">${employe.poste}</span>
            </div>
        </div>
        
        <!-- Informations personnelles -->
        <div class="info-card">
            <div class="card-title">
                <span>📘</span> Informations personnelles
            </div>
            <div class="info-grid">
                <div class="info-item">
                    <div class="info-label">Matricule</div>
                    <div class="info-value">${employe.matricule}</div>
                </div>
                <div class="info-item">
                    <div class="info-label">Département</div>
                    <div class="info-value">${employe.departementNom}</div>
                </div>
                <div class="info-item">
                    <div class="info-label">Date d'embauche</div>
                    <div class="info-value">${employe.dateEmbauche}</div>
                </div>
                <div class="info-item">
                    <div class="info-label">Type de contrat</div>
                    <div class="info-value">${employe.typeContrat}</div>
                </div>
            </div>
        </div>
        
        <!-- Contact -->
        <div class="info-card">
            <div class="card-title">
                <span>📞</span> Contact
            </div>
            <div class="info-grid">
                <div class="info-item">
                    <div class="info-label">Téléphone</div>
                    <div class="info-value">${employe.telephone}</div>
                </div>
                <div class="info-item">
                    <div class="info-label">Email</div>
                    <div class="info-value">${employe.email}</div>
                </div>
            </div>
        </div>
        
        <!-- Rémunération -->
        <div class="info-card">
            <div class="card-title">
                <span>💰</span> Rémunération & avantages
            </div>
            <div class="info-grid">
                <div class="info-item">
                    <div class="info-label">Salaire de base</div>
                    <div class="info-value">${employe.salaireBase} FCFA</div>
                </div>
                <div class="info-item">
                    <div class="info-label">Solde congés</div>
                    <div class="info-value">${employe.soldeCongesJours} jours</div>
                </div>
            </div>
        </div>
        
        <!-- Actions -->
        <div class="action-buttons">
            <c:if test="${user.role == 'RH' || user.role == 'DIRECTEUR'}">
                <a href="employe?action=edit&id=${employe.id}" class="btn btn-primary">✏️ Modifier le profil</a>
            </c:if>
            <c:choose>
                <c:when test="${user.role == 'EMPLOYE'}">
                    <a href="dashboard" class="btn btn-secondary">⬅️ Retour au tableau de bord</a>
                </c:when>
                <c:otherwise>
                    <a href="employe?action=list" class="btn btn-secondary">⬅️ Retour à la liste</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>