<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire fiche de paie</title>
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
            max-width: 700px;
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
        
        .info-card {
            background: #f0fdf4;
            border-radius: 12px;
            padding: 15px;
            margin-bottom: 20px;
            border-left: 4px solid #10b981;
        }
        
        .info-card p {
            margin: 5px 0;
            font-size: 13px;
        }
        
        .form-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group-full {
            grid-column: span 2;
        }
        
        label {
            display: block;
            font-size: 13px;
            font-weight: 600;
            color: #1e293b;
            margin-bottom: 8px;
        }
        
        input, select {
            width: 100%;
            padding: 12px 14px;
            border: 1px solid #e2e8f0;
            border-radius: 12px;
            font-size: 14px;
            font-family: inherit;
            transition: all 0.2s;
        }
        
        input:focus, select:focus {
            outline: none;
            border-color: #0a2540;
            box-shadow: 0 0 0 3px rgba(10,37,64,0.1);
        }
        
        .time-input {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .time-input input {
            width: 80px;
            text-align: center;
        }
        
        .result-display {
            background: #f8fafc;
            border-radius: 12px;
            padding: 15px;
            margin-top: 20px;
            text-align: center;
        }
        
        .result-display h3 {
            font-size: 14px;
            color: #64748b;
            margin-bottom: 5px;
        }
        
        .result-display .net {
            font-size: 24px;
            font-weight: 700;
            color: #0a2540;
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
        
        @media (max-width: 640px) {
            .form-grid {
                grid-template-columns: 1fr;
            }
            .form-group-full {
                grid-column: span 1;
            }
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
                <h1><c:if test="${fiche == null}">➕ Ajouter une fiche de paie</c:if><c:if test="${fiche != null}">✏️ Modifier la fiche de paie</c:if></h1>
                <p>Veuillez renseigner les informations ci-dessous</p>
            </div>
            
            <div class="card-body">
                <form action="fichepaie" method="post" id="paieForm">
                    <input type="hidden" name="action" value="${fiche == null ? 'save' : 'update'}">
                    <c:if test="${fiche != null}">
                        <input type="hidden" name="id" value="${fiche.id}">
                    </c:if>
                    
                    <div class="form-group">
                        <label>👤 Employé</label>
                        <select name="employeId" id="employeId" required>
                            <option value="">Sélectionner un employé</option>
                            <c:forEach var="e" items="${employes}">
                                <option value="${e.id}" 
                                    data-salaire="${e.salaireBase}"
                                    data-nom="${e.nom} ${e.prenom}"
                                    ${fiche.employeId == e.id ? 'selected' : ''}>
                                    ${e.nom} ${e.prenom} (${e.matricule})
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <!-- Zone d'information employé -->
                    <div id="employeInfo" class="info-card" style="display: none;">
                        <p><strong>📌 Employé :</strong> <span id="infoNom"></span></p>
                        <p><strong>💰 Salaire contractuel :</strong> <span id="infoSalaire"></span> FCFA</p>
                    </div>
                    
                    <div class="form-grid">
                        <div class="form-group">
                            <label>📅 Mois</label>
                            <input type="month" name="mois" id="mois" value="${fiche.mois}" required>
                        </div>
                        
                        <div class="form-group">
                            <label>💰 Salaire base (FCFA)</label>
                            <input type="number" step="0.01" name="salaireBase" id="salaireBase" value="${fiche.salaireBase}" required>
                        </div>
                        
                        <div class="form-group">
                            <label>⏱️ Heures supplémentaires</label>
                            <div class="time-input">
                                <input type="number" step="0.5" name="heuresSup" id="heuresSup" value="${fiche.heuresSup}" placeholder="0">
                                <span>heures</span>
                                <button type="button" id="addHourBtn" class="btn" style="padding: 8px 12px; background: #e2e8f0;">+1h</button>
                                <button type="button" id="subHourBtn" class="btn" style="padding: 8px 12px; background: #e2e8f0;">-1h</button>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label>🎁 Primes (FCFA)</label>
                            <input type="number" step="0.01" name="primes" id="primes" value="${fiche.primes}" placeholder="0">
                        </div>
                        
                        <div class="form-group">
                            <label>📉 Retenues (FCFA)</label>
                            <input type="number" step="0.01" name="retenues" id="retenues" value="${fiche.retenues}" placeholder="0">
                        </div>
                        
                        <div class="form-group">
                            <label>📊 Montant heures sup (calculé)</label>
                            <input type="text" id="montantHeuresSup" readonly style="background:#f8fafc;">
                        </div>
                    </div>
                    
                    <!-- Résultat du calcul -->
                    <div class="result-display">
                        <h3>Salaire net à payer</h3>
                        <div class="net" id="salaireNet">0 FCFA</div>
                        <small>Calculé automatiquement</small>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary"> Enregistrer</button>
                        <a href="fichepaie?action=list" class="btn btn-secondary"> Annuler</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <script>
        // Données des employés
        const employesData = {};
        <c:forEach var="e" items="${employes}">
            employesData[${e.id}] = {
                salaire: ${e.salaireBase},
                nom: "${e.nom} ${e.prenom}"
            };
        </c:forEach>
        
        // Éléments du formulaire
        const employeSelect = document.getElementById('employeId');
        const salaireBaseInput = document.getElementById('salaireBase');
        const heuresSupInput = document.getElementById('heuresSup');
        const primesInput = document.getElementById('primes');
        const retenuesInput = document.getElementById('retenues');
        const montantHeuresSupSpan = document.getElementById('montantHeuresSup');
        const salaireNetSpan = document.getElementById('salaireNet');
        const infoDiv = document.getElementById('employeInfo');
        const infoNom = document.getElementById('infoNom');
        const infoSalaire = document.getElementById('infoSalaire');
        
        // Fonction de calcul
        function calculerSalaire() {
            const salaireBase = parseFloat(salaireBaseInput.value) || 0;
            const heuresSup = parseFloat(heuresSupInput.value) || 0;
            const primes = parseFloat(primesInput.value) || 0;
            const retenues = parseFloat(retenuesInput.value) || 0;
            
            // Taux horaire = salaire base / 173h (mensuel légal)
            const tauxHoraire = salaireBase / 173;
            const montantHS = heuresSup * tauxHoraire * 1.25;
            
            const salaireBrut = salaireBase + montantHS + primes;
            const salaireNet = salaireBrut - retenues;
            
            montantHeuresSupSpan.value = montantHS.toFixed(0) + " FCFA";
            salaireNetSpan.innerHTML = salaireNet.toFixed(0) + " FCFA";
            
            return salaireNet;
        }
        
        // Mise à jour quand on sélectionne un employé
        employeSelect.addEventListener('change', function() {
            const employeId = this.value;
            
            if (employeId && employesData[employeId]) {
                const data = employesData[employeId];
                infoNom.textContent = data.nom;
                infoSalaire.textContent = data.salaire;
                infoDiv.style.display = 'block';
                
                // Remplir automatiquement le salaire base
                salaireBaseInput.value = data.salaire;
                calculerSalaire();
            } else {
                infoDiv.style.display = 'none';
            }
        });
        
        // Écouteurs pour recalculer
        salaireBaseInput.addEventListener('input', calculerSalaire);
        heuresSupInput.addEventListener('input', calculerSalaire);
        primesInput.addEventListener('input', calculerSalaire);
        retenuesInput.addEventListener('input', calculerSalaire);
        
        // Boutons +1h et -1h
        document.getElementById('addHourBtn').addEventListener('click', function() {
            let val = parseFloat(heuresSupInput.value) || 0;
            heuresSupInput.value = val + 1;
            calculerSalaire();
        });
        
        document.getElementById('subHourBtn').addEventListener('click', function() {
            let val = parseFloat(heuresSupInput.value) || 0;
            if (val > 0) {
                heuresSupInput.value = val - 1;
                calculerSalaire();
            }
        });
        
        // Déclencher le calcul au chargement
        calculerSalaire();
        
        // Déclencher le changement si un employé est déjà sélectionné
        <c:if test="${fiche.employeId != null}">
            document.getElementById('employeId').dispatchEvent(new Event('change'));
        </c:if>
    </script>
</body>
</html>