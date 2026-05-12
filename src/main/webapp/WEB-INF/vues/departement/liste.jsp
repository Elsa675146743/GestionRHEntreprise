<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des départements</title>
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
        }
        
        /* Container centré */
        .departement-container {
            max-width: 1000px;
            width: 100%;
            margin: 0 auto;
        }
        
        /* Card principale */
        .main-card {
            background: white;
            border-radius: 20px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.05);
            overflow: hidden;
        }
        
        /* Header de la carte */
        .card-header {
            padding: 24px 28px;
            border-bottom: 1px solid #e9ecef;
            background: white;
        }
        
        .card-header h1 {
            font-size: 22px;
            font-weight: 600;
            color: #1a2a3e;
            margin-bottom: 6px;
        }
        
        .card-header p {
            font-size: 13px;
            color: #6c757d;
        }
        
        /* Corps de la carte */
        .card-body {
            padding: 24px 28px;
        }
        
        /* Barre d'action */
        .action-bar {
            display: flex;
            justify-content: flex-end;
            margin-bottom: 24px;
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
        
        .budget-value {
            font-weight: 500;
            color: #0a2540;
        }
        
        /* Boutons d'action */
        .action-links {
            display: flex;
            gap: 8px;
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
        
        @media (max-width: 640px) {
            .card-header, .card-body, .card-footer {
                padding: 20px;
            }
            .action-links {
                flex-direction: column;
                gap: 6px;
            }
            .btn-edit, .btn-delete {
                text-align: center;
            }
        }
    </style>
</head>
<body>
    <div class="departement-container">
        <div class="main-card">
            <div class="card-header">
                <h1>🏢 Départements</h1>
                <p>Gérez les départements de l'entreprise</p>
            </div>
            
            <div class="card-body">
                <div class="action-bar">
                    <a href="departement?action=add" class="btn-add">➕ Ajouter un département</a>
                </div>
                
                <div class="table-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nom</th>
                                <th>Responsable</th>
                                <th>Budget Salaire</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="d" items="${departements}">
                                <tr>
                                    <td>${d.id}</td>
                                    <td><strong>${d.nom}</strong></td>
                                    <td>${d.responsable}</td>
                                    <td class="budget-value">${d.budgetSalaire} FCFA</td>
                                    <td class="action-links">
                                        <a href="departement?action=edit&id=${d.id}" class="btn-edit">✏️ Modifier</a>
                                        <a href="departement?action=delete&id=${d.id}" class="btn-delete" onclick="return confirm('Supprimer ce département ?')">🗑️ Supprimer</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty departements}">
                                <tr class="empty-row">
                                    <td colspan="5">📭 Aucun département trouvé</td>
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