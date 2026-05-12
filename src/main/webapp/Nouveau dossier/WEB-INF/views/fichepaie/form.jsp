<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire Fiche de Paie</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f4f4f4; }
        h2 { color: #333; }
        form { background: white; padding: 20px; border-radius: 8px; width: 400px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input { width: 100%; padding: 8px; margin-top: 5px; border: 1px solid #ddd; border-radius: 4px; }
        .btn { margin-top: 15px; padding: 10px 20px; background: #9C27B0; color: white; border: none; border-radius: 4px; cursor: pointer; }
        .btn:hover { background: #7B1FA2; }
        a { display: inline-block; margin-top: 10px; color: #9C27B0; }
    </style>
</head>
<body>
    <h2>${fiche != null ? 'Modifier' : 'Nouvelle'} Fiche de Paie</h2>
    <form action="fichepaie" method="post">
        <input type="hidden" name="id" value="${fiche.id}"/>

        <label>Employé ID</label>
        <input type="number" name="employeId" value="${fiche.employeId}" required/>

        <label>Mois (ex: 2026-05)</label>
        <input type="month" name="mois" value="${fiche.mois}" required/>

        <label>Salaire de Base</label>
        <input type="number" step="0.01" name="salaireBase" value="${fiche.salaireBase}" required/>

        <label>Heures Supplémentaires</label>
        <input type="number" step="0.01" name="heuresSup" value="${fiche.heuresSup}"/>

        <label>Montant Heures Sup</label>
        <input type="number" step="0.01" name="montantHeuresSup" value="${fiche.montantHeuresSup}"/>

        <label>Primes</label>
        <input type="number" step="0.01" name="primes" value="${fiche.primes}"/>

        <label>Retenues</label>
        <input type="number" step="0.01" name="retenues" value="${fiche.retenues}"/>

        <label>Salaire Brut</label>
        <input type="number" step="0.01" name="salaireBrut" value="${fiche.salaireBrut}" required/>

        <label>Salaire Net</label>
        <input type="number" step="0.01" name="salaireNet" value="${fiche.salaireNet}" required/>

        <button type="submit" class="btn">Enregistrer</button>
    </form>
    <a href="fichepaie">← Retour à la liste</a>
</body>
</html>