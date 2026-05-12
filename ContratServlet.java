package com.rh.servlet;

import com.rh.dao.ContratDAO;
import com.rh.model.Contrat;
import com.rh.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

@WebServlet("/contrat")
public class ContratServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection conn = DBConnection.getConnection();
            ContratDAO dao = new ContratDAO(conn);
            String action = request.getParameter("action");

            if ("delete".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                dao.delete(id);
                response.sendRedirect("contrat");
                return;
            }

            if ("edit".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                Contrat contrat = dao.getById(id);
                request.setAttribute("contrat", contrat);
                request.getRequestDispatcher("/WEB-INF/views/contrat/form.jsp")
                       .forward(request, response);
                return;
            }

            List<Contrat> liste = dao.getAll();
            request.setAttribute("contrats", liste);
            request.getRequestDispatcher("/WEB-INF/views/contrat/liste.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection conn = DBConnection.getConnection();
            ContratDAO dao = new ContratDAO(conn);

            Contrat c = new Contrat();
            String idParam = request.getParameter("id");
            c.setEmployeId(Long.parseLong(request.getParameter("employeId")));
            c.setTypeContrat(request.getParameter("typeContrat"));
            c.setDateDebut(Date.valueOf(request.getParameter("dateDebut")));
            String dateFin = request.getParameter("dateFin");
            if (dateFin != null && !dateFin.isEmpty()) {
                c.setDateFin(Date.valueOf(dateFin));
            }
            c.setSalaire(Double.parseDouble(request.getParameter("salaire")));
            c.setAvantages(request.getParameter("avantages"));

            if (idParam != null && !idParam.isEmpty()) {
                c.setId(Long.parseLong(idParam));
                dao.update(c);
            } else {
                dao.insert(c);
            }

            response.sendRedirect("contrat");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
