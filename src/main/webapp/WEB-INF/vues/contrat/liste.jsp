<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des contrats</title>
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
        
        /* Container centré */
        .contrat-container {
            max-width: 1100px;
            width: 100%;
            margin: 0 auto;
        }
        
        /* Carte principale */
        .main-card {
            background: white;
            border-radius: 20px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.05);
            overflow: hidden;
        }
        
        /* En-tête */
        .card-header {
            padding: 24px 28px;
            border-bottom: 1px solid #e9ecef;
            background: white;
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
            margin-bottom: 4px;
        }
        
        .card-header p {
            font-size: 13px;
            color: #6c757d;
        }
        
        /* Corps */
        .card-body {
            padding: 24px 28px;
        }
        
        /* Bouton ajouter */
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
        
        /* Tableau */
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
            letter-spacing: 0.3px;
            border-bottom: 1px solid #e9ecef;
        }
        
        td {
            padding: 14px 12px;
            border-bottom: 1px solid #f1f3f5;
            font-size: 14px;
            color: #212529;
        }
        
        tr:hover td {
            background: #f8f9fa;
        }
        
        .salaire-value {
            font-weight: 500;
            color: #0a2540;
        }
        
        /* Boutons d'action */
        .action-links {
            display: flex;
            gap: 8px;
            flex-wrap: wrap;
        }
        
        .btn-edit {
            background: none;
            color: #0a2540;
            padding: 5px 12px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 12px;
            font-weight: 500;
            transition: all 0.2s;
            border: 1px solid #cbd5e1;
        }
        
        .btn-edit:hover {
            background: #0a2540;
            color: white;
            border-color: #0a2540;
        }
        
        .btn-delete {
            background: none;
            color: #dc2626;
            padding: 5px 12px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 12px;
            font-weight: 500;
            transition: all 0.2s;
            border: 1px solid #fecaca;
        }
        
        .btn-delete:hover {
            background: #dc2626;
            color: white;
            border-color: #dc2626;
        }
        
        .btn-pdf {
            background: none;
            color: #0a2540;
            padding: 5px 12px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 12px;
            font-weight: 500;
            transition: all 0.2s;
            border: 1px solid #cbd5e1;
        }
        
        .btn-pdf:hover {
            background: #0a2540;
            color: white;
            border-color: #0a2540;
        }
        
        /* Empty state */
        .empty-row td {
            text-align: center;
            padding: 50px;
            color: #adb5bd;
        }
        
        /* Footer */
        .card-footer {
            padding: 16px 28px;
            border-top: 1px solid #e9ecef;
            background: #fafbfc;
        }
        
        .card-footer a {
            color: #6c757d;
            text-decoration: none;
            font-size: 13px;
            display: inline-flex;
            align-items: center;
            gap: 6px;
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
            .action-links {
                flex-direction: column;
            }
            .btn-edit, .btn-delete, .btn-pdf {
                text-align: center;
            }
        }
    </style>
</head>
<body>
    <div class="contrat-container">
        <div class="main-card">
            <div class="card-header">
                <div>
                    <h1>📄 Contrats de travail</h1>
                    <p>Gérez les contrats des employés</p>
                </div>
                <a href="contrat?action=add" class="btn-add">➕ Ajouter un contrat</a>
            </div>
            
            <div class="card-body">
                <div class="table-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Employé</th>
                                <th>Type contrat</th>
                                <th>Date début</th>
                                <th>Date fin</th>
                                <th>Salaire</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="c" items="${contrats}">
                                <tr>
                                    <td>${c.id}</td>
                                    <td>${c.employeId}</td>
                                    <td>${c.typeContrat}</td>
                                    <td>${c.dateDebut}</td>
                                    <td>${c.dateFin != null ? c.dateFin : "-"}</td>
                                    <td class="salaire-value">${c.salaire} FCFA</td>
                                    <td class="action-links">
                                        <a href="contrat?action=edit&id=${c.id}" class="btn-edit">✏️ Modifier</a>
                                        <a href="contrat?action=delete&id=${c.id}" class="btn-delete" onclick="return confirm('Supprimer ce contrat ?')">🗑️ Supprimer</a>
                                        <a href="${pageContext.request.contextPath}/pdf/contrat?id=${c.id}" class="btn-pdf">📄 PDF</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty contrats}">
                                <tr class="empty-row">
                                    <td colspan="7">📭 Aucun contrat trouvé</td>
                                </tr>
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