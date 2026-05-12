<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord statistiques</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin: 20px 0;
        }
        .stat-card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            text-align: center;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .stat-card h3 {
            margin: 0 0 10px 0;
            color: #667eea;
        }
        .stat-card .value {
            font-size: 28px;
            font-weight: bold;
            color: #333;
        }
        .stat-card.green .value { color: #28a745; }
        .stat-card.red .value { color: #dc3545; }
        .stat-card.orange .value { color: #fd7e14; }
        
        table {
            margin-top: 20px;
        }
        .progress-bar {
            background: #e0e0e0;
            border-radius: 10px;
            height: 20px;
            overflow: hidden;
        }
        .progress-fill {
            background: #28a745;
            height: 100%;
            border-radius: 10px;
            width: 0%;
        }
        .progress-fill.warning { background: #fd7e14; }
        .progress-fill.danger { background: #dc3545; }
        
        .btn-group {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
            margin-bottom: 20px;
            justify-content: space-between;
            align-items: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="btn-group">
            <h1>Tableau de bord - Statistiques RH</h1>
            <a href="${pageContext.request.contextPath}/pdf/rapport" class="btn">📊 PDF Rapport mensuel</a>
        </div>
        
        <div class="stats-grid">
            <div class="stat-card ${masseSalarialeTotale > 5000000 ? 'orange' : 'green'}">
                <h3>Masse salariale totale</h3>
                <div class="value">${masseSalarialeTotale} FCFA</div>
            </div>
            <div class="stat-card">
                <h3>Nombre d'employés</h3>
                <div class="value">${nbEmployes}</div>
            </div>
            <div class="stat-card">
                <h3>Nombre de départements</h3>
                <div class="value">${nbDepartements}</div>
            </div>
            <div class="stat-card">
                <h3>Salaire moyen</h3>
                <div class="value">${salaireMoyen} FCFA</div>
            </div>
        </div>
        
        <h2>Statistiques par mois</h2>
        <table border="1">
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
                        <span style="color: ${pourcent >= 0 ? 'green' : 'red'}">
                            ${pourcent >= 0 ? '+' : ''}${pourcent}%
                        </span>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <h2>Statistiques par département</h2>
        <table border="1">
            <thead>
                <tr>
                    <th>Département</th>
                    <th>Nombre d'employés</th>
                    <th>Masse salariale</th>
                    <th>Proportion</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="stat" items="${statsParDepartement}">
                    <c:set var="pourcentage" value="${masseSalarialeTotale > 0 ? stat[2] / masseSalarialeTotale * 100 : 0}" />
                    <c:set var="progressClass" value="${pourcentage > 50 ? 'danger' : (pourcentage > 30 ? 'warning' : '')}" />
                    <tr>
                        <td>${stat[0]}</td>
                        <td>${stat[1]}</td>
                        <td>${stat[2]} FCFA</td>
                        <td>
                            ${pourcentage}%
                            <div class="progress-bar">
                                <div class="progress-fill ${progressClass}" style="width: ${pourcentage}%;"></div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <br>
        <a href="dashboard">⬅️ Retour au dashboard principal</a>
    </div>
</body>
</html>