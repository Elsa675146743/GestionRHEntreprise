<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord - Gestion RH</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { background: #eef2f7; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        .header { background: #0a2540; color: white; padding: 0 32px; position: fixed; top: 0; left: 0; right: 0; height: 64px; display: flex; justify-content: space-between; align-items: center; z-index: 100; box-shadow: 0 2px 8px rgba(0,0,0,0.08); }
        .logo { font-size: 20px; font-weight: 700; }
        .logo span { font-weight: 300; }
        .nav-links { display: flex; gap: 32px; align-items: center; }
        .nav-links a { color: #e2e8f0; text-decoration: none; font-size: 14px; font-weight: 500; }
        .nav-links a:hover { color: white; }
        .logout-btn { background: rgba(255,255,255,0.1); padding: 8px 18px; border-radius: 8px; }
        .logout-btn:hover { background: #dc3545; }
        .app-container { margin-top: 64px; display: flex; min-height: calc(100vh - 64px); }
        .sidebar { width: 260px; background: white; border-right: 1px solid #e2e8f0; padding: 24px 0; }
        .user-card { text-align: center; padding: 0 20px 24px 20px; border-bottom: 1px solid #e2e8f0; margin-bottom: 16px; }
        .avatar { width: 72px; height: 72px; background: #0a2540; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin: 0 auto 12px; font-size: 28px; font-weight: 600; color: white; cursor: pointer; transition: transform 0.2s; overflow: hidden; }
        .avatar-img { width: 100%; height: 100%; object-fit: cover; }
        .avatar:hover { transform: scale(1.05); }
        .user-name { font-weight: 600; font-size: 16px; color: #1a2a3e; text-decoration: none; cursor: pointer; }
        .user-name:hover { color: #0a2540; text-decoration: underline; }
        .user-role { font-size: 12px; color: #64748b; margin-top: 4px; }
        .menu-item { display: flex; align-items: center; gap: 12px; padding: 12px 20px; color: #475569; text-decoration: none; font-size: 14px; font-weight: 500; transition: all 0.2s; border-left: 3px solid transparent; }
        .menu-item:hover { background: #f8fafc; color: #0a2540; }
        .menu-item.active { background: #eef2ff; color: #0a2540; border-left-color: #0a2540; }
        .main-content { flex: 1; padding: 28px 32px; }
        .welcome-banner { background: linear-gradient(135deg, #0a2540 0%, #1e3a5f 100%); border-radius: 16px; padding: 28px 32px; color: white; margin-bottom: 32px; display: flex; justify-content: space-between; align-items: center; }
        .welcome-text h2 { font-size: 22px; font-weight: 600; margin-bottom: 6px; }
        .badge { background: rgba(255,255,255,0.15); padding: 8px 16px; border-radius: 30px; font-size: 12px; }
        .stats-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; margin-bottom: 32px; }
        .stat-card { background: white; border-radius: 12px; padding: 20px; border: 1px solid #e2e8f0; }
        .stat-label { font-size: 13px; color: #64748b; margin-bottom: 8px; }
        .stat-value { font-size: 28px; font-weight: 700; color: #0a2540; }
        .cards-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; margin-bottom: 32px; }
        .card { background: white; border-radius: 12px; padding: 24px; border: 1px solid #e2e8f0; cursor: pointer; transition: all 0.2s; }
        .card:hover { box-shadow: 0 8px 20px rgba(0,0,0,0.08); transform: translateY(-3px); }
        .card-icon { font-size: 36px; margin-bottom: 16px; }
        .card h3 { font-size: 16px; font-weight: 600; color: #1e293b; margin-bottom: 8px; }
        .card p { font-size: 13px; color: #64748b; margin-bottom: 16px; }
        .card-link { color: #0a2540; text-decoration: none; font-weight: 600; font-size: 13px; }
        .two-columns { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
        .info-panel { background: white; border-radius: 12px; padding: 24px; border: 1px solid #e2e8f0; }
        .panel-title { font-size: 16px; font-weight: 600; color: #1e293b; margin-bottom: 16px; padding-bottom: 12px; border-bottom: 1px solid #e2e8f0; display: flex; justify-content: space-between; }
        .list-item { padding: 14px 0; border-bottom: 1px solid #f1f5f9; }
        .list-item:last-child { border-bottom: none; }
        .item-title { font-weight: 500; font-size: 14px; color: #1e293b; margin-bottom: 4px; }
        .item-date { font-size: 11px; color: #94a3b8; }
        .event-day { font-weight: 600; color: #0a2540; font-size: 12px; margin-bottom: 4px; }
        @media (max-width: 1024px) { .stats-grid, .cards-grid { grid-template-columns: repeat(2, 1fr); } }
        @media (max-width: 768px) { .sidebar { display: none; } .stats-grid, .cards-grid { grid-template-columns: 1fr; } .two-columns { grid-template-columns: 1fr; } .main-content { padding: 20px; } }
    </style>
</head>
<body>
    <div class="header">
        <div class="logo">GESTION<span> RH</span></div>
        <div class="nav-links">
            <a href="dashboard">Accueil</a>
            <c:if test="${user.role == 'RH' || user.role == 'DIRECTEUR'}">
                <a href="employe?action=list">Employés</a>
            </c:if>
            <a href="logout" class="logout-btn">Déconnexion</a>
        </div>
    </div>
    
    <div class="app-container">
        <aside class="sidebar">
            <div class="user-card">
                <a href="employe?action=view&id=${user.employeId}" style="text-decoration: none;">
                    <c:choose>
                        <c:when test="${employePhoto != null && employePhoto != ''}">
                            <div class="avatar">
                                <img src="uploads/${employePhoto}" class="avatar-img" alt="Photo">
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="avatar">${fn:substring(user.login, 0, 1)}</div>
                        </c:otherwise>
                    </c:choose>
                    <div class="user-name">${user.login}</div>
                </a>
                <div class="user-role">
                    <c:choose>
                        <c:when test="${user.role == 'EMPLOYE'}">Employé</c:when>
                        <c:when test="${user.role == 'RH'}">Ressources Humaines</c:when>
                        <c:when test="${user.role == 'DIRECTEUR'}">Direction</c:when>
                    </c:choose>
                </div>
            </div>
            <nav>
                <c:if test="${user.role == 'EMPLOYE'}">
                    <a href="dashboard" class="menu-item active">📊 Tableau de bord</a>
                    <a href="fichepaie?action=list" class="menu-item">💰 Ma fiche de paie</a>
                    <a href="conge?action=list" class="menu-item">🏖️ Mes congés</a>
                </c:if>
                <c:if test="${user.role == 'RH'}">
                    <a href="dashboard" class="menu-item active">📊 Tableau de bord</a>
                    <a href="employe?action=list" class="menu-item">👥 Employés</a>
                    <a href="departement?action=list" class="menu-item">🏢 Départements</a>
                    <a href="contrat?action=list" class="menu-item">📄 Contrats</a>
                    <a href="conge?action=list" class="menu-item">🏖️ Congés</a>
                    <a href="fichepaie?action=list" class="menu-item">💰 Fiches de paie</a>
                </c:if>
                <c:if test="${user.role == 'DIRECTEUR'}">
                    <a href="dashboard" class="menu-item active">📊 Tableau de bord</a>
                    <a href="employe?action=list" class="menu-item">👥 Employés</a>
                    <a href="departement?action=list" class="menu-item">🏢 Départements</a>
                    <a href="contrat?action=list" class="menu-item">📄 Contrats</a>
                    <a href="conge?action=list" class="menu-item">🏖️ Congés</a>
                    <a href="fichepaie?action=list" class="menu-item">💰 Fiches de paie</a>
                    <a href="statistiques" class="menu-item">📈 Statistiques</a>
                </c:if>
            </nav>
        </aside>
        
        <main class="main-content">
            <div class="welcome-banner">
                <div class="welcome-text">
                    <h2>Bonjour, ${user.login}</h2>
                    <p>Bienvenue sur votre tableau de bord RH</p>
                </div>
                <div class="badge">👤 ${user.role}</div>
            </div>
            
            <div class="stats-grid">
                <div class="stat-card"><div class="stat-label">👥 Employés</div><div class="stat-value">-</div></div>
                <div class="stat-card"><div class="stat-label">🏢 Départements</div><div class="stat-value">-</div></div>
                <div class="stat-card"><div class="stat-label">🏖️ Congés</div><div class="stat-value">-</div></div>
                <div class="stat-card"><div class="stat-label">💰 Masse salariale</div><div class="stat-value">- FCFA</div></div>
            </div>
            
            <div class="cards-grid">
                <c:if test="${user.role == 'EMPLOYE'}">
                    <div class="card" onclick="location.href='fichepaie?action=list'"><div class="card-icon">💰</div><h3>Ma fiche de paie</h3><p>Consultez et téléchargez vos bulletins</p><span class="card-link">Accéder →</span></div>
                    <div class="card" onclick="location.href='conge?action=list'"><div class="card-icon">🏖️</div><h3>Mes congés</h3><p>Gérez vos demandes d'absence</p><span class="card-link">Accéder →</span></div>
                </c:if>
                <c:if test="${user.role == 'RH'}">
                    <div class="card" onclick="location.href='employe?action=list'"><div class="card-icon">👥</div><h3>Employés</h3><p>Gérez les employés</p><span class="card-link">Accéder →</span></div>
                    <div class="card" onclick="location.href='departement?action=list'"><div class="card-icon">🏢</div><h3>Départements</h3><p>Gérez les départements</p><span class="card-link">Accéder →</span></div>
                    <div class="card" onclick="location.href='fichepaie?action=list'"><div class="card-icon">💰</div><h3>Fiches de paie</h3><p>Gérez les bulletins</p><span class="card-link">Accéder →</span></div>
                </c:if>
                <c:if test="${user.role == 'DIRECTEUR'}">
                    <div class="card" onclick="location.href='employe?action=list'"><div class="card-icon">👥</div><h3>Employés</h3><p>Consultez le personnel</p><span class="card-link">Accéder →</span></div>
                    <div class="card" onclick="location.href='departement?action=list'"><div class="card-icon">🏢</div><h3>Départements</h3><p>Gérez les départements</p><span class="card-link">Accéder →</span></div>
                    <div class="card" onclick="location.href='statistiques'"><div class="card-icon">📊</div><h3>Statistiques</h3><p>Indicateurs clés</p><span class="card-link">Accéder →</span></div>
                </c:if>
            </div>
            
            <div class="two-columns">
                <div class="info-panel">
                    <div class="panel-title"><span>📢 Activités récentes</span></div>
                    <div class="list-item"><div class="item-title">Bienvenue sur votre tableau de bord</div><div class="item-date">Aujourd'hui</div></div>
                    <div class="list-item"><div class="item-title">Système opérationnel</div><div class="item-date">Hier</div></div>
                </div>
                <div class="info-panel">
                    <div class="panel-title"><span>📅 Agenda</span></div>
                    <div class="list-item"><div class="event-day">MAR 14 MAI</div><div class="item-title">Réunion RH</div></div>
                    <div class="list-item"><div class="event-day">MER 15 MAI</div><div class="item-title">Entretiens</div></div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>