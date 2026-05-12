package com.rh.servlet;

import com.rh.dao.CongeDAO;
import com.rh.dao.EmployeDao;
import com.rh.dao.impl.CongeDAOImpl;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.model.Conge;
import com.rh.model.Employe;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteConge(req, resp);
                break;
            default:
                listConges(req, resp);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        
        if (action != null && action.equals("update")) {
            updateConge(req, resp);
        } else {
            saveConge(req, resp);
        }
    }
    
    private void listConges(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Conge> conges = congeDAO.findAll();
        req.setAttribute("conges", conges);
        req.getRequestDispatcher("/WEB-INF/vues/conge/liste.jsp").forward(req, resp);
    }
    
    private void showForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Employe> employes = employeDAO.findAll();
        req.setAttribute("employes", employes);
        req.getRequestDispatcher("/WEB-INF/vues/conge/form.jsp").forward(req, resp);
    }
    
    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Conge conge = congeDAO.read(id);
        List<Employe> employes = employeDAO.findAll();
        req.setAttribute("conge", conge);
        req.setAttribute("employes", employes);
        req.getRequestDispatcher("/WEB-INF/vues/conge/form.jsp").forward(req, resp);
    }
    
    private void saveConge(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long employeId = Long.parseLong(req.getParameter("employeId"));
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
    
    private void updateConge(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Long employeId = Long.parseLong(req.getParameter("employeId"));
        String typeConge = req.getParameter("typeConge");
        LocalDate dateDebut = LocalDate.parse(req.getParameter("dateDebut"));
        LocalDate dateFin = LocalDate.parse(req.getParameter("dateFin"));
        long nbJours = ChronoUnit.DAYS.between(dateDebut, dateFin) + 1;
        String motif = req.getParameter("motif");
        String statut = req.getParameter("statut");
        String approuvePar = req.getParameter("approuvePar");
        
        Conge c = new Conge();
        c.setId(id);
        c.setEmployeId(employeId);
        c.setTypeConge(typeConge);
        c.setDateDebut(dateDebut);
        c.setDateFin(dateFin);
        c.setNbJours((int) nbJours);
        c.setMotif(motif);
        c.setStatut(statut);
        c.setApprouvePar(approuvePar);
        
        congeDAO.update(c);
        resp.sendRedirect("conge?action=list");
    }
    
    private void deleteConge(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        congeDAO.delete(id);
        resp.sendRedirect("conge?action=list");
    }
}