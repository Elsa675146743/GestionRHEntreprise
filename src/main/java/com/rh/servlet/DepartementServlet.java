package com.rh.servlet;

import com.rh.dao.DepartementDAO;
import com.rh.dao.PosteDAO;
import com.rh.dao.impl.DepartementDAOImpl;
import com.rh.dao.impl.PosteDAOImpl;
import com.rh.model.Departement;
import com.rh.model.Poste;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DepartementServlet extends HttpServlet {

    private DepartementDAO departementDAO = new DepartementDAOImpl();
    private PosteDAO posteDAO = new PosteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                listDepartements(req, resp);
                break;
            case "add":
                showForm(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteDepartement(req, resp);
                break;
            case "postes":
                getPostesByDepartement(req, resp);
                break;
            default:
                listDepartements(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action != null && action.equals("update")) {
            updateDepartement(req, resp);
        } else {
            saveDepartement(req, resp);
        }
    }

    private void listDepartements(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Departement> departements = departementDAO.findAll();
        req.setAttribute("departements", departements);
        req.getRequestDispatcher("/WEB-INF/vues/departement/liste.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/vues/departement/form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Departement departement = departementDAO.read(id);
        List<Poste> postes = posteDAO.findByDepartementId(id);
        req.setAttribute("departement", departement);
        req.setAttribute("postes", postes);
        req.getRequestDispatcher("/WEB-INF/vues/departement/form.jsp").forward(req, resp);
    }

    private void saveDepartement(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String nom = req.getParameter("nom");
        String responsable = req.getParameter("responsable");
        double budgetSalaire = Double.parseDouble(req.getParameter("budgetSalaire"));

        Departement d = new Departement();
        d.setNom(nom);
        d.setResponsable(responsable);
        d.setBudgetSalaire(budgetSalaire);
        departementDAO.create(d);

        // Sauvegarder les postes
        String[] titres = req.getParameterValues("posteTitre");
        String[] descriptions = req.getParameterValues("posteDescription");
        if (titres != null) {
            for (int i = 0; i < titres.length; i++) {
                if (titres[i] != null && !titres[i].trim().isEmpty()) {
                    Poste p = new Poste();
                    p.setTitre(titres[i].trim());
                    p.setDescription(descriptions != null && i < descriptions.length ? descriptions[i] : "");
                    p.setDepartementId(d.getId());
                    posteDAO.create(p);
                }
            }
        }

        resp.sendRedirect("departement?action=list");
    }

    private void updateDepartement(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        String nom = req.getParameter("nom");
        String responsable = req.getParameter("responsable");
        double budgetSalaire = Double.parseDouble(req.getParameter("budgetSalaire"));

        Departement d = new Departement();
        d.setId(id);
        d.setNom(nom);
        d.setResponsable(responsable);
        d.setBudgetSalaire(budgetSalaire);
        departementDAO.update(d);

        // Mettre à jour les postes
        posteDAO.deleteByDepartementId(id);
        String[] titres = req.getParameterValues("posteTitre");
        String[] descriptions = req.getParameterValues("posteDescription");
        if (titres != null) {
            for (int i = 0; i < titres.length; i++) {
                if (titres[i] != null && !titres[i].trim().isEmpty()) {
                    Poste p = new Poste();
                    p.setTitre(titres[i].trim());
                    p.setDescription(descriptions != null && i < descriptions.length ? descriptions[i] : "");
                    p.setDepartementId(id);
                    posteDAO.create(p);
                }
            }
        }

        resp.sendRedirect("departement?action=list");
    }

    private void deleteDepartement(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        posteDAO.deleteByDepartementId(id);
        departementDAO.delete(id);
        resp.sendRedirect("departement?action=list");
    }

    // Retourne les postes d'un département en JSON pour AJAX
    private void getPostesByDepartement(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long departementId = Long.parseLong(req.getParameter("departementId"));
        List<Poste> postes = posteDAO.findByDepartementId(departementId);

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < postes.size(); i++) {
            Poste p = postes.get(i);
            json.append("{\"id\":").append(p.getId())
                .append(",\"titre\":\"").append(p.getTitre()).append("\"}");
            if (i < postes.size() - 1) json.append(",");
        }
        json.append("]");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json.toString());
    }
}