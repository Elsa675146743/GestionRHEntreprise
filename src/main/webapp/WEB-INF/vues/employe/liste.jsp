<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des employés</title>
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
        
        /* Header */
        .app-header {
            background: #0a2540;
            color: white;
            padding: 0 32px;
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            height: 64px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            z-index: 100;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        }
        
        .logo {
            font-size: 20px;
            font-weight: 700;
        }
        
        .logo span {
            font-weight: 300;
        }
        
        .nav-links {
            display: flex;
            gap: 32px;
            align-items: center;
        }
        
        .nav-links a {
            color: #e2e8f0;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
        }
        
        .nav-links a:hover {
            color: white;
        }
        
        /* Main content */
        .main-content {
            margin-top: 64px;
            padding: 32px;
        }
        
        /* Page header */
        .page-header {
            margin-bottom: 28px;
        }
        
        .page-header h1 {
            font-size: 24px;
            font-weight: 700;
            color: #1e293b;
            margin-bottom: 8px;
        }
        
        .page-header p {
            font-size: 14px;
            color: #64748b;
        }
        
        /* Action bar */
        .action-bar {
            background: white;
            border-radius: 16px;
            padding: 20px 24px;
            margin-bottom: 24px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            gap: 16px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }
        
        .btn-group {
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
        }
        
        .btn {
            padding: 8px 18px;
            border-radius: 8px;
            font-size: 13px;
            font-weight: 500;
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
            transform: translateY(-1px);
        }
        
        .btn-secondary {
            background: #e2e8f0;
            color: #1e293b;
        }
        
        .btn-secondary:hover {
            background: #cbd5e1;
        }
        
        .btn-outline {
            background: white;
            color: #0a2540;
            border: 1px solid #cbd5e1;
        }
        
        .btn-outline:hover {
            background: #f8fafc;
        }
        
        /* Search and filters */
        .search-section {
            display: flex;
            gap: 16px;
            flex-wrap: wrap;
            align-items: center;
        }
        
        .search-box {
            display: flex;
            align-items: center;
            background: white;
            border: 1px solid #e2e8f0;
            border-radius: 10px;
            padding: 8px 16px;
            gap: 8px;
        }
        
        .search-box input {
            border: none;
            outline: none;
            font-size: 14px;
            width: 220px;
        }
        
        .filter-select {
            padding: 8px 16px;
            border: 1px solid #e2e8f0;
            border-radius: 10px;
            font-size: 13px;
            background: white;
        }
        
        /* Table */
        .table-container {
            background: white;
            border-radius: 16px;
            overflow: hidden;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
        }
        
        th {
            background: #f8fafc;
            color: #475569;
            padding: 14px 16px;
            text-align: left;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            border-bottom: 1px solid #e2e8f0;
        }
        
        td {
            padding: 14px 16px;
            border-bottom: 1px solid #f1f5f9;
            font-size: 14px;
            vertical-align: top;
        }
        
        tr:hover td {
            background: #fafcff;
        }
        
        .employee-name {
            font-weight: 600;
            color: #1e293b;
        }
        
        .employee-email {
            font-size: 12px;
            color: #94a3b8;
            margin-top: 2px;
        }
        
        .action-links {
            display: flex;
            gap: 6px;
        }
        
        .action-btn {
            padding: 6px 10px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 12px;
            transition: all 0.2s;
        }
        
        .action-btn.view { background: #e2e8f0; color: #475569; }
        .action-btn.view:hover { background: #cbd5e1; }
        .action-btn.edit { background: #eab308; color: #1e293b; }
        .action-btn.edit:hover { background: #ca8a04; }
        .action-btn.delete { background: #ef4444; color: white; }
        .action-btn.delete:hover { background: #dc2626; }
        
        /* Pagination */
        .pagination {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px 24px;
            background: white;
            border-top: 1px solid #e2e8f0;
        }
        
        .pagination-info {
            font-size: 13px;
            color: #64748b;
        }
        
        .pagination-links {
            display: flex;
            gap: 8px;
        }
        
        .pagination-links a {
            padding: 6px 12px;
            background: #f1f5f9;
            color: #1e293b;
            text-decoration: none;
            border-radius: 6px;
            font-size: 13px;
            transition: all 0.2s;
        }
        
        .pagination-links a:hover {
            background: #0a2540;
            color: white;
        }
        
        .pagination-links span {
            padding: 6px 12px;
            color: #94a3b8;
        }
        
        /* Footer */
        .footer {
            text-align: center;
            padding: 24px;
            font-size: 12px;
            color: #94a3b8;
            border-top: 1px solid #e2e8f0;
            margin-top: 24px;
        }
        
        /* Responsive */
        @media (max-width: 768px) {
            .main-content {
                padding: 20px;
            }
            .action-bar {
                flex-direction: column;
                align-items: stretch;
            }
            .search-section {
                flex-direction: column;
            }
            .table-container {
                overflow-x: auto;
            }
            .pagination {
                flex-direction: column;
                gap: 12px;
            }
        }
    </style>
</head>
<body>
    <div class="app-header">
        <div class="logo">GESTION<span> RH</span></div>
        <div class="nav-links">
            <a href="dashboard">Accueil</a>
            <a href="employe?action=list">Employés</a>
            <a href="departement?action=list">Départements</a>
            <a href="logout" style="color: #dc3545;">Déconnexion</a>
        </div>
    </div>
    
    <div class="main-content">
        <div class="page-header">
            <h1>Liste des employés</h1>
            <p>Total : ${totalRecords} employés inscrits dans le système</p>
        </div>
        
        <div class="action-bar">
            <div class="btn-group">
                <a href="employe?action=add" class="btn btn-primary">➕ Ajouter un employé</a>
                <a href="${pageContext.request.contextPath}/csv/employes" class="btn btn-secondary">📥 Exporter CSV</a>
                <a href="${pageContext.request.contextPath}/pdf/employes" class="btn btn-secondary">📄 Exporter PDF</a>
            </div>
            
            <div class="search-section">
                <form action="employe" method="get" style="display: flex; gap: 12px; flex-wrap: wrap;">
                    <input type="hidden" name="action" value="search">
                    <div class="search-box">
                        <span>🔍</span>
                        <input type="text" name="keyword" placeholder="Rechercher par nom, matricule..." value="${keyword}">
                    </div>
                    <button type="submit" class="btn btn-primary" style="padding: 8px 20px;">Rechercher</button>
                    <c:if test="${keyword != null && keyword != ''}">
                        <a href="employe?action=list" class="btn btn-secondary">Réinitialiser</a>
                    </c:if>
                </form>
            </div>
        </div>
        
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>MATRICULE</th>
                        <th>NOM & PRÉNOM</th>
                        <th>POSTE</th>
                        <th>DÉPARTEMENT</th>
                        <th>EMBAUCHE</th>
                        <th>SALAIRE</th>
                        <th>CONTRAT</th>
                        <th>ACTIONS</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="e" items="${employes}">
                        <tr>
                            <td>${e.id}</td>
                            <td>${e.matricule}</td>
                            <td>
                                <div class="employee-name">${e.nom} ${e.prenom}</div>
                                <div class="employee-email">${e.email}</div>
                            </td>
                            <td>${e.poste}</td>
                            <td>${e.departementNom}</td>
                            <td>${e.dateEmbauche}</td>
                            <td>${e.salaireBase} FCFA</td>
                            <td>${e.typeContrat}</td>
                            <td class="action-links">
                                <a href="employe?action=view&id=${e.id}" class="action-btn view">👁️</a>
                                <a href="employe?action=delete&id=${e.id}" class="action-btn delete" onclick="return confirm('Supprimer cet employé ?')">🗑️</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty employes}">
                        <tr>
                            <td colspan="9" style="text-align: center; padding: 40px;">Aucun employé trouvé</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
            
            <div class="pagination">
                <div class="pagination-info">
                    Affichage de ${(currentPage-1)*5+1} à ${currentPage*5 > totalRecords ? totalRecords : currentPage*5} sur ${totalRecords} employés
                </div>
                <div class="pagination-links">
                    <c:if test="${currentPage > 1}">
                        <a href="employe?action=${keyword != null ? 'search' : 'list'}&keyword=${keyword}&page=${currentPage-1}">◀ Précédent</a>
                    </c:if>
                    <span>Page ${currentPage} / ${totalPages}</span>
                    <c:if test="${currentPage < totalPages}">
                        <a href="employe?action=${keyword != null ? 'search' : 'list'}&keyword=${keyword}&page=${currentPage+1}">Suivant ▶</a>
                    </c:if>
                </div>
            </div>
        </div>
        
        <div class="footer">
            <a href="dashboard">Retour au tableau de bord</a>
        </div>
    </div>
    
   
</body>
</html>