<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire congé</title>
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
        }
        
        .container {
            max-width: 800px;
            margin: 40px auto;
            background: white;
            border-radius: 20px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        
        .form-header {
            background: #0a2540;
            padding: 24px 32px;
            color: white;
        }
        
        .form-header h1 {
            font-size: 22px;
            font-weight: 600;
            margin-bottom: 6px;
            color: white;
            
        }
        
        .form-header p {
            font-size: 13px;
            opacity: 0.8;
        }
        
        .form-body {
            padding: 32px;
        }
        
        .form-group {
            margin-bottom: 24px;
        }
        
        label {
            display: block;
            font-weight: 600;
            font-size: 13px;
            color: #1e293b;
            margin-bottom: 8px;
        }
        
        input, select, textarea {
            width: 100%;
            padding: 12px 14px;
            border: 1px solid #e2e8f0;
            border-radius: 10px;
            font-size: 14px;
            transition: all 0.2s;
            font-family: inherit;
        }
        
        input:focus, select:focus, textarea:focus {
            outline: none;
            border-color: #0a2540;
            box-shadow: 0 0 0 3px rgba(10,37,64,0.1);
        }
        
        textarea {
            resize: vertical;
            min-height: 80px;
        }
        
        .form-actions {
            display: flex;
            gap: 12px;
            margin-top: 32px;
        }
        
        .btn-primary {
            background: #0a2540;
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 10px;
            font-weight: 600;
            font-size: 14px;
            cursor: pointer;
            transition: all 0.2s;
        }
        
        .btn-primary:hover {
            background: #1e3a5f;
            transform: translateY(-1px);
        }
        
        .btn-secondary {
            background: #e2e8f0;
            color: #1e293b;
            padding: 12px 24px;
            border: none;
            border-radius: 10px;
            font-weight: 600;
            font-size: 14px;
            text-decoration: none;
            text-align: center;
            cursor: pointer;
            transition: all 0.2s;
        }
        
        .btn-secondary:hover {
            background: #cbd5e1;
        }
        
        .error-message {
            background: #fee2e2;
            color: #dc2626;
            padding: 12px 16px;
            border-radius: 10px;
            margin-bottom: 24px;
            font-size: 14px;
            border-left: 4px solid #dc2626;
        }
        
        hr {
            margin: 20px 0;
            border: none;
            border-top: 1px solid #e2e8f0;
        }
        
        /* Breadcrumb */
        .breadcrumb {
    padding: 16px 32px;
    background: #f8fafc;
    border-bottom: 1px solid #e2e8f0;
    font-size: 13px;
    border-radius: 12px 12px 0 0;
    margin-bottom: 0;
}
        
        .breadcrumb a {
            color: #64748b;
            text-decoration: none;
        }
        
        .breadcrumb a:hover {
            color: #0a2540;
        }
        
        .breadcrumb span {
            color: #1e293b;
        }
        
        @media (max-width: 640px) {
            .container {
                margin: 20px;
            }
            .form-body {
                padding: 24px;
            }
            .form-actions {
                flex-direction: column;
            }
            .btn-primary, .btn-secondary {
                text-align: center;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="breadcrumb">
            
        </div>
        
        <div class="form-header">
            <h1> Faire une demande de congé</h1>
            <p>Veuillez remplir les informations ci-dessous pour soumettre votre demande</p>
        </div>
        
        <div class="form-body">
            <c:if test="${error != null}">
                <div class="error-message">❌ ${error}</div>
            </c:if>
            
            <form action="conge" method="post">
                <input type="hidden" name="action" value="${conge == null ? 'save' : 'update'}">
                <c:if test="${conge != null}">
                    <input type="hidden" name="id" value="${conge.id}">
                </c:if>
                
                <div class="form-group">
                    <label>📌 Employé</label>
                    <c:choose>
                        <c:when test="${user.role == 'EMPLOYE'}">
                            <input type="text" value="${user.login}" disabled style="background: #f8fafc;">
                            <input type="hidden" name="employeId" value="${user.employeId}">
                        </c:when>
                        <c:otherwise>
                            <select name="employeId" required>
                                <option value="">Sélectionner un employé</option>
                                <c:forEach var="e" items="${employes}">
                                    <option value="${e.id}" ${conge.employeId == e.id ? 'selected' : ''}>${e.nom} ${e.prenom}</option>
                                </c:forEach>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </div>
                
                <div class="form-group">
                    <label> Type congé</label>
                    <select name="typeConge" required>
                        <option value="ANNUEL" ${conge.typeConge == 'ANNUEL' ? 'selected' : ''}>🏖️ Annuel</option>
                        <option value="MALADIE" ${conge.typeConge == 'MALADIE' ? 'selected' : ''}>🤒 Maladie</option>
                        <option value="MATERNITE" ${conge.typeConge == 'MATERNITE' ? 'selected' : ''}>👶 Maternité</option>
                        <option value="PATERNITE" ${conge.typeConge == 'PATERNITE' ? 'selected' : ''}>👨‍🍼 Paternité</option>
                        <option value="EXCEPTIONNEL" ${conge.typeConge == 'EXCEPTIONNEL' ? 'selected' : ''}>⭐ Exceptionnel</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label> Date début</label>
                    <input type="date" name="dateDebut" value="${conge.dateDebut}" required>
                </div>
                
                <div class="form-group">
                    <label> Date fin</label>
                    <input type="date" name="dateFin" value="${conge.dateFin}" required>
                </div>
                
                <div class="form-group">
                    <label> Motif (optionnel)</label>
                    <textarea name="motif" placeholder="Décrivez brièvement la raison de votre demande...">${conge.motif}</textarea>
                </div>
                
                <c:if test="${conge != null && (user.role == 'RH' || user.role == 'DIRECTEUR')}">
                    <div class="form-group">
                        <label>📌 Statut</label>
                        <select name="statut">
                            <option value="DEMANDE" ${conge.statut == 'DEMANDE' ? 'selected' : ''}>📋 En attente</option>
                            <option value="APPROUVE" ${conge.statut == 'APPROUVE' ? 'selected' : ''}>✅ Approuvé</option>
                            <option value="REFUSE" ${conge.statut == 'REFUSE' ? 'selected' : ''}>❌ Refusé</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label>👔 Approuvé par</label>
                        <input type="text" name="approuvePar" value="${conge.approuvePar}" placeholder="Nom du responsable">
                    </div>
                </c:if>
                
                <div class="form-actions">
                    <button type="submit" class="btn-primary"> Enregistrer</button>
                    <a href="conge?action=list" class="btn-secondary"> Annuler</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>