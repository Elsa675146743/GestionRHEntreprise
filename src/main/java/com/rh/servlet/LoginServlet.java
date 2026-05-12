package com.rh.servlet;

import com.rh.dao.UtilisateurDAO;
import com.rh.dao.impl.UtilisateurDAOImpl;
import com.rh.model.Utilisateur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/vues/login.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        
        Utilisateur utilisateur = utilisateurDAO.findByLogin(login);
        
        // Comparaison simple sans BCrypt
        if (utilisateur != null && utilisateur.getMdpHash().equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", utilisateur);
            resp.sendRedirect("dashboard");
        } else {
            req.setAttribute("error", "Login ou mot de passe incorrect");
            req.getRequestDispatcher("/WEB-INF/vues/login.jsp").forward(req, resp);
        }
    }
}