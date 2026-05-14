<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
        .action-btn.details { background: #0a2540; color: white; }
        .action-btn.details:hover { background: #1e3a5f; }
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
        
        /* Modal pour les détails */
        .modal {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: white;
            padding: 25px;
            border-radius: 16px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.2);
            z-index: 1000;
            width: 450px;
            max-width: 90%;
        }
        .overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);
            z-index: 999;
        }
        .modal h3 {
            margin-bottom: 15px;
            color: #0a2540;
            border-bottom: 2px solid #e2e8f0;
            padding-bottom: 10px;
        }
        .detail-row {
            display: flex;
            justify-content: space-between;
            padding: 8px 0;
            border-bottom: 1px solid #f1f5f9;
        }
        .detail-row.total {
            margin-top: 10px;
            padding-top: 10px;
            border-top: 2px solid #e2e8f0;
            font-weight: bold;
            font-size: 16px;
        }
        .detail-label {
            color: #64748b;
        }
        .detail-value {
            font-weight: 500;
            color: #1e293b;
        }
        .detail-value.positive {
            color: #10b981;
        }
        .detail-value.negative {
            color: #ef4444;
        }
        .close-modal {
            background: #e2e8f0;
            color: #1e293b;
            padding: 8px 16px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            margin-top: 15px;
            width: 100%;
        }
        .close-modal:hover {
            background: #cbd5e1;
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
                        <c:set var="tauxHoraire" value="${f.salaireBase / 173}" />
                        <c:set var="montantHS" value="${f.heuresSup * tauxHoraire * 1.25}" />
                        <c:set var="salaireBrut" value="${f.salaireBase + montantHS + f.primes}" />
                        <c:set var="cnps" value="${salaireBrut * 0.056}" />
                        <c:set var="impot" value="${salaireBrut * 0.1}" />
                        <tr>
                            <td>${f.id}</td>
                            <td>${f.employeId}</td>
                            <td>${f.mois}</td>
                            <td>${f.salaireBase} FCFA<\/td>
                            <td>${f.heuresSup} h (${montantHS} FCFA)<\/td>
                            <td>${f.primes} FCFA<\/td>
                            <td>${f.retenues} FCFA<\/td>
                            <td><strong>${f.salaireNet} FCFA</strong><\/td>
                            <td class="action-links">
                                <c:if test="${user.role == 'RH' || user.role == 'DIRECTEUR'}">
                                    <a href="fichepaie?action=edit&id=${f.id}" class="action-btn edit">✏️</a>
                                    <a href="fichepaie?action=delete&id=${f.id}" class="action-btn delete" onclick="return confirm('Supprimer ?')">🗑️</a>
                                </c:if>
                                <a href="javascript:void(0)" onclick="showDetails(${f.id}, ${f.salaireBase}, ${f.heuresSup}, ${f.primes}, ${f.retenues}, '${f.mois}')" class="action-btn details">📊 Détail</a>
                                <a href="${pageContext.request.contextPath}/pdf/fichepaie?id=${f.id}" class="action-btn pdf">📄 PDF</a>
                            <\/td>
                        </tr>
                    </c:if>
                </c:forEach>
                <c:if test="${empty fiches}">
                    <tr class="empty-row"><td colspan="9">📭 Aucune fiche de paie trouvée<\/td><\/tr>
                <\/c:if>
            <\/tbody>
        <\/table>
        
        <a href="dashboard" class="back-link">⬅️ Retour au dashboard<\/a>
    <\/div>
    
    <!-- Modal pour les détails -->
    <div id="detailsModal" class="modal">
        <h3>📄 Détail de la fiche de paie</h3>
        <div id="modalContent"></div>
        <button class="close-modal" onclick="closeModal()">Fermer</button>
    <\/div>
    <div id="modalOverlay" class="overlay" onclick="closeModal()"><\/div>
    
    <script>
        function showDetails(id, salaireBase, heuresSup, primes, retenues, mois) {
            const tauxHoraire = salaireBase / 173;
            const montantHS = heuresSup * tauxHoraire * 1.25;
            const salaireBrut = salaireBase + montantHS + primes;
            const cnps = salaireBrut * 0.056;
            const impot = salaireBrut * 0.1;
            const autresRetenues = retenues - (cnps + impot);
            
            const content = `
                <div class="detail-row">
                    <span class="detail-label">📅 Mois</span>
                    <span class="detail-value">${mois}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">💰 Salaire base</span>
                    <span class="detail-value">${salaireBase.toLocaleString()} FCFA</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">⏱️ Heures supplémentaires (${heuresSup}h)</span>
                    <span class="detail-value positive">+ ${montantHS.toLocaleString()} FCFA</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">🎁 Primes</span>
                    <span class="detail-value positive">+ ${primes.toLocaleString()} FCFA</span>
                </div>
                <div class="detail-row" style="border-top: 1px solid #e2e8f0; margin-top: 5px; padding-top: 10px;">
                    <span class="detail-label"><strong>Salaire brut</strong></span>
                    <span class="detail-value"><strong>${salaireBrut.toLocaleString()} FCFA</strong></span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">📉 Retenues détaillées :</span>
                    <span class="detail-value"></span>
                </div>
                <div class="detail-row" style="padding-left: 20px;">
                    <span class="detail-label">- CNPS (5,6%)</span>
                    <span class="detail-value negative">- ${cnps.toLocaleString()} FCFA</span>
                </div>
                <div class="detail-row" style="padding-left: 20px;">
                    <span class="detail-label">- Impôt sur salaire (10%)</span>
                    <span class="detail-value negative">- ${impot.toLocaleString()} FCFA</span>
                </div>
                <c:if test="${autresRetenues != 0}">
                <div class="detail-row" style="padding-left: 20px;">
                    <span class="detail-label">- Autres retenues</span>
                    <span class="detail-value negative">- ${autresRetenues.toLocaleString()} FCFA</span>
                </div>
                <\/c:if>
                <div class="detail-row total">
                    <span class="detail-label"><strong>Salaire net à payer</strong></span>
                    <span class="detail-value"><strong>${(salaireBrut - retenues).toLocaleString()} FCFA</strong></span>
                </div>
            `;
            
            document.getElementById('modalContent').innerHTML = content;
            document.getElementById('detailsModal').style.display = 'block';
            document.getElementById('modalOverlay').style.display = 'block';
        }
        
        function closeModal() {
            document.getElementById('detailsModal').style.display = 'none';
            document.getElementById('modalOverlay').style.display = 'none';
        }
    </script>
</body>
</html>