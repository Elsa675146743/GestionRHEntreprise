<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Messages - Gestion RH</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .container { max-width: 1200px; margin: 0 auto; padding: 20px; }
        .page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px; }
        .btn-primary { background: #0a2540; color: white; padding: 10px 20px; border-radius: 8px; text-decoration: none; }
        .message-list { background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        .message-item { display: flex; justify-content: space-between; align-items: center; padding: 15px 20px; border-bottom: 1px solid #e2e8f0; cursor: pointer; transition: background 0.2s; }
        .message-item:hover { background: #f8fafc; }
        .message-item.unread { background: #eef2ff; }
        .message-info { flex: 1; }
        .message-sujet { font-weight: 600; color: #1e293b; }
        .message-expediteur { font-size: 12px; color: #64748b; margin-top: 4px; }
        .message-date { font-size: 12px; color: #94a3b8; }
        .badge { background: #ef4444; color: white; border-radius: 20px; padding: 2px 8px; font-size: 11px; }
        .tabs { display: flex; gap: 10px; margin-bottom: 20px; }
        .tab { padding: 8px 16px; background: #e2e8f0; border-radius: 8px; text-decoration: none; color: #1e293b; }
        .tab.active { background: #0a2540; color: white; }
    </style>
</head>
<body>
    <div class="container">
        <div class="page-header">
            <h1>💬 Messages</h1>
            <a href="message?action=send" class="btn-primary">✏️ Nouveau message</a>
        </div>
        
        <div class="tabs">
            <a href="message?action=list" class="tab ${param.action == 'list' or param.action == null ? 'active' : ''}">📥 Reçus</a>
            <a href="message?action=sent" class="tab ${param.action == 'sent' ? 'active' : ''}">📤 Envoyés</a>
        </div>
        
        <div class="message-list">
            <c:forEach var="msg" items="${messages}">
                <div class="message-item ${!msg.lu ? 'unread' : ''}" onclick="location.href='message?action=view&id=${msg.id}'">
                    <div class="message-info">
                        <div class="message-sujet">
                            ${msg.sujet}
                            <c:if test="${!msg.lu}">
                                <span class="badge">Nouveau</span>
                            </c:if>
                        </div>
                        <div class="message-expediteur">
                            De : ${msg.expediteurNom}
                            <c:if test="${msg.type == 'GENERAL'}">
                                <span style="background:#e2e8f0; padding:2px 8px; border-radius:12px; margin-left:8px;">📢 Annonce</span>
                            </c:if>
                        </div>
                    </div>
                    <div class="message-date">${fn:substring(msg.dateEnvoi, 0, 16)}</div>
                </div>
            </c:forEach>
            <c:if test="${empty messages}">
                <div style="text-align:center; padding:40px; color:#94a3b8;">📭 Aucun message</div>
            </c:if>
        </div>
        
        <br>
        <a href="dashboard">⬅️ Retour au dashboard</a>
    </div>
</body>
</html>