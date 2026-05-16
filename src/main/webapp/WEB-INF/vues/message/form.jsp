<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nouveau message - Gestion RH</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .container { max-width: 700px; margin: 0 auto; padding: 20px; }
        .card { background: white; border-radius: 16px; padding: 24px; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        .form-group { margin-bottom: 20px; }
        label { display: block; font-weight: 600; margin-bottom: 8px; color: #1e293b; }
        input, select, textarea { width: 100%; padding: 10px; border: 1px solid #e2e8f0; border-radius: 8px; }
        textarea { min-height: 150px; resize: vertical; }
        .btn-primary { background: #0a2540; color: white; padding: 10px 20px; border: none; border-radius: 8px; cursor: pointer; }
        .btn-secondary { background: #e2e8f0; color: #1e293b; padding: 10px 20px; border: none; border-radius: 8px; text-decoration: none; display: inline-block; }
        .info-note { background: #f1f5f9; padding: 12px; border-radius: 8px; margin-bottom: 20px; font-size: 13px; color: #475569; }
        .radio-group { display: flex; gap: 20px; margin-top: 8px; }
        .radio-group label { display: inline-flex; align-items: center; gap: 8px; font-weight: normal; margin: 0; }
        .destinataire-list { max-height: 200px; overflow-y: auto; border: 1px solid #e2e8f0; border-radius: 8px; padding: 10px; margin-top: 10px; }
        .destinataire-item { display: flex; align-items: center; padding: 8px; border-bottom: 1px solid #e2e8f0; }
        .destinataire-item:last-child { border-bottom: none; }
        .destinataire-item input { width: auto; margin-right: 10px; }
        .select-visible { margin-top: 10px; }
        .select-visible select { width: 100%; padding: 10px; border: 1px solid #e2e8f0; border-radius: 8px; background: white; }
        hr { margin: 15px 0; border: none; border-top: 1px solid #e2e8f0; }
    </style>
</head>
<body>
    <div class="container">
        <h1>✏️ Nouveau message</h1>
        <br>
        
        <div class="card">
            <form action="message" method="post" id="messageForm">
                <input type="hidden" name="action" value="send">
                
                <!-- ========== POUR EMPLOYÉ SEULEMENT ========== -->
                <c:if test="${sessionScope.user.role == 'EMPLOYE'}">
                    <div class="info-note">
                        💡 Votre message sera envoyé aux Ressources Humaines.
                    </div>
                    <input type="hidden" name="type" value="PRIVE">
                    
                    <div class="form-group">
                        <label>👤 Destinataire (RH)</label>
                        <div class="destinataire-list">
                            <c:forEach var="rh" items="${rhList}">
                                <div class="destinataire-item">
                                    <input type="radio" name="destinataireId" value="${rh.id}" required>
                                    <label style="margin:0;">${rh.nom} ${rh.prenom} (RH)</label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
                
                <!-- ========== POUR RH OU DIRECTEUR ========== -->
                <c:if test="${sessionScope.user.role == 'RH' or sessionScope.user.role == 'DIRECTEUR'}">
                    <div class="form-group">
                        <label>📌 Type de message</label>
                        <select name="type" id="type" onchange="toggleDestinataire()">
                            <option value="GENERAL">📢 Annonce générale (à tous les employés)</option>
                            <option value="PRIVE">💬 Message privé</option>
                        </select>
                    </div>
                    
                    <div id="destinataireSection" style="display: block;">
                        <div class="form-group">
                            <label>👤 Destinataire</label>
                            <div class="radio-group">
                                <label>
                                    <input type="radio" name="destinataireMode" value="ALL" onchange="toggleSelect()">
                                    Tous les employés
                                </label>
                                <label>
                                    <input type="radio" name="destinataireMode" value="SELECT" checked onchange="toggleSelect()">
                                    Sélectionner un employé
                                </label>
                            </div>
                            
                            <div id="employeSelectDiv" class="select-visible">
                                <select name="destinataireId" required>
                                    <option value="">-- Sélectionner un employé --</option>
                                    <c:forEach var="e" items="${employes}">
                                        <option value="${e.id}">${e.nom} ${e.prenom}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </c:if>
                
                <div class="form-group">
                    <label>📝 Sujet</label>
                    <input type="text" name="sujet" required>
                </div>
                
                <div class="form-group">
                    <label>💬 Message</label>
                    <textarea name="contenu" required></textarea>
                </div>
                
                <hr>
                
                <div style="display: flex; gap: 10px;">
                    <button type="submit" class="btn-primary"> Envoyer</button>
                    <a href="message?action=list" class="btn-secondary"> Annuler</a>
                </div>
            </form>
        </div>
    </div>
    
    <script>
        function toggleDestinataire() {
            var type = document.getElementById('type').value;
            var destinataireSection = document.getElementById('destinataireSection');
            if (type === 'GENERAL') {
                destinataireSection.style.display = 'none';
                // Désactiver la validation du select
                var select = document.querySelector('select[name="destinataireId"]');
                if (select) select.removeAttribute('required');
            } else {
                destinataireSection.style.display = 'block';
                var select = document.querySelector('select[name="destinataireId"]');
                if (select && document.querySelector('input[name="destinataireMode"]:checked').value === 'SELECT') {
                    select.setAttribute('required', 'required');
                }
            }
        }
        
        function toggleSelect() {
            var mode = document.querySelector('input[name="destinataireMode"]:checked').value;
            var selectDiv = document.getElementById('employeSelectDiv');
            var select = document.querySelector('select[name="destinataireId"]');
            
            if (mode === 'SELECT') {
                selectDiv.style.display = 'block';
                select.setAttribute('required', 'required');
            } else {
                selectDiv.style.display = 'none';
                select.removeAttribute('required');
                select.value = '';
            }
        }
        
        // Initialisation
        toggleDestinataire();
        toggleSelect();
    </script>
</body>
</html>