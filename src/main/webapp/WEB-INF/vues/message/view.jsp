<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Message - Gestion RH</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .container { max-width: 800px; margin: 0 auto; padding: 20px; }
        .card { background: white; border-radius: 16px; padding: 24px; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        .message-header { border-bottom: 1px solid #e2e8f0; padding-bottom: 15px; margin-bottom: 15px; }
        .message-sujet { font-size: 20px; font-weight: 700; color: #1e293b; }
        .message-meta { font-size: 13px; color: #64748b; margin-top: 8px; }
        .message-contenu { font-size: 15px; line-height: 1.6; color: #334155; margin: 20px 0; white-space: pre-wrap; }
        .btn-primary { background: #0a2540; color: white; padding: 10px 20px; border-radius: 8px; text-decoration: none; display: inline-block; }
        .btn-secondary { background: #e2e8f0; color: #1e293b; padding: 10px 20px; border-radius: 8px; text-decoration: none; display: inline-block; }
        .reply-box { margin-top: 30px; padding-top: 20px; border-top: 1px solid #e2e8f0; }
        textarea { width: 100%; padding: 10px; border: 1px solid #e2e8f0; border-radius: 8px; min-height: 100px; margin: 10px 0; }
    </style>
</head>
<body>
    <div class="container">
        <h1>💬 Message</h1>
        <br>
        
        <div class="card">
            <div class="message-header">
                <div class="message-sujet">${message.sujet}</div>
                <div class="message-meta">
                    De : <strong>${message.expediteurNom}</strong> | 
                    Le : ${fn:substring(message.dateEnvoi, 0, 16)}
                    <c:if test="${message.type == 'GENERAL'}">
                        | <span style="background:#e2e8f0; padding:2px 8px; border-radius:12px;">📢 Annonce générale</span>
                    </c:if>
                </div>
            </div>
            
            <div class="message-contenu">
                ${message.contenu}
            </div>
            
            <div style="display: flex; gap: 10px;">
                <a href="message?action=list" class="btn-secondary">⬅️ Retour</a>
                <a href="message?action=reply&id=${message.id}" class="btn-primary">↩️ Répondre</a>
            </div>
            
            <c:if test="${message.expediteurId != user.employeId}">
                <div class="reply-box" id="replyBox" style="display:none;">
                    <form action="message" method="post">
                        <input type="hidden" name="action" value="reply">
                        <input type="hidden" name="parentId" value="${message.id}">
                        <textarea name="contenu" placeholder="Votre réponse..." required></textarea>
                        <button type="submit" class="btn-primary">✉️ Envoyer la réponse</button>
                        <button type="button" onclick="hideReply()" class="btn-secondary">Annuler</button>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
    
    <script>
        function showReply() {
            document.getElementById('replyBox').style.display = 'block';
        }
        function hideReply() {
            document.getElementById('replyBox').style.display = 'none';
        }
    </script>
</body>
</html>