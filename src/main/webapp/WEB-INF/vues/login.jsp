<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion - Gestion RH</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        body {
background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
    display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            padding: 20px;
        }
        
        .login-card {
            background: white;
            border-radius: 20px;
            box-shadow: 0 25px 50px rgba(0,0,0,0.3);
            width: 100%;
            max-width: 450px;
            overflow: hidden;
            animation: slideUp 0.6s ease-out;
        }
        
        @keyframes slideUp {
            from {
                opacity: 0;
                transform: translateY(50px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        .login-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 40px 30px;
            text-align: center;
        }
        
        .login-header h1 {
            color: white;
            margin: 0;
            font-size: 28px;
            border: none;
            padding: 0;
        }
        
        .login-header p {
            color: rgba(255,255,255,0.8);
            margin-top: 10px;
            font-size: 14px;
        }
        
        .login-body {
            padding: 35px 30px;
        }
        
        .form-group {
            margin-bottom: 25px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #333;
            font-size: 14px;
        }
        
        .form-group input {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 10px;
            font-size: 14px;
            transition: all 0.3s;
        }
        
        .form-group input:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102,126,234,0.1);
        }
        
        .login-btn {
            width: 100%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 12px;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        .login-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(102,126,234,0.4);
        }
        
        .error-message {
            background: #f8d7da;
            color: #721c24;
            padding: 12px;
            border-radius: 10px;
            margin-bottom: 20px;
            border-left: 4px solid #dc3545;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="login-card">
        <div class="login-header">
            <h1>🔐 Gestion RH</h1>
            <p>Veuillez entrer vos identifiants pour accéder à votre espace</p>
        </div>
        
        <div class="login-body">
            <c:if test="${error != null}">
                <div class="error-message">
                    ❌ ${error}
                </div>
            </c:if>
            
            <form action="login" method="post">
                <div class="form-group">
                    <label>👤 Login / Identifiant</label>
                    <input type="text" name="login" placeholder="Nom d'utilisateur" required>
                </div>
                
                <div class="form-group">
                    <label>🔒 Mot de passe</label>
                    <input type="password" name="password" placeholder="********" required>
                </div>
                
                <button type="submit" class="login-btn">Se connecter</button>
            </form>
        </div>
    </div>
</body>
</html>