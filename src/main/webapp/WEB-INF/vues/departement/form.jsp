<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire Département</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            background: #f0f2f5;
            font-family: 'Inter', 'Segoe UI', system-ui, sans-serif;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding: 40px 20px;
        }
        .form-container { max-width: 600px; width: 100%; margin: 0 auto; }
        .main-card { background: white; border-radius: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.08); overflow: hidden; }
        .card-header { background: #0a2540; padding: 24px 28px; text-align: center; }
        .card-header h1 { font-size: 22px; font-weight: 600; color: white; margin-bottom: 6px; }
        .card-header p { font-size: 13px; color: rgba(255,255,255,0.7); }
        .card-body { padding: 32px 28px; }
        .form-group { margin-bottom: 24px; }
        label { display: block; font-size: 13px; font-weight: 600; color: #1e293b; margin-bottom: 8px; }
        input { width: 100%; padding: 12px 14px; border: 1px solid #e2e8f0; border-radius: 12px; font-size: 14px; font-family: inherit; transition: all 0.2s; }
        input:focus { outline: none; border-color: #0a2540; box-shadow: 0 0 0 3px rgba(10,37,64,0.1); }
        .section-title { font-size: 15px; font-weight: 700; color: #0a2540; margin-bottom: 16px; padding-bottom: 8px; border-bottom: 2px solid #e2e8f0; }
        .poste-item { display: flex; gap: 10px; margin-bottom: 12px; align-items: center; }
        .poste-item input { margin-bottom: 0; }
        .btn-remove { background: #fee2e2; color: #dc2626; border: none; border-radius: 8px; padding: 10px 14px; cursor: pointer; font-size: 16px; flex-shrink: 0; }
        .btn-remove:hover { background: #fecaca; }
        .btn-add-poste { background: #f0f9ff; color: #0369a1; border: 2px dashed #0369a1; border-radius: 10px; padding: 10px 20px; cursor: pointer; font-size: 14px; font-weight: 600; width: 100%; margin-top: 8px; }
        .btn-add-poste:hover { background: #e0f2fe; }
        .form-actions { display: flex; gap: 12px; margin-top: 32px; }
        .btn { flex: 1; padding: 12px 20px; border-radius: 12px; font-size: 14px; font-weight: 600; text-decoration: none; text-align: center; transition: all 0.2s; cursor: pointer; border: none; }
        .btn-primary { background: #0a2540; color: white; }
        .btn-primary:hover { background: #1e3a5f; }
        .btn-secondary { background: #f1f5f9; color: #1e293b; }
        .btn-secondary:hover { background: #e2e8f0; }
    </style>
</head>
<body>
    <div class="form-container">
        <div class="main-card">
            <div class="card-header">
                <h1><c:if test="${departement == null}">➕ Ajouter un département</c:if><c:if test="${departement != null}">✏️ Modifier le département</c:if></h1>
                <p>Veuillez renseigner les informations ci-dessous</p>
            </div>
            <div class="card-body">
                <form action="departement" method="post">
                    <input type="hidden" name="action" value="${departement == null ? 'save' : 'update'}">
                    <c:if test="${departement != null}">
                        <input type="hidden" name="id" value="${departement.id}">
                    </c:if>

                    <div class="form-group">
                        <label>🏷️ Nom du département</label>
                        <input type="text" name="nom" value="${departement.nom}" placeholder="Ex: Informatique, Finance, RH..." required>
                    </div>

                    <div class="form-group">
                        <label>👤 Responsable</label>
                        <input type="text" name="responsable" value="${departement.responsable}" placeholder="Nom du responsable" required>
                    </div>

                    <div class="form-group">
                        <label>💰 Budget salaire (FCFA)</label>
                        <input type="number" step="0.01" name="budgetSalaire" value="${departement.budgetSalaire}" placeholder="Ex: 150000000" required>
                    </div>

                    <!-- Section Postes -->
                    <div class="form-group">
                        <div class="section-title">💼 Postes du département</div>
                        <div id="postes-container">
                            <c:choose>
                                <c:when test="${not empty postes}">
                                    <c:forEach var="poste" items="${postes}">
                                        <div class="poste-item">
                                            <input type="text" name="posteTitre" value="${poste.titre}" placeholder="Titre du poste (ex: Développeur)" required>
                                            <input type="text" name="posteDescription" value="${poste.description}" placeholder="Description (optionnel)">
                                            <button type="button" class="btn-remove" onclick="removePoste(this)">🗑️</button>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <div class="poste-item">
                                        <input type="text" name="posteTitre" placeholder="Titre du poste (ex: Développeur)" required>
                                        <input type="text" name="posteDescription" placeholder="Description (optionnel)">
                                        <button type="button" class="btn-remove" onclick="removePoste(this)">🗑️</button>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <button type="button" class="btn-add-poste" onclick="addPoste()">➕ Ajouter un poste</button>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">✅ Enregistrer</button>
                        <a href="departement?action=list" class="btn btn-secondary">❌ Annuler</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        function addPoste() {
            const container = document.getElementById('postes-container');
            const div = document.createElement('div');
            div.className = 'poste-item';
            div.innerHTML = `
                <input type="text" name="posteTitre" placeholder="Titre du poste (ex: Développeur)" required>
                <input type="text" name="posteDescription" placeholder="Description (optionnel)">
                <button type="button" class="btn-remove" onclick="removePoste(this)">🗑️</button>
            `;
            container.appendChild(div);
        }

        function removePoste(btn) {
            const container = document.getElementById('postes-container');
            if (container.children.length > 1) {
                btn.parentElement.remove();
            }
        }
    </script>
</body>
</html>