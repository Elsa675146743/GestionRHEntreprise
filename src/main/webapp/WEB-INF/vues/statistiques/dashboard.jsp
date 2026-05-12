<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord statistiques</title>
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
        
        .stats-container {
            max-width: 1200px;
            width: 100%;
            margin: 0 auto;
        }
        
        /* Carte principale */
        .main-card {
            background: white;
            border-radius: 24px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.08);
            overflow: hidden;
        }
        
        /* En-tête */
        .card-header {
            padding: 28px 32px;
            background: linear-gradient(135deg, #0a2540 0%, #1e3a5f 100%);
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            gap: 16px;
        }
        
        .card-header h1 {
            font-size: 24px;
            font-weight: 600;
            color: white;
            margin: 0;
        }
        
        .btn-pdf {
            background: rgba(255,255,255,0.15);
            color: white;
            padding: 10px 20px;
            border-radius: 10px;
            text-decoration: none;
            font-size: 13px;
            font-weight: 500;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            transition: all 0.2s;
        }
        
        .btn-pdf:hover {
            background: rgba(255,255,255,0.25);
            transform: translateY(-1px);
        }
        
        /* Corps */
        .card-body {
            padding: 32px;
        }
        
        /* Grille de statistiques */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            margin-bottom: 40px;
        }
        
        .stat-card {
            background: #f8f9fa;
            border-radius: 16px;
            padding: 20px;
            text-align: center;
            transition: all 0.2s;
        }
        
        .stat-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }
        
        .stat-card h3 {
            font-size: 13px;
            font-weight: 600;
            color: #64748b;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin-bottom: 12px;
        }
        
        .stat-card .value {
            font-size: 28px;
            font-weight: 700;
        }
        
        /* Couleurs des cartes */
        .stat-card.masse .value { color: #1e3a5f; }
        .stat-card.employes .value { color: #0a2540; }
        .stat-card.departements .value { color: #2c3e50; }
        .stat-card.salaire .value { color: #1e3a5f; }
        
        /* Sections */
        .section-title {
            font-size: 18px;
            font-weight: 600;
            color: #1e293b;
            margin: 32px 0 20px 0;
            padding-bottom: 12px;
            border-bottom: 2px solid #e2e8f0;
        }
        
        .section-title:first-of-type {
            margin-top: 0;
        }
        
        /* Tableaux */
        .table-wrapper {
            overflow-x: auto;
            border-radius: 12px;
            border: 1px solid #e2e8f0;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
        }
        
        th {
            background: #f8fafc;
            padding: 14px 16px;
            text-align: left;
            font-size: 12px;
            font-weight: 600;
            color: #1e293b;
            text-transform: uppercase;
            border-bottom: 1px solid #e2e8f0;
        }
        
        td {
            padding: 12px 16px;
            font-size: 14px;
            color: #1e293b;
            border-bottom: 1px solid #f1f5f9;
        }
        
        tr:hover td {
            background: #fafcff;
        }
        
        .evolution-positive {
            color: #10b981;
            font-weight: 500;
        }
        
        .evolution-negative {
            color: #ef4444;
            font-weight: 500;
        }
        
        .progress-bar {
            background: #e2e8f0;
            border-radius: 20px;
            height: 8px;
            overflow: hidden;
            margin-top: 8px;
        }
        
        .progress-fill {
            background: #10b981;
            height: 100%;
            border-radius: 20px;
            width: 0%;
        }
        
        .progress-fill.warning { background: #f59e0b; }
        .progress-fill.danger { background: #ef4444; }
        
        .percentage-text {
            font-size: 13px;
            font-weight: 500;
            color: #1e293b;
        }
        
        /* Footer */
        .card-footer {
            padding: 20px 32px;
            border-top: 1px solid #e2e8f0;
            background: #fafbfc;
        }
        
        .card-footer a {
            color: #64748b;
            text-decoration: none;
            font-size: 13px;
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }
        
        .card-footer a:hover {
            color: #0a2540;
        }
        
        /* Empty state */
        .empty-row td {
            text-align: center;
            padding: 40px;
            color: #94a3b8;
        }
        
        @media (max-width: 768px) {
            .card-header, .card-body, .card-footer {
                padding: 24px;
            }
            .stats-grid {
                grid-template-columns: repeat(2, 1fr);
                gap: 16px;
            }
        }
        
        @media (max-width: 480px) {
            .stats-grid {
                grid-template-columns: 1fr;
            }
            .card-header {
                flex-direction: column;
                text-align: center;
            }
        }
    </style>
</head>
<body>
    <div class="stats-container">
        <div class="main-card">
            <div class="card-header">
                <h1>📊 Tableau de bord - Statistiques RH</h1>
                <a href="${pageContext.request.contextPath}/pdf/rapport" class="btn-pdf">📄 PDF Rapport mensuel</a>
            </div>
            
            <div class="card-body">
                <!-- Cartes statistiques -->
                <div class="stats-grid">
                    <div class="stat-card masse">
                        <h3>💰 Masse salariale</h3>
                        <div class="value">${masseSalarialeTotale} FCFA</div>
                    </div>
                    <div class="stat-card employes">
                        <h3>👥 Employés</h3>
                        <div class="value">${nbEmployes}</div>
                    </div>
                    <div class="stat-card departements">
                        <h3>🏢 Départements</h3>
                        <div class="value">${nbDepartements}</div>
                    </div>
                    <div class="stat-card salaire">
                        <h3>📊 Salaire moyen</h3>
                        <div class="value">${salaireMoyen} FCFA</div>
                    </div>
                </div>
                
                <!-- Statistiques par mois -->
                <div class="section-title">📅 Évolution mensuelle</div>
                <div class="table-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <th>Mois</th>
                                <th>Nombre de fiches</th>
                                <th>Masse salariale</th>
                                <th>Évolution</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="stat" items="${statsParMois}" varStatus="status">
                                <tr>
                                    <td>${stat[0]}</td>
                                    <td>${stat[1]}</td>
                                    <td>${stat[2]} FCFA</td>
                                    <td>
                                        <c:set var="pourcent" value="${status.index > 0 ? (stat[2] - statsParMois[status.index-1][2]) / statsParMois[status.index-1][2] * 100 : 0}" />
                                        <span class="${pourcent >= 0 ? 'evolution-positive' : 'evolution-negative'}">
                                            ${pourcent >= 0 ? '+' : ''}${pourcent}%
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty statsParMois}">
                                <tr class="empty-row">
                                    <td colspan="4">📭 Aucune donnée disponible</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
                
                <!-- Statistiques par département -->
                <div class="section-title">🏢 Répartition par département</div>
                <div class="table-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <th>Département</th>
                                <th>Employés</th>
                                <th>Masse salariale</th>
                                <th>Proportion</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="stat" items="${statsParDepartement}">
                                <c:set var="pourcentage" value="${masseSalarialeTotale > 0 ? stat[2] / masseSalarialeTotale * 100 : 0}" />
                                <c:set var="progressClass" value="${pourcentage > 50 ? 'danger' : (pourcentage > 30 ? 'warning' : '')}" />
                                <tr>
                                    <td><strong>${stat[0]}</strong></td>
                                    <td>${stat[1]}</td>
                                    <td>${stat[2]} FCFA</td>
                                    <td style="min-width: 150px;">
                                        <span class="percentage-text">${pourcentage}%</span>
                                        <div class="progress-bar">
                                            <div class="progress-fill ${progressClass}" style="width: ${pourcentage}%;"></div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty statsParDepartement}">
                                <tr class="empty-row">
                                    <td colspan="4">📭 Aucune donnée disponible</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            
            <div class="card-footer">
                <a href="dashboard">⬅️ Retour au tableau de bord principal</a>
            </div>
        </div>
    </div>
</body>
</html>