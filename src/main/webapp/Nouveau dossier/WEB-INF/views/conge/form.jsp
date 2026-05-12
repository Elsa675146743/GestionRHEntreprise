<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire Congé</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f4f4f4; }
        h2 { color: #333; }
        form { background: white; padding: 20px; border-radius: 8px; width: 400px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input, select, textarea { width: 100%; padding: 8px; margin-top: 5px; border: 1px solid #ddd; border-radius: 4px; }
        .btn { margin-top: 15px; padding: 10px 20px; background: #2196F3; color: white; border: none; border-radius: 4px; cursor: pointer; }
        .btn:hover { background: #1976D2; }
        a { display: inline-block; margin-top: 10px; color: #2196F3; }
    </style>
</head>
<body>
    <h2>${conge != null ? 'Modifier' : 'Nouveau'} Congé</h2>
    <form action="conge" method="post">
        <input type="hidden" name="id" value="${conge.id}"/>

        <label>Employé ID</label>
        <input type="number" name="employeId" value="${conge.employeId}" required/>

        <label>Type de Congé</label>
        <select name="typeConge">
            <option value="ANNUEL" ${conge.typeConge == 'ANNUEL' ? 'selected' : ''}>Annuel</option>
            <option value="MALADIE" ${conge.typeConge == 'MALADIE' ? 'selected' : ''}>Maladie</option>
            <option value="MATERNITE" ${conge.typeConge == 'MATERNITE' ? 'selected' : ''}>Maternité</option>
            <option value="PATERNITE" ${conge.typeConge == 'PATERNITE' ? 'selected' : ''}>Paternité</option>
            <option value="EXCEPTIONNEL" ${conge.typeConge == 'EXCEPTIONNEL' ? 'selected' : ''}>Exceptionnel</option>
        </select>

        <label>Date Début</label>
        <input type="date" name="dateDebut" value="${conge.dateDebut}" required/>

        <label>Date Fin</label>
        <input type="date" name="dateFin" value="${conge.dateFin}" required/>

        <label>Nombre de Jours</label>
        <input type="number" name="nbJours" value="${conge.nbJours}" required/>

        <label>Motif</label>
        <textarea name="motif" rows="3">${conge.motif}</textarea>

        <label>Statut</label>
        <select name="statut">
            <option value="DEMANDE" ${conge.statut == 'DEMANDE' ? 'selected' : ''}>Demandé</option>
            <option value="APPROUVE" ${conge.statut == 'APPROUVE' ? 'selected' : ''}>Approuvé</option>
            <option value="REFUSE" ${conge.statut == 'REFUSE' ? 'selected' : ''}>Refusé</option>
        </select>

        <label>Approuvé Par</label>
        <input type="text" name="approuvePar" value="${conge.approuvePar}"/>

        <button type="submit" class="btn">Enregistrer</button>
    </form>
    <a href="conge">← Retour à la liste</a>
</body>
</html>