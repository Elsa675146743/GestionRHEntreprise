package com.rh.servlet;

import com.rh.dao.DepartementDAO;
import com.rh.dao.impl.DepartementDAOImpl;
import com.rh.model.Departement;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListeDepartementServlet extends HttpServlet {
    
    private DepartementDAO departementDAO = new DepartementDAOImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        List<Departement> departements = departementDAO.findAll();
        req.setAttribute("departements", departements);
        req.getRequestDispatcher("/WEB-INF/vues/departement/liste.jsp").forward(req, resp);
    }
}