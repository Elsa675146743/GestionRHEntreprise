package com.rh.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rh.model.Utilisateur;
import com.rh.dao.ContratDAO;
import com.rh.dao.EmployeDao;
import com.rh.dao.impl.ContratDAOImpl;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.model.Contrat;
import com.rh.model.Employe;
import com.rh.util.SmsUtil;

public class DashboardServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private ContratDAO contratDAO = new ContratDAOImpl();
    private EmployeDao employeDAO = new EmployeDaoImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }
        
        // Vérifier les contrats CDD qui expirent
        checkExpiringContracts();
        
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        System.out.println("=== DASHBOARD ===");
        System.out.println("Utilisateur : " + user.getLogin());
        System.out.println("Rôle : " + user.getRole());
        System.out.println("================");
        
        req.getRequestDispatcher("/WEB-INF/vues/dashboard.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        doGet(req, resp);
    }
    
    // ==================== VÉRIFICATION EXPIRATION CDD ====================
    
    private void checkExpiringContracts() {
        List<Contrat> contrats = contratDAO.findAll();
        LocalDate today = LocalDate.now();
        LocalDate alertDate30 = today.plusDays(30);
        LocalDate alertDate7 = today.plusDays(7);
        LocalDate alertDate1 = today.plusDays(1);
        
        for (Contrat c : contrats) {
            if (c.getTypeContrat().equals("CDD") && c.getDateFin() != null) {
                LocalDate dateFin = c.getDateFin();
                
                // Vérifier si le contrat expire dans 30 jours, 7 jours ou 1 jour
                if (dateFin.equals(alertDate30) || dateFin.equals(alertDate7) || dateFin.equals(alertDate1)) {
                    Employe employe = employeDAO.read(c.getEmployeId());
                    if (employe != null && employe.getTelephone() != null && !employe.getTelephone().isEmpty()) {
                        String message = "Contrat de " + employe.getNom() + " " + employe.getPrenom() + 
                                         " expire le " + dateFin + ". Action requise";
                        SmsUtil.sendSms(employe.getTelephone(), message);
                        System.out.println("Alerte expiration envoyée pour le contrat de " + employe.getNom());
                    }
                }
            }
        }
    }
}