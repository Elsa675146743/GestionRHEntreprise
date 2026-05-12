<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire Employé</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            background: #eef2f7;
            font-family: 'Inter', 'Segoe UI', system-ui, sans-serif;
            padding: 40px 20px;
        }
        
        .form-container {
            max-width: 800px;
            margin: 0 auto;
        }
        
        .form-header {
            margin-bottom: 24px;
        }
        
        .form-header h1 {
            font-size: 28px;
            font-weight: 700;
            color: #1e293b;
            margin-bottom: 8px;
        }
        
        .form-header p {
            font-size: 14px;
            color: #64748b;
        }
        
        .form-card {
            background: white;
            border-radius: 24px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.08);
            overflow: hidden;
        }
        
        .form-body {
            padding: 32px;
        }
        
        .form-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px 24px;
        }
        
        .form-group {
            display: flex;
            flex-direction: column;
            gap: 6px;
        }
        
        .form-group-full {
            grid-column: span 2;
        }
        
        label {
            font-size: 13px;
            font-weight: 600;
            color: #0a2540;
            text-transform: uppercase;
            letter-spacing: 0.3px;
        }
        
        input, select, textarea {
            padding: 12px 14px;
            border: 1px solid #e2e8f0;
            border-radius: 12px;
            font-size: 14px;
            font-family: inherit;
            transition: all 0.2s;
            background: white;
        }
        
        input:focus, select:focus, textarea:focus {
            outline: none;
            border-color: #0a2540;
            box-shadow: 0 0 0 3px rgba(10,37,64,0.1);
        }
        
        input[readonly] {
            background: #f8fafc;
            color: #64748b;
        }
        
        .photo-section {
            background: #f8fafc;
            border-radius: 16px;
            padding: 20px;
            margin-top: 20px;
            display: flex;
            align-items: center;
            gap: 20px;
            flex-wrap: wrap;
        }
        
        .current-photo {
            display: flex;
            align-items: center;
            gap: 12px;
        }
        
        .current-photo img {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid #cbd5e1;
        }
        
        .photo-label {
            font-size: 13px;
            font-weight: 500;
            color: #1e293b;
        }
        
        input[type="file"] {
            border: none;
            padding: 8px 0;
        }
        
        .form-actions {
            display: flex;
            gap: 12px;
            margin-top: 32px;
            padding-top: 24px;
            border-top: 1px solid #e2e8f0;
        }
        
        .btn {
            padding: 12px 28px;
            border-radius: 12px;
            font-weight: 600;
            font-size: 14px;
            text-decoration: none;
            transition: all 0.2s;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            cursor: pointer;
            border: none;
        }
        
        .btn-primary {
            background: #0a2540;
            color: white;
        }
        
        .btn-primary:hover {
            background: #1e3a5f;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(10,37,64,0.2);
        }
        
        .btn-secondary {
            background: #f1f5f9;
            color: #1e293b;
        }
        
        .btn-secondary:hover {
            background: #e2e8f0;
            transform: translateY(-2px);
        }
        
        .error-message {
            background: #fee2e2;
            color: #dc2626;
            padding: 12px 16px;
            border-radius: 12px;
            margin-bottom: 20px;
            font-size: 14px;
            border-left: 4px solid #dc2626;
        }
        
        @media (max-width: 640px) {
            .form-grid {
                grid-template-columns: 1fr;
            }
            .form-group-full {
                grid-column: span 1;
            }
            .form-body {
                padding: 24px;
            }
            .form-actions {
                flex-direction: column;
            }
            .btn {
                justify-content: center;
            }
        }
    </style>
</head>
<body>
    <div class="form-container">
        <div class="form-header">
            <h1><c:if test="${employe == null}">➕ Ajouter</c:if><c:if test="${employe != null}">✏️ Modifier</c:if> un employé</h1>
            <p>Veuillez remplir les informations ci-dessous</p>
        </div>
        
        <div class="form-card">
            <div class="form-body">
                <c:if test="${error != null}">
                    <div class="error-message">❌ ${error}</div>
                </c:if>
                
                <!-- L'action et l'id sont dans l'URL -->
                <c:choose>
                    <c:when test="${employe != null}">
                        <form action="employe?action=update&id=${employe.id}" method="post" enctype="multipart/form-data">
                    </c:when>
                    <c:otherwise>
                        <form action="employe?action=save" method="post" enctype="multipart/form-data">
                    </c:otherwise>
                </c:choose>
                    
                    <div class="form-grid">
                        <div class="form-group">
                            <label>📌 Matricule</label>
                            <input type="text" name="matricule" value="${employe.matricule}" required placeholder="EMP-XXX">
                        </div>
                        
                        <div class="form-group">
                            <label>👤 Nom</label>
                            <input type="text" name="nom" value="${employe.nom}" required>
                        </div>
                        
                        <div class="form-group">
                            <label>👤 Prénom</label>
                            <input type="text" name="prenom" value="${employe.prenom}" required>
                        </div>
                        
                        <div class="form-group">
                            <label>💼 Poste</label>
                            <input type="text" name="poste" value="${employe.poste}" required>
                        </div>
                        
                        <div class="form-group">
                            <label>🏢 Département</label>
                            <select name="departementId" required>
                                <option value="">Sélectionner</option>
                                <c:forEach var="d" items="${departements}">
                                    <option value="${d.id}" ${employe.departementId == d.id ? 'selected' : ''}>${d.nom}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label>📅 Date d'embauche</label>
                            <input type="date" name="dateEmbauche" value="${employe.dateEmbauche}" required>
                        </div>
                        
                        <div class="form-group">
                            <label>💰 Salaire base (FCFA)</label>
                            <input type="number" step="0.01" name="salaireBase" value="${employe.salaireBase}" required>
                        </div>
                        
                        <div class="form-group">
                            <label>📄 Type contrat</label>
                            <select name="typeContrat" required>
                                <option value="CDI" ${employe.typeContrat == 'CDI' ? 'selected' : ''}>CDI</option>
                                <option value="CDD" ${employe.typeContrat == 'CDD' ? 'selected' : ''}>CDD</option>
                                <option value="STAGE" ${employe.typeContrat == 'STAGE' ? 'selected' : ''}>STAGE</option>
                                <option value="CONSULTANT" ${employe.typeContrat == 'CONSULTANT' ? 'selected' : ''}>CONSULTANT</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label>📞 Téléphone</label>
                            <input type="text" name="telephone" value="${employe.telephone}" placeholder="6XXXXXXXX">
                        </div>
                        
                        <div class="form-group">
                            <label>✉️ Email</label>
                            <input type="email" name="email" value="${employe.email}" required>
                        </div>
                        
                        <div class="form-group-full">
                            <div class="photo-section">
                                <div class="current-photo">
                                    <c:choose>
                                        <c:when test="${employe.photoFilename != null && employe.photoFilename != ''}">
                                            <img src="uploads/${employe.photoFilename}" alt="Photo">
                                            <span class="photo-label">Photo actuelle</span>
                                        </c:when>
                                        <c:otherwise>
                                            <div style="width:60px; height:60px; background:#e2e8f0; border-radius:50%; display:flex; align-items:center; justify-content:center; color:#94a3b8;">📷</div>
                                            <span class="photo-label">Aucune photo</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div style="flex:1;">
                                    <label style="text-transform:none;">📸 Changer de photo</label>
                                    <input type="file" name="photo" accept="image/jpeg,image/png">
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">💾 Enregistrer</button>
                        <a href="employe?action=list" class="btn btn-secondary">❌ Annuler</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
