package com.rh.servlet;

import com.rh.dao.FichePaieDAO;
import com.rh.model.FichePaie;
import com.rh.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/fichepaie")
public class FichePaieServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection conn = DBConnection.getConnection();
            FichePaieDAO dao = new FichePaieDAO(conn);
            String action = request.getParameter("action");

            if ("delete".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                dao.delete(id);
                response.sendRedirect("fichepaie");
                return;
            }

            if ("edit".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                FichePaie fiche = dao.getById(id);
                request.setAttribute("fiche", fiche);
                request.getRequestDispatcher("/WEB-INF/views/fichepaie/form.jsp")
                       .forward(request, response);
                return;
            }

            if ("stats".equals(action)) {
                double masseSalariale = dao.getMasseSalarialeTotale();
                request.setAttribute("masseSalariale", masseSalariale);
                request.getRequestDispatcher("/WEB-INF/views/fichepaie/stats.jsp")
                       .forward(request, response);
                return;
            }

            List<FichePaie> liste = dao.getAll();
            request.setAttribute("fiches", liste);
            request.getRequestDispatcher("/WEB-INF/views/fichepaie/liste.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection conn = DBConnection.getConnection();
            FichePaieDAO dao = new FichePaieDAO(conn);

            FichePaie f = new FichePaie();
            String idParam = request.getParameter("id");
            f.setEmployeId(Long.parseLong(request.getParameter("employeId")));
            f.setMois(request.getParameter("mois"));
            f.setSalaireBase(Double.parseDouble(request.getParameter("salaireBase")));
            f.setHeuresSup(Double.parseDouble(request.getParameter("heuresSup")));
            f.setMontantHeuresSup(Double.parseDouble(request.getParameter("montantHeuresSup")));
            f.setPrimes(Double.parseDouble(request.getParameter("primes")));
            f.setRetenues(Double.parseDouble(request.getParameter("retenues")));
            f.setSalaireBrut(Double.parseDouble(request.getParameter("salaireBrut")));
            f.setSalaireNet(Double.parseDouble(request.getParameter("salaireNet")));

            if (idParam != null && !idParam.isEmpty()) {
                f.setId(Long.parseLong(idParam));
                dao.update(f);
            } else {
                dao.insert(f);
            }

            response.sendRedirect("fichepaie");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
