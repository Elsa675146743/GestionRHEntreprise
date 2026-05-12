<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des congés</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            background: #f0f2f5;
            font-family: 'Inter', 'Segoe UI', system-ui, sans-serif;
            min-height: 100vh;
            padding: 40px 20px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        
        .conge-container {
            max-width: 1100px;
            width: 100%;
            margin: 0 auto;
        }
        
        .main-card {
            background: white;
            border-radius: 20px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.05);
            overflow: hidden;
        }
        
        .card-header {
            padding: 24px 28px;
            border-bottom: 1px solid #e9ecef;
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            gap: 16px;
        }
        
        .card-header h1 {
            font-size: 22px;
            font-weight: 600;
            color: #1a2a3e;
        }
        
        .card-header p {
            font-size: 13px;
            color: #6c757d;
            margin-top: 4px;
        }
        
        .btn-add {
            background: #0a2540;
            color: white;
            padding: 10px 20px;
            border-radius: 8px;
            text-decoration: none;
            font-size: 13px;
            font-weight: 500;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            transition: all 0.2s;
        }
        
        .btn-add:hover {
            background: #1e3a5f;
            transform: translateY(-1px);
        }
        
        .card-body {
            padding: 24px 28px;
        }
        
        .table-wrapper {
            overflow-x: auto;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
        }
        
        th {
            text-align: left;
            padding: 14px 12px;
            background: #f8f9fa;
            color: #495057;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
            border-bottom: 1px solid #e9ecef;
        }
        
        td {
            padding: 14px 12px;
            border-bottom: 1px solid #f1f3f5;
            font-size: 14px;
        }
        
        tr:hover td {
            background: #f8f9fa;
        }
        
        .status-badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 500;
        }
        
        .status-DEMANDE {
            background: #fef3c7;
            color: #d97706;
        }
        
        .status-APPROUVE {
            background: #d1fae5;
            color: #059669;
        }
        
        .status-REFUSE {
            background: #fee2e2;
            color: #dc2626;
        }
        
        .action-links {
            display: flex;
            gap: 8px;
            flex-wrap: wrap;
        }
        
        .btn-approve {
            background: none;
            color: #059669;
            padding: 5px 12px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 12px;
            font-weight: 500;
            border: 1px solid #d1fae5;
            transition: all 0.2s;
        }
        
        .btn-approve:hover {
            background: #059669;
            color: white;
        }
        
        .btn-refuse {
            background: none;
            color: #dc2626;
            padding: 5px 12px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 12px;
            font-weight: 500;
            border: 1px solid #fee2e2;
            transition: all 0.2s;
        }
        
        .btn-refuse:hover {
            background: #dc2626;
            color: white;
        }
        
        .empty-row td {
            text-align: center;
            padding: 50px;
            color: #adb5bd;
        }
        
        .card-footer {
            padding: 16px 28px;
            border-top: 1px solid #e9ecef;
            background: #fafbfc;
        }
        
        .card-footer a {
            color: #6c757d;
            text-decoration: none;
            font-size: 13px;
        }
        
        .card-footer a:hover {
            color: #0a2540;
        }
        
        @media (max-width: 768px) {
            .card-header, .card-body, .card-footer {
                padding: 20px;
            }
            .card-header {
                flex-direction: column;
                align-items: flex-start;
            }
        }
    </style>
</head>
<body>
    <div class="conge-container">
        <div class="main-card">
            <div class="card-header">
                <div>
                    <h1>🏖️ Gestion des congés</h1>
                    <p>Demandes de congé des employés</p>
                </div>
                <!-- Tout le monde peut faire une demande -->
                <a href="conge?action=add" class="btn-add">➕ Nouvelle demande</a>
            </div>
            
            <div class="card-body">
                <div class="table-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Employé</th>
                                <th>Type</th>
                                <th>Date début</th>
                                <th>Date fin</th>
                                <th>Nb jours</th>
                                <th>Statut</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="c" items="${conges}">
                                <c:if test="${user.role != 'EMPLOYE' || c.employeId == user.employeId}">
                                    <tr>
                                        <td>${c.id}</td>
                                        <td>${c.employeId}</td>
                                        <td>${c.typeConge}</td>
                                        <td>${c.dateDebut}</td>
                                        <td>${c.dateFin}</td>
                                        <td>${c.nbJours} j
                                        <td><span class="status-badge status-${c.statut}">${c.statut}</span></td>
                                        <td class="action-links">
                                            <c:choose>
                                                <c:when test="${user.role == 'EMPLOYE'}">
                                                    <!-- Employé : pas d'action, juste voir -->
                                                    <span style="color:#94a3b8;">-</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <!-- RH ou DIRECTEUR peut approuver/refuser si demande est en attente -->
                                                    <c:if test="${c.statut == 'DEMANDE'}">
                                                        <a href="conge?action=approve&id=${c.id}" class="btn-approve" onclick="return confirm('Approuver cette demande ?')">✅ Approuver</a>
                                                        <a href="conge?action=refuse&id=${c.id}" class="btn-refuse" onclick="return confirm('Refuser cette demande ?')">❌ Refuser</a>
                                                    </c:if>
                                                    <c:if test="${c.statut != 'DEMANDE'}">
                                                        <span style="color:#94a3b8;">✓ Traité</span>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            <c:if test="${empty conges}">
                                <tr class="empty-row">
                                    <td colspan="8">📭 Aucune demande de congé trouvée</td>
                                </td>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            
            <div class="card-footer">
                <a href="dashboard">⬅️ Retour au tableau de bord</a>
            </div>
        </div>
    </div>
</body>
</html>