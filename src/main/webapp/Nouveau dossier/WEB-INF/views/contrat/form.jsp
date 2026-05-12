<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire Contrat</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f4f4f4; }
        h2 { color: #333; }
        form { background: white; padding: 20px; border-radius: 8px; width: 400px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input, select { width: 100%; padding: 8px; margin-top: 5px; border: 1px solid #ddd; border-radius: 4px; }
        .btn { margin-top: 15px; padding: 10px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
        .btn:hover { background: #45a049; }
        a { display: inline-block; margin-top: 10px; color: #2196F3; }
    </style>
</head>
<body>
    <h2>${contrat != null ? 'Modifier' : 'Nouveau'} Contrat</h2>
    <form action="contrat" method="post">
        <input type="hidden" name="id" value="${contrat.id}"/>

        <label>Employé ID</label>
        <input type="number" name="employeId" value="${contrat.employeId}" required/>

        <label>Type de Contrat</label>
        <select name="typeContrat">
            <option value="CDI" ${contrat.typeContrat == 'CDI' ? 'selected' : ''}>CDI</option>
            <option value="CDD" ${contrat.typeContrat == 'CDD' ? 'selected' : ''}>CDD</option>
            <option value="STAGE" ${contrat.typeContrat == 'STAGE' ? 'selected' : ''}>STAGE</option>
        </select>

        <label>Date Début</label>
        <input type="date" name="dateDebut" value="${contrat.dateDebut}" required/>

        <label>Date Fin</label>
        <input type="date" name="dateFin" value="${contrat.dateFin}"/>

        <label>Salaire</label>
        <input type="number" step="0.01" name="salaire" value="${contrat.salaire}" required/>

        <label>Avantages</label>
        <input type="text" name="avantages" value="${contrat.avantages}"/>

        <button type="submit" class="btn">Enregistrer</button>
    </form>
    <a href="contrat">← Retour à la liste</a>
</body>
</html>