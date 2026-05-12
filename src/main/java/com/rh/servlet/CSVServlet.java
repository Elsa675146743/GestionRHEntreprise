package com.rh.servlet;

import com.rh.dao.EmployeDao;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.model.Employe;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CSVServlet extends HttpServlet {
    
    private EmployeDao employeDAO = new EmployeDaoImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        resp.setContentType("text/csv");
        resp.setHeader("Content-Disposition", "attachment; filename=\"employes.csv\"");
        
        PrintWriter out = resp.getWriter();
        out.println("ID,Matricule,Nom,Prénom,Poste,Email,Téléphone");
        
        List<Employe> employes = employeDAO.findAll();
        for (Employe e : employes) {
            out.println(e.getId() + "," + e.getMatricule() + "," + e.getNom() + "," + 
                        e.getPrenom() + "," + e.getPoste() + "," + e.getEmail() + "," + e.getTelephone());
        }
    }
}