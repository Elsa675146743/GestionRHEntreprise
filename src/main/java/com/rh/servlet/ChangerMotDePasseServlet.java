package com.rh.servlet;

import com.rh.dao.UtilisateurDAO;
import com.rh.dao.impl.UtilisateurDAOImpl;
import com.rh.model.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangerMotDePasseServlet extends HttpServlet {

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/vues/changerMotDePasse.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        String ancienMotDePasse = req.getParameter("ancienMotDePasse");
        String nouveauMotDePasse = req.getParameter("nouveauMotDePasse");
        String confirmerMotDePasse = req.getParameter("confirmerMotDePasse");

        // Vérifier l'ancien mot de passe
        if (!BCrypt.checkpw(ancienMotDePasse, utilisateur.getMdpHash())) {
            req.setAttribute("error", "L'ancien mot de passe est incorrect.");
            req.getRequestDispatcher("/WEB-INF/vues/changerMotDePasse.jsp").forward(req, resp);
            return;
        }

        // Vérifier que les deux nouveaux mots de passe correspondent
        if (!nouveauMotDePasse.equals(confirmerMotDePasse)) {
            req.setAttribute("error", "Les nouveaux mots de passe ne correspondent pas.");
            req.getRequestDispatcher("/WEB-INF/vues/changerMotDePasse.jsp").forward(req, resp);
            return;
        }

        // Vérifier la longueur minimale
        if (nouveauMotDePasse.length() < 6) {
            req.setAttribute("error", "Le mot de passe doit contenir au moins 6 caractères.");
            req.getRequestDispatcher("/WEB-INF/vues/changerMotDePasse.jsp").forward(req, resp);
            return;
        }

        // Hacher et sauvegarder le nouveau mot de passe
        String nouveauHash = BCrypt.hashpw(nouveauMotDePasse, BCrypt.gensalt());
        utilisateurDAO.updateMotDePasse(utilisateur.getId(), nouveauHash);

        // Mettre à jour la session
        utilisateur.setMdpHash(nouveauHash);
        utilisateur.setPremierConnexion(false);
        session.setAttribute("user", utilisateur);

        resp.sendRedirect("dashboard");
    }
}