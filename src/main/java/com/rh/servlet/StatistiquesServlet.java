package com.rh.servlet;

import com.rh.dao.DepartementDAO;
import com.rh.dao.EmployeDao;
import com.rh.dao.FichePaieDAO;
import com.rh.dao.impl.DepartementDAOImpl;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.dao.impl.FichePaieDAOImpl;
import com.rh.model.Departement;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StatistiquesServlet extends HttpServlet {
    
    private FichePaieDAO fichePaieDAO = new FichePaieDAOImpl();
    private EmployeDao employeDAO = new EmployeDaoImpl();
    private DepartementDAO departementDAO = new DepartementDAOImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }
        
        // Statistiques globales
        double masseSalarialeTotale = fichePaieDAO.getMasseSalarialeTotale();
        int nbEmployes = employeDAO.findAll().size();
        int nbDepartements = departementDAO.findAll().size();
        double salaireMoyen = nbEmployes > 0 ? masseSalarialeTotale / nbEmployes : 0;
        
        // Statistiques par mois
        List<Object[]> statsParMois = fichePaieDAO.getStatsParMois();
        
        // Statistiques par département
        List<Object[]> statsParDepartement = fichePaieDAO.getStatsParDepartement();
        
        // Liste des départements pour le filtre
        List<Departement> departements = departementDAO.findAll();
        
        req.setAttribute("masseSalarialeTotale", masseSalarialeTotale);
        req.setAttribute("nbEmployes", nbEmployes);
        req.setAttribute("nbDepartements", nbDepartements);
        req.setAttribute("salaireMoyen", salaireMoyen);
        req.setAttribute("statsParMois", statsParMois);
        req.setAttribute("statsParDepartement", statsParDepartement);
        req.setAttribute("departements", departements);
        
        req.getRequestDispatcher("/WEB-INF/vues/statistiques/dashboard.jsp").forward(req, resp);
    }
}