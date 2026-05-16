<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Changer le mot de passe</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f0f4f8;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .container {
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 450px;
        }
        .header {
            text-align: center;
            margin-bottom: 30px;
        }
        .header .icon {
            font-size: 48px;
            margin-bottom: 10px;
        }
        .header h2 {
            color: #1e293b;
            font-size: 24px;
            margin-bottom: 8px;
        }
        .header p {
            color: #64748b;
            font-size: 14px;
        }
        .alert-warning {
            background: #fff3cd;
            border: 1px solid #ffc107;
            border-radius: 8px;
            padding: 12px 16px;
            margin-bottom: 20px;
            color: #856404;
            font-size: 14px;
        }
        .alert-error {
            background: #fee2e2;
            border: 1px solid #ef4444;
            border-radius: 8px;
            padding: 12px 16px;
            margin-bottom: 20px;
            color: #dc2626;
            font-size: 14px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 6px;
            color: #374151;
            font-weight: 600;
            font-size: 14px;
        }
        input[type="password"] {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #e5e7eb;
            border-radius: 8px;
            font-size: 14px;
            transition: border-color 0.2s;
            outline: none;
        }
        input[type="password"]:focus {
            border-color: #3b82f6;
        }
        .btn {
            width: 100%;
            padding: 14px;
            background: #1e3a5f;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s;
        }
        .btn:hover { background: #2d5a8e; }
        .password-hint {
            font-size: 12px;
            color: #9ca3af;
            margin-top: 4px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <div class="icon">🔐</div>
        <h2>Changement de mot de passe</h2>
        <p>Pour votre sécurité, veuillez définir un nouveau mot de passe.</p>
    </div>

    <div class="alert-warning">
        ⚠️ C'est votre première connexion. Vous devez changer votre mot de passe avant de continuer.
    </div>

    <c:if test="${not empty error}">
        <div class="alert-error">❌ ${error}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/changerMotDePasse">
        <div class="form-group">
            <label>Mot de passe actuel</label>
            <input type="password" name="ancienMotDePasse" placeholder="Entrez votre mot de passe actuel" required />
        </div>
        <div class="form-group">
            <label>Nouveau mot de passe</label>
            <input type="password" name="nouveauMotDePasse" placeholder="Entrez votre nouveau mot de passe" required />
            <p class="password-hint">Minimum 6 caractères</p>
        </div>
        <div class="form-group">
            <label>Confirmer le nouveau mot de passe</label>
            <input type="password" name="confirmerMotDePasse" placeholder="Confirmez votre nouveau mot de passe" required />
        </div>
        <button type="submit" class="btn">✅ Valider le nouveau mot de passe</button>
    </form>
</div>
</body>
</html>