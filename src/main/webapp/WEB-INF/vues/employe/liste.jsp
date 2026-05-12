<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des employés</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .search-bar {
            display: inline-block;
            margin-right: 10px;
        }
        .pagination {
            margin-top: 20px;
            text-align: center;
        }
        .pagination a {
            display: inline-block;
            padding: 5px 10px;
            margin: 0 5px;
            background: #667eea;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .pagination a:hover {
            background: #5a67d8;
        }
        .pagination span {
            display: inline-block;
            padding: 5px 10px;
            margin: 0 5px;
        }
        .info {
            margin-top: 10px;
            text-align: center;
            color: #666;
        }
        .btn-group {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }
        .btn-group .btn {
            margin: 0;
        }
        /* Modal styles */
        .modal {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.3);
            z-index: 1000;
            width: 350px;
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
        }
        .modal input, .modal textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .modal button {
            margin-right: 10px;
        }
        .action-links a {
            margin: 0 3px;
            display: inline-block;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Liste des employés</h1>
        
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; flex-wrap: wrap; gap: 10px;">
            <div class="btn-group">
                <a href="employe?action=add" class="btn">➕ Ajouter</a>
                <a href="${pageContext.request.contextPath}/csv/employes" class="btn">📥 Exporter CSV</a>
                <a href="${pageContext.request.contextPath}/pdf/employes" class="btn">📄 Exporter PDF</a>
                <button onclick="openSmsModal()" class="btn">📱 Envoyer SMS</button>
            </div>
            
            <form action="employe" method="get" style="display: flex;">
                <input type="hidden" name="action" value="search">
                <input type="text" name="keyword" placeholder="🔍 Rechercher..." value="${keyword}" style="padding: 8px; width: 200px;">
                <button type="submit" style="margin-left: 5px;">OK</button>
            </form>
        </div>
        
        <c:if test="${keyword != null && keyword != ''}">
            <div class="info">Résultats pour : "<strong>${keyword}</strong>" (${totalRecords} employé(s) trouvé(s))</div>
        </c:if>
        
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Matricule</th>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Poste</th>
                    <th>Département</th>
                    <th>Date embauche</th>
                    <th>Salaire base</th>
                    <th>Contrat</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="e" items="${employes}">
                <tr>
                    <td>${e.id}</td>
                    <td>${e.matricule}</td>
                    <td>${e.nom}</td>
                    <td>${e.prenom}</td>
                    <td>${e.poste}</td>
                    <td>${e.departementNom}</td>
                    <td>${e.dateEmbauche}</td>
                    <td>${e.salaireBase} FCFA</td>
                    <td>${e.typeContrat}</td>
                    <td class="action-links">
                        <a href="employe?action=view&id=${e.id}" class="view">👁️ Voir</a>
                        <a href="employe?action=edit&id=${e.id}" class="edit">✏️ Modifier</a>
                        <a href="employe?action=delete&id=${e.id}" class="delete" onclick="return confirm('Supprimer cet employé ?')">🗑️ Supprimer</a>
                    </td>
                </tr>
                </c:forEach>
                
                <c:if test="${empty employes}">
                    <tr>
                        <td colspan="10" style="text-align: center;">Aucun employé trouvé</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        
        <!-- Pagination -->
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="employe?action=${keyword != null ? 'search' : 'list'}&keyword=${keyword}&page=${currentPage-1}">◀ Précédent</a>
            </c:if>
            
            <span>Page ${currentPage} / ${totalPages}</span>
            
            <c:if test="${currentPage < totalPages}">
                <a href="employe?action=${keyword != null ? 'search' : 'list'}&keyword=${keyword}&page=${currentPage+1}">Suivant ▶</a>
            </c:if>
        </div>
        
        <div class="info">
            📊 Total : ${totalRecords} employé(s) | Page ${currentPage} sur ${totalPages}
        </div>
        
        <br>
        <a href="dashboard">⬅️ Retour au dashboard</a>
    </div>
    
    <!-- Modal SMS -->
    <div id="smsModal" class="modal">
        <h3>📱 Envoyer un SMS</h3>
        <form action="${pageContext.request.contextPath}/sms/send" method="post">
            <label>Numéro :</label>
            <input type="text" name="phoneNumber" placeholder="Ex: 691234567" required>
            
            <label>Message :</label>
            <textarea name="message" rows="3" placeholder="Votre message..." required></textarea>
            
            <button type="submit" style="background:#28a745;">✅ Envoyer</button>
            <button type="button" onclick="closeSmsModal()" style="background:#6c757d;">❌ Annuler</button>
        </form>
    </div>
    
    <!-- Overlay -->
    <div id="smsOverlay" class="overlay"></div>
    
    <script>
        function openSmsModal() {
            document.getElementById('smsModal').style.display = 'block';
            document.getElementById('smsOverlay').style.display = 'block';
        }
        function closeSmsModal() {
            document.getElementById('smsModal').style.display = 'none';
            document.getElementById('smsOverlay').style.display = 'none';
        }
    </script>
</body>
</html>