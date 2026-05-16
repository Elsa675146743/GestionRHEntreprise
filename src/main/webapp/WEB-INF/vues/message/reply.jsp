<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Répondre - Gestion RH</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .container { max-width: 800px; margin: 0 auto; padding: 20px; }
        .card { background: white; border-radius: 16px; padding: 24px; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        .original-message { background: #f8fafc; padding: 15px; border-radius: 12px; margin-bottom: 20px; border-left: 4px solid #0a2540; }
        .original-sujet { font-weight: 600; margin-bottom: 8px; }
        .original-contenu { font-size: 13px; color: #475569; margin-top: 8px; white-space: pre-wrap; }
        .form-group { margin-bottom: 20px; }
        label { display: block; font-weight: 600; margin-bottom: 8px; color: #1e293b; }
        textarea { width: 100%; padding: 12px; border: 1px solid #e2e8f0; border-radius: 8px; min-height: 150px; resize: vertical; font-family: inherit; }
        .btn-primary { background: #0a2540; color: white; padding: 10px 20px; border: none; border-radius: 8px; cursor: pointer; }
        .btn-secondary { background: #e2e8f0; color: #1e293b; padding: 10px 20px; border: none; border-radius: 8px; text-decoration: none; display: inline-block; }
        hr { margin: 20px 0; border: none; border-top: 1px solid #e2e8f0; }
    </style>
</head>
<body>
    <div class="container">
        <h1>↩️ Répondre au message</h1>
        <br>
        
        <div class="card">
            <div class="original-message">
                <div class="original-sujet">
                    📌 Sujet : ${parent.sujet}
                </div>
                <div class="original-sujet" style="font-size:12px; color:#64748b;">
                    De : ${parent.expediteurNom} | Le : ${fn:substring(parent.dateEnvoi, 0, 16)}
                </div>
                <div class="original-contenu">
                    ${parent.contenu}
                </div>
            </div>
            
            <form action="message" method="post">
                <input type="hidden" name="action" value="reply">
                <input type="hidden" name="parentId" value="${parent.id}">
                
                <div class="form-group">
                    <label>📝 Votre réponse</label>
                    <textarea name="contenu" placeholder="Écrivez votre réponse ici..." required></textarea>
                </div>
                
                <div style="display: flex; gap: 10px;">
                    <button type="submit" class="btn-primary">✉️ Envoyer la réponse</button>
                    <a href="message?action=list" class="btn-secondary">❌ Annuler</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>