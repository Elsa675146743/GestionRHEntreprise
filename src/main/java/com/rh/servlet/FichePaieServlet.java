package com.rh.servlet;

import com.rh.dao.FichePaieDAO;
import com.rh.dao.EmployeDao;
import com.rh.dao.impl.FichePaieDAOImpl;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.model.FichePaie;
import com.rh.model.Employe;
import com.rh.util.SmsUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FichePaieServlet extends HttpServlet {
    
    private FichePaieDAO fichePaieDAO = new FichePaieDAOImpl();
    private EmployeDao employeDAO = new EmployeDaoImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        
        if (action == null) action = "list";
        
        switch (action) {
            case "list":
                listFiches(req, resp);
                break;
            case "add":
                showForm(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteFiche(req, resp);
                break;
            default:
                listFiches(req, resp);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        
        if (action != null && action.equals("update")) {
            updateFiche(req, resp);
        } else {
            saveFiche(req, resp);
        }
    }
    
    private void listFiches(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<FichePaie> fiches = fichePaieDAO.findAll();
        req.setAttribute("fiches", fiches);
        req.getRequestDispatcher("/WEB-INF/vues/fichepaie/liste.jsp").forward(req, resp);
    }
    
    private void showForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Employe> employes = employeDAO.findAll();
        req.setAttribute("employes", employes);
        req.getRequestDispatcher("/WEB-INF/vues/fichepaie/form.jsp").forward(req, resp);
    }
    
    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        FichePaie fiche = fichePaieDAO.read(id);
        List<Employe> employes = employeDAO.findAll();
        req.setAttribute("fiche", fiche);
        req.setAttribute("employes", employes);
        req.getRequestDispatcher("/WEB-INF/vues/fichepaie/form.jsp").forward(req, resp);
    }
    
    private void saveFiche(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long employeId = Long.parseLong(req.getParameter("employeId"));
        String mois = req.getParameter("mois");
        double salaireBase = Double.parseDouble(req.getParameter("salaireBase"));
        double heuresSup = Double.parseDouble(req.getParameter("heuresSup"));
        double primes = Double.parseDouble(req.getParameter("primes"));
        double retenues = Double.parseDouble(req.getParameter("retenues"));
        
        double montantHeuresSup = (heuresSup * (salaireBase / 173) * 1.25);
        double salaireBrut = salaireBase + montantHeuresSup + primes;
        double salaireNet = salaireBrut - retenues;
        
        System.out.println("=== CRÉATION FICHE PAIE ===");
        System.out.println("Employé ID: " + employeId);
        System.out.println("Mois: " + mois);
        System.out.println("Salaire net: " + salaireNet);
        
        FichePaie f = new FichePaie();
        f.setEmployeId(employeId);
        f.setMois(mois);
        f.setSalaireBase(salaireBase);
        f.setHeuresSup(heuresSup);
        f.setMontantHeuresSup(montantHeuresSup);
        f.setPrimes(primes);
        f.setRetenues(retenues);
        f.setSalaireBrut(salaireBrut);
        f.setSalaireNet(salaireNet);
        
        fichePaieDAO.create(f);
        
        // ENVOI SMS : Fiche de paie disponible
        Employe employe = employeDAO.read(employeId);
        if (employe != null && employe.getTelephone() != null && !employe.getTelephone().isEmpty()) {
            String message = "Votre fiche de paie de " + mois + " est disponible. Net : " + String.format("%,.0f", salaireNet) + " FCFA";
            System.out.println("📱 ENVOI SMS FICHE PAIE");
            System.out.println("Numéro: " + employe.getTelephone());
            System.out.println("Message: " + message);
            SmsUtil.sendSms(employe.getTelephone(), message);
        } else {
            System.out.println("⚠️ SMS non envoyé: numéro manquant pour l'employé ID " + employeId);
        }
        
        resp.sendRedirect("fichepaie?action=list");
    }
    
    private void updateFiche(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Long employeId = Long.parseLong(req.getParameter("employeId"));
        String mois = req.getParameter("mois");
        double salaireBase = Double.parseDouble(req.getParameter("salaireBase"));
        double heuresSup = Double.parseDouble(req.getParameter("heuresSup"));
        double primes = Double.parseDouble(req.getParameter("primes"));
        double retenues = Double.parseDouble(req.getParameter("retenues"));
        
        double montantHeuresSup = (heuresSup * (salaireBase / 173) * 1.25);
        double salaireBrut = salaireBase + montantHeuresSup + primes;
        double salaireNet = salaireBrut - retenues;
        
        FichePaie f = new FichePaie();
        f.setId(id);
        f.setEmployeId(employeId);
        f.setMois(mois);
        f.setSalaireBase(salaireBase);
        f.setHeuresSup(heuresSup);
        f.setMontantHeuresSup(montantHeuresSup);
        f.setPrimes(primes);
        f.setRetenues(retenues);
        f.setSalaireBrut(salaireBrut);
        f.setSalaireNet(salaireNet);
        
        fichePaieDAO.update(f);
        resp.sendRedirect("fichepaie?action=list");
    }
    
    private void deleteFiche(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        fichePaieDAO.delete(id);
        resp.sendRedirect("fichepaie?action=list");
    }
}