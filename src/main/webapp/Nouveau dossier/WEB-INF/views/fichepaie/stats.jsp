<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Statistiques Paie</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f4f4f4; }
        h2 { color: #333; }
        .card { background: white; padding: 20px; border-radius: 8px; 
                width: 300px; margin: 10px; display: inline-block;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        .card h3 { color: #9C27B0; margin: 0; }
        .card p { font-size: 28px; font-weight: bold; color: #333; margin: 10px 0; }
        .btn { padding: 10px 20px; background: #9C27B0; color: white; 
               text-decoration: none; border-radius: 4px; }
        .btn:hover { background: #7B1FA2; }
    </style>
</head>
<body>
    <h2>📊 Statistiques de la Paie</h2>

    <div class="card">
        <h3>💰 Masse Salariale Totale</h3>
        <p>${masseSalariale} FCFA</p>
    </div>

    <br/><br/>
    <a href="fichepaie" class="btn">← Retour à la liste</a>
</body>
</html>