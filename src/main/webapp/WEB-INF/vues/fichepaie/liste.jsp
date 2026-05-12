<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des fiches de paie</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
            flex-wrap: wrap;
            gap: 15px;
        }
        .page-header h1 {
            margin: 0;
            font-size: 24px;
            color: #1e293b;
        }
        .btn-group {
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
        }
        .btn {
            padding: 10px 20px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 500;
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
        }
        .btn-secondary {
            background: #e2e8f0;
            color: #1e293b;
        }
        .btn-secondary:hover {
            background: #cbd5e1;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }
        th {
            background: #f8fafc;
            color: #1e293b;
            padding: 14px 12px;
            text-align: left;
            font-weight: 600;
            font-size: 13px;
            border-bottom: 1px solid #e2e8f0;
        }
        td {
            padding: 12px;
            border-bottom: 1px solid #f1f5f9;
            font-size: 14px;
        }
        tr:hover {
            background: #f8fafc;
        }
        .action-links {
            display: flex;
            gap: 8px;
            flex-wrap: wrap;
        }
        .action-btn {
            padding: 5px 12px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 12px;
            font-weight: 500;
            transition: all 0.2s;
        }
        .action-btn.edit { background: #eab308; color: #1e293b; }
        .action-btn.edit:hover { background: #ca8a04; }
        .action-btn.delete { background: #ef4444; color: white; }
        .action-btn.delete:hover { background: #dc2626; }
        .action-btn.pdf { background: #10b981; color: white; }
        .action-btn.pdf:hover { background: #059669; }
        .empty-row td {
            text-align: center;
            padding: 40px;
            color: #64748b;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            color: #64748b;
            text-decoration: none;
        }
        .back-link:hover {
            color: #0a2540;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="page-header">
            <h1>💰 Fiches de paie</h1>
            <div class="btn-group">
                <c:if test="${user.role == 'RH' || user.role == 'DIRECTEUR'}">
                    <a href="fichepaie?action=add" class="btn btn-primary">➕ Nouvelle fiche</a>
                </c:if>
            </div>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Employé</th>
                    <th>Mois</th>
                    <th>Salaire base</th>
                    <th>Heures sup</th>
                    <th>Primes</th>
                    <th>Retenues</th>
                    <th>Salaire net</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="f" items="${fiches}">
                    <c:if test="${user.role != 'EMPLOYE' || f.employeId == user.employeId}">
                        <tr>
                            <td>${f.id}</td>
                            <td>${f.employeId}</td>
                            <td>${f.mois}</td>
                            <td>${f.salaireBase} FCFA</td>
                            <td>${f.heuresSup} h</td>
                            <td>${f.primes} FCFA</td>
                            <td>${f.retenues} FCFA</td>
                            <td><strong>${f.salaireNet} FCFA</strong></td>
                            <td class="action-links">
                                <c:if test="${user.role == 'RH' || user.role == 'DIRECTEUR'}">
                                    <a href="fichepaie?action=edit&id=${f.id}" class="action-btn edit">✏️</a>
                                    <a href="fichepaie?action=delete&id=${f.id}" class="action-btn delete" onclick="return confirm('Supprimer ?')">🗑️</a>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/pdf/fichepaie?id=${f.id}" class="action-btn pdf">📄 PDF</a>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                <c:if test="${empty fiches}">
                    <tr class="empty-row"><td colspan="9">📭 Aucune fiche de paie trouvée</td></tr>
                </c:if>
            </tbody>
        </table>
        
        <a href="dashboard" class="back-link">⬅️ Retour au dashboard</a>
    </div>
</body>
</html>