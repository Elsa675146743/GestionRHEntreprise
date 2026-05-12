package com.rh.servlet;

import com.rh.dao.ContratDAO;
import com.rh.dao.EmployeDao;
import com.rh.dao.impl.ContratDAOImpl;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.model.Contrat;
import com.rh.model.Employe;
import com.rh.util.SmsUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContratServlet extends HttpServlet {
    
    private ContratDAO contratDAO = new ContratDAOImpl();
    private EmployeDao employeDAO = new EmployeDaoImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        
        if (action == null) action = "list";
        
        switch (action) {
            case "list":
                listContrats(req, resp);
                break;
            case "add":
                showForm(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteContrat(req, resp);
                break;
            default:
                listContrats(req, resp);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        
        if (action != null && action.equals("update")) {
            updateContrat(req, resp);
        } else {
            saveContrat(req, resp);
        }
    }
    
    private void listContrats(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Contrat> contrats = contratDAO.findAll();
        req.setAttribute("contrats", contrats);
        req.getRequestDispatcher("/WEB-INF/vues/contrat/liste.jsp").forward(req, resp);
    }
    
    private void showForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Employe> employes = employeDAO.findAll();
        req.setAttribute("employes", employes);
        req.getRequestDispatcher("/WEB-INF/vues/contrat/form.jsp").forward(req, resp);
    }
    
    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Contrat contrat = contratDAO.read(id);
        List<Employe> employes = employeDAO.findAll();
        req.setAttribute("contrat", contrat);
        req.setAttribute("employes", employes);
        req.getRequestDispatcher("/WEB-INF/vues/contrat/form.jsp").forward(req, resp);
    }
    
    private void saveContrat(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long employeId = Long.parseLong(req.getParameter("employeId"));
        String typeContrat = req.getParameter("typeContrat");
        LocalDate dateDebut = LocalDate.parse(req.getParameter("dateDebut"));
        String dateFinStr = req.getParameter("dateFin");
        LocalDate dateFin = dateFinStr != null && !dateFinStr.isEmpty() ? LocalDate.parse(dateFinStr) : null;
        double salaire = Double.parseDouble(req.getParameter("salaire"));
        String avantages = req.getParameter("avantages");
        
        Contrat c = new Contrat();
        c.setEmployeId(employeId);
        c.setTypeContrat(typeContrat);
        c.setDateDebut(dateDebut);
        c.setDateFin(dateFin);
        c.setSalaire(salaire);
        c.setAvantages(avantages);
        
        contratDAO.create(c);
        
        // ALERTE SMS : Contrat CDD qui expire bientôt
        if (typeContrat.equals("CDD") && dateFin != null) {
            checkAndSendExpirationAlert(c);
        }
        
        resp.sendRedirect("contrat?action=list");
    }
    
    private void updateContrat(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Long employeId = Long.parseLong(req.getParameter("employeId"));
        String typeContrat = req.getParameter("typeContrat");
        LocalDate dateDebut = LocalDate.parse(req.getParameter("dateDebut"));
        String dateFinStr = req.getParameter("dateFin");
        LocalDate dateFin = dateFinStr != null && !dateFinStr.isEmpty() ? LocalDate.parse(dateFinStr) : null;
        double salaire = Double.parseDouble(req.getParameter("salaire"));
        String avantages = req.getParameter("avantages");
        
        Contrat c = new Contrat();
        c.setId(id);
        c.setEmployeId(employeId);
        c.setTypeContrat(typeContrat);
        c.setDateDebut(dateDebut);
        c.setDateFin(dateFin);
        c.setSalaire(salaire);
        c.setAvantages(avantages);
        
        contratDAO.update(c);
        
        // ALERTE SMS : Contrat CDD qui expire bientôt
        if (typeContrat.equals("CDD") && dateFin != null) {
            checkAndSendExpirationAlert(c);
        }
        
        resp.sendRedirect("contrat?action=list");
    }
    
    private void deleteContrat(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        contratDAO.delete(id);
        resp.sendRedirect("contrat?action=list");
    }
    
    // ==================== VÉRIFICATION EXPIRATION CDD ====================
    
    private void checkAndSendExpirationAlert(Contrat contrat) {
        LocalDate today = LocalDate.now();
        LocalDate alertDate30 = today.plusDays(30);
        LocalDate alertDate7 = today.plusDays(7);
        LocalDate alertDate1 = today.plusDays(1);
        
        LocalDate dateFin = contrat.getDateFin();
        
        // Vérifier si le contrat expire dans 30 jours, 7 jours ou 1 jour
        if (dateFin.equals(alertDate30) || dateFin.equals(alertDate7) || dateFin.equals(alertDate1)) {
            Employe employe = employeDAO.read(contrat.getEmployeId());
            if (employe != null && employe.getTelephone() != null && !employe.getTelephone().isEmpty()) {
                String message = "Contrat de " + employe.getNom() + " " + employe.getPrenom() + 
                                 " expire le " + dateFin + ". Action requise";
                SmsUtil.sendSms(employe.getTelephone(), message);
                System.out.println("Alerte expiration envoyée pour le contrat de " + employe.getNom());
            }
        }
    }
}