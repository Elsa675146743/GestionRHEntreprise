<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire contrat</title>
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
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 40px 20px;
        }
        
        .form-container {
            max-width: 600px;
            width: 100%;
            margin: 0 auto;
        }
        
        .main-card {
            background: white;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.08);
            overflow: hidden;
        }
        
        .card-header {
            background: #0a2540;
            padding: 24px 28px;
            text-align: center;
        }
        
        .card-header h1 {
            font-size: 22px;
            font-weight: 600;
            color: white;
            margin-bottom: 6px;
        }
        
        .card-header p {
            font-size: 13px;
            color: rgba(255,255,255,0.7);
        }
        
        .card-body {
            padding: 32px 28px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            font-size: 13px;
            font-weight: 600;
            color: #1e293b;
            margin-bottom: 8px;
        }
        
        input, select, textarea {
            width: 100%;
            padding: 12px 14px;
            border: 1px solid #e2e8f0;
            border-radius: 12px;
            font-size: 14px;
            font-family: inherit;
            transition: all 0.2s;
        }
        
        input:focus, select:focus, textarea:focus {
            outline: none;
            border-color: #0a2540;
            box-shadow: 0 0 0 3px rgba(10,37,64,0.1);
        }
        
        textarea {
            resize: vertical;
            min-height: 80px;
        }
        
        .info-card {
            background: #f8fafc;
            border-radius: 12px;
            padding: 15px;
            margin-bottom: 20px;
            border-left: 4px solid #0a2540;
        }
        
        .info-card p {
            margin: 5px 0;
            font-size: 13px;
        }
        
        .form-actions {
            display: flex;
            gap: 12px;
            margin-top: 28px;
        }
        
        .btn {
            flex: 1;
            padding: 12px 20px;
            border-radius: 12px;
            font-size: 14px;
            font-weight: 600;
            text-decoration: none;
            text-align: center;
            transition: all 0.2s;
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
            background: #f1f5f9;
            color: #1e293b;
        }
        
        .btn-secondary:hover {
            background: #e2e8f0;
            transform: translateY(-1px);
        }
        
        @media (max-width: 480px) {
            .card-header, .card-body {
                padding: 24px 20px;
            }
            .form-actions {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
    <div class="form-container">
        <div class="main-card">
            <div class="card-header">
                <h1><c:if test="${contrat == null}">➕ Ajouter un contrat</c:if><c:if test="${contrat != null}">✏️ Modifier le contrat</c:if></h1>
                <p>Veuillez renseigner les informations ci-dessous</p>
            </div>
            
            <div class="card-body">
                <form action="contrat" method="post" id="contratForm">
                    <input type="hidden" name="action" value="${contrat == null ? 'save' : 'update'}">
                    <c:if test="${contrat != null}">
                        <input type="hidden" name="id" value="${contrat.id}">
                    </c:if>
                    
                    <div class="form-group">
                        <label>👤 Employé</label>
                        <select name="employeId" id="employeId" required>
                            <option value="">Sélectionner un employé</option>
                            <c:forEach var="e" items="${employes}">
                                <option value="${e.id}" 
                                    data-salaire="${e.salaireBase}"
                                    data-dateembauche="${e.dateEmbauche}"
                                    data-typecontrat="${e.typeContrat}"
                                    data-nom="${e.nom} ${e.prenom}"
                                    ${contrat.employeId == e.id ? 'selected' : ''}>
                                    ${e.nom} ${e.prenom} (${e.matricule})
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <!-- Zone d'affichage des infos de l'employé -->
                    <div id="employeInfo" class="info-card" style="display: none;">
                        <p><strong>📌 Employé sélectionné :</strong> <span id="infoNom"></span></p>
                        <p><strong>💰 Salaire actuel :</strong> <span id="infoSalaire"></span> FCFA</p>
                        <p><strong>📅 Date d'embauche :</strong> <span id="infoDateEmbauche"></span></p>
                        <p><strong>📄 Contrat actuel :</strong> <span id="infoTypeContrat"></span></p>
                    </div>
                    
                    <div class="form-group">
                        <label>📄 Type contrat</label>
                        <select name="typeContrat" required>
                            <option value="CDI" ${contrat.typeContrat == 'CDI' ? 'selected' : ''}>CDI - Contrat à durée indéterminée</option>
                            <option value="CDD" ${contrat.typeContrat == 'CDD' ? 'selected' : ''}>CDD - Contrat à durée déterminée</option>
                            <option value="STAGE" ${contrat.typeContrat == 'STAGE' ? 'selected' : ''}>STAGE - Convention de stage</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label>📅 Date début</label>
                        <input type="date" name="dateDebut" id="dateDebut" value="${contrat.dateDebut}" required>
                    </div>
                    
                    <div class="form-group">
                        <label>📅 Date fin</label>
                        <input type="date" name="dateFin" value="${contrat.dateFin}">
                        <small style="font-size: 11px; color: #6c757d;">Laissez vide pour CDI</small>
                    </div>
                    
                    <div class="form-group">
                        <label>💰 Salaire (FCFA)</label>
                        <input type="number" step="0.01" name="salaire" id="salaire" value="${contrat.salaire}" placeholder="Ex: 500000" required>
                    </div>
                    
                    <div class="form-group">
                        <label>🎁 Avantages (optionnel)</label>
                        <textarea name="avantages" rows="3" placeholder="Ex: Transport, Logement, Prime...">${contrat.avantages}</textarea>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary"> Enregistrer</button>
                        <a href="contrat?action=list" class="btn btn-secondary"> Annuler</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <script>
        // Récupérer les données des employés depuis les options
        const employesData = {};
        <c:forEach var="e" items="${employes}">
            employesData[${e.id}] = {
                salaire: ${e.salaireBase},
                dateEmbauche: "${e.dateEmbauche}",
                typeContrat: "${e.typeContrat}",
                nom: "${e.nom} ${e.prenom}"
            };
        </c:forEach>
        
        // Fonction pour mettre à jour le formulaire quand on sélectionne un employé
        document.getElementById('employeId').addEventListener('change', function() {
            const employeId = this.value;
            const infoDiv = document.getElementById('employeInfo');
            
            if (employeId && employesData[employeId]) {
                const data = employesData[employeId];
                
                // Afficher les infos
                document.getElementById('infoNom').textContent = data.nom;
                document.getElementById('infoSalaire').textContent = data.salaire;
                document.getElementById('infoDateEmbauche').textContent = data.dateEmbauche;
                document.getElementById('infoTypeContrat').textContent = data.typeContrat;
                infoDiv.style.display = 'block';
                
                // Pré-remplir les champs
                document.getElementById('dateDebut').value = data.dateEmbauche;
                document.getElementById('salaire').value = data.salaire;
            } else {
                infoDiv.style.display = 'none';
            }
        });
        
        // Déclencher le changement si un employé est déjà sélectionné
        <c:if test="${contrat.employeId != null}">
            document.getElementById('employeId').dispatchEvent(new Event('change'));
        </c:if>
    </script>
</body>
</html>