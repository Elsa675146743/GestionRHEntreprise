package com.rh.servlet;

import com.rh.dao.CongeDAO;
import com.rh.dao.EmployeDao;
import com.rh.dao.impl.CongeDAOImpl;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.model.Conge;
import com.rh.model.Employe;
import com.rh.model.Utilisateur;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CongeServlet extends HttpServlet {
    
    private CongeDAO congeDAO = new CongeDAOImpl();
    private EmployeDao employeDAO = new EmployeDaoImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        
        if (action == null) action = "list";
        
        switch (action) {
            case "list":
                listConges(req, resp);
                break;
            case "add":
                showForm(req, resp);
                break;
            case "delete":
                deleteConge(req, resp);
                break;
            case "approve":
                approveConge(req, resp);
                break;
            case "refuse":
                refuseConge(req, resp);
                break;
            // La modification est supprimée pour des raisons de sécurité
            default:
                listConges(req, resp);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Seule la création est possible via POST
        saveConge(req, resp);
    }
    
    private void listConges(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Conge> conges = congeDAO.findAll();
        req.setAttribute("conges", conges);
        req.getRequestDispatcher("/WEB-INF/vues/conge/liste.jsp").forward(req, resp);
    }
    
    private void showForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        
        List<Employe> employes = employeDAO.findAll();
        req.setAttribute("employes", employes);
        req.setAttribute("currentUserId", user.getEmployeId());
        req.getRequestDispatcher("/WEB-INF/vues/conge/form.jsp").forward(req, resp);
    }
    
    private void saveConge(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        
        Long employeId;
        // Si l'utilisateur est EMPLOYE, on utilise son ID automatiquement
        if (user.getRole().equals("EMPLOYE")) {
            employeId = user.getEmployeId();
        } else {
            employeId = Long.parseLong(req.getParameter("employeId"));
        }
        
        String typeConge = req.getParameter("typeConge");
        LocalDate dateDebut = LocalDate.parse(req.getParameter("dateDebut"));
        LocalDate dateFin = LocalDate.parse(req.getParameter("dateFin"));
        long nbJours = ChronoUnit.DAYS.between(dateDebut, dateFin) + 1;
        String motif = req.getParameter("motif");
        String statut = "DEMANDE";
        
        Conge c = new Conge();
        c.setEmployeId(employeId);
        c.setTypeConge(typeConge);
        c.setDateDebut(dateDebut);
        c.setDateFin(dateFin);
        c.setNbJours((int) nbJours);
        c.setMotif(motif);
        c.setStatut(statut);
        c.setApprouvePar(null);
        
        congeDAO.create(c);
        resp.sendRedirect("conge?action=list");
    }
    
    // ==================== APPROUVER UNE DEMANDE ====================
    
    private void approveConge(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        
        Long id = Long.parseLong(req.getParameter("id"));
        Conge conge = congeDAO.read(id);
        conge.setStatut("APPROUVE");
        conge.setApprouvePar(user.getLogin());
        congeDAO.update(conge);
        resp.sendRedirect("conge?action=list");
    }
    
    // ==================== REFUSER UNE DEMANDE ====================
    
    private void refuseConge(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        
        Long id = Long.parseLong(req.getParameter("id"));
        Conge conge = congeDAO.read(id);
        conge.setStatut("REFUSE");
        conge.setApprouvePar(user.getLogin());
        congeDAO.update(conge);
        resp.sendRedirect("conge?action=list");
    }
    
    // ==================== SUPPRIMER UNE DEMANDE (réservé RH/DIRECTEUR) ====================
    
    private void deleteConge(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        congeDAO.delete(id);
        resp.sendRedirect("conge?action=list");
    }
}