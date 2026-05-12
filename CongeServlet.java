package com.rh.servlet;

import com.rh.dao.CongeDAO;
import com.rh.model.Conge;
import com.rh.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

@WebServlet("/conge")
public class CongeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection conn = DBConnection.getConnection();
            CongeDAO dao = new CongeDAO(conn);
            String action = request.getParameter("action");

            if ("delete".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                dao.delete(id);
                response.sendRedirect("conge");
                return;
            }

            if ("edit".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                Conge conge = dao.getById(id);
                request.setAttribute("conge", conge);
                request.getRequestDispatcher("/WEB-INF/views/conge/form.jsp")
                       .forward(request, response);
                return;
            }

            List<Conge> liste = dao.getAll();
            request.setAttribute("conges", liste);
            request.getRequestDispatcher("/WEB-INF/views/conge/liste.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection conn = DBConnection.getConnection();
            CongeDAO dao = new CongeDAO(conn);

            Conge c = new Conge();
            String idParam = request.getParameter("id");
            c.setEmployeId(Long.parseLong(request.getParameter("employeId")));
            c.setTypeConge(request.getParameter("typeConge"));
            c.setDateDebut(Date.valueOf(request.getParameter("dateDebut")));
            c.setDateFin(Date.valueOf(request.getParameter("dateFin")));
            c.setNbJours(Integer.parseInt(request.getParameter("nbJours")));
            c.setMotif(request.getParameter("motif"));
            c.setStatut(request.getParameter("statut"));
            c.setApprouvePar(request.getParameter("approuvePar"));

            if (idParam != null && !idParam.isEmpty()) {
                c.setId(Long.parseLong(idParam));
                dao.update(c);
            } else {
                dao.insert(c);
            }

            response.sendRedirect("conge");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}s
