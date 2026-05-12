package com.rh.servlet;

import com.rh.dao.EmployeDao;
import com.rh.dao.DepartementDAO;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.dao.impl.DepartementDAOImpl;
import com.rh.model.Employe;
import com.rh.model.Departement;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeServlet extends HttpServlet {
    
    private EmployeDao employeDAO = new EmployeDaoImpl();
    private DepartementDAO departementDAO = new DepartementDAOImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "list":
                listEmployes(req, resp);
                break;
            case "search":
                searchEmployes(req, resp);
                break;
            case "add":
                showForm(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteEmploye(req, resp);
                break;
            case "view":
                viewEmploye(req, resp);
                break;
            default:
                listEmployes(req, resp);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // Récupérer l'action depuis le paramètre du formulaire
        String action = req.getParameter("action");
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        
        System.out.println("=== DO POST ===");
        System.out.println("Action: " + action);
        System.out.println("IsMultipart: " + isMultipart);
        
        if (isMultipart) {
            // Formulaire avec photo
            if (action != null && action.equals("update")) {
                System.out.println("Appel de updateEmployeWithPhoto");
                updateEmployeWithPhoto(req, resp);
            } else {
                System.out.println("Appel de saveEmployeWithPhoto");
                saveEmployeWithPhoto(req, resp);
            }
        } else {
            // Formulaire sans photo
            if (action != null && action.equals("update")) {
                System.out.println("Appel de updateEmploye");
                updateEmploye(req, resp);
            } else {
                System.out.println("Appel de saveEmploye");
                saveEmploye(req, resp);
            }
        }
    }
    
    // ==================== LISTE AVEC PAGINATION ====================
    
    private void listEmployes(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        int page = 1;
        int recordsPerPage = 5;
        
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        
        int offset = (page - 1) * recordsPerPage;
        List<Employe> employes = employeDAO.findAllPaginated(offset, recordsPerPage);
        int totalRecords = employeDAO.countAll();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        
        req.setAttribute("employes", employes);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalRecords", totalRecords);
        
        req.getRequestDispatcher("/WEB-INF/vues/employe/liste.jsp").forward(req, resp);
    }
    
    // ==================== RECHERCHE PAR MOT-CLÉ + PAGINATION ====================
    
    private void searchEmployes(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String keyword = req.getParameter("keyword");
        int page = 1;
        int recordsPerPage = 5;
        
        if (keyword == null) keyword = "";
        
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        
        int offset = (page - 1) * recordsPerPage;
        List<Employe> employes = employeDAO.searchByKeyword(keyword, offset, recordsPerPage);
        int totalRecords = employeDAO.countSearch(keyword);
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        
        req.setAttribute("employes", employes);
        req.setAttribute("keyword", keyword);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalRecords", totalRecords);
        
        req.getRequestDispatcher("/WEB-INF/vues/employe/liste.jsp").forward(req, resp);
    }
    
    // ==================== FORMULAIRES ====================
    
    private void showForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Departement> departements = departementDAO.findAll();
        req.setAttribute("departements", departements);
        req.getRequestDispatcher("/WEB-INF/vues/employe/form.jsp").forward(req, resp);
    }
    
    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Employe employe = employeDAO.read(id);
        List<Departement> departements = departementDAO.findAll();
        req.setAttribute("employe", employe);
        req.setAttribute("departements", departements);
        req.getRequestDispatcher("/WEB-INF/vues/employe/form.jsp").forward(req, resp);
    }
    
    // ==================== VOIR DÉTAILS ====================
    
    private void viewEmploye(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Employe employe = employeDAO.read(id);
        req.setAttribute("employe", employe);
        req.getRequestDispatcher("/WEB-INF/vues/employe/view.jsp").forward(req, resp);
    }
    
    // ==================== CRUD SANS PHOTO ====================
    
    private void saveEmploye(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        System.out.println("=== SAVE EMPLOYE (sans photo) ===");
        
        String matricule = req.getParameter("matricule");
        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String poste = req.getParameter("poste");
        Long departementId = Long.parseLong(req.getParameter("departementId"));
        LocalDate dateEmbauche = LocalDate.parse(req.getParameter("dateEmbauche"));
        double salaireBase = Double.parseDouble(req.getParameter("salaireBase"));
        String typeContrat = req.getParameter("typeContrat");
        String telephone = req.getParameter("telephone");
        String email = req.getParameter("email");
        
        Employe e = new Employe();
        e.setMatricule(matricule);
        e.setNom(nom);
        e.setPrenom(prenom);
        e.setPoste(poste);
        e.setDepartementId(departementId);
        e.setDateEmbauche(dateEmbauche);
        e.setSalaireBase(salaireBase);
        e.setTypeContrat(typeContrat);
        e.setTelephone(telephone);
        e.setEmail(email);
        e.setPhotoFilename(null);
        e.setSoldeCongesJours(0);
        
        employeDAO.create(e);
        System.out.println("Employé créé avec ID: " + e.getId());
        resp.sendRedirect("employe?action=list");
    }
    
    private void updateEmploye(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        System.out.println("=== UPDATE EMPLOYE (sans photo) ===");
        
        Long id = Long.parseLong(req.getParameter("id"));
        String matricule = req.getParameter("matricule");
        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String poste = req.getParameter("poste");
        Long departementId = Long.parseLong(req.getParameter("departementId"));
        LocalDate dateEmbauche = LocalDate.parse(req.getParameter("dateEmbauche"));
        double salaireBase = Double.parseDouble(req.getParameter("salaireBase"));
        String typeContrat = req.getParameter("typeContrat");
        String telephone = req.getParameter("telephone");
        String email = req.getParameter("email");
        
        System.out.println("ID à modifier: " + id);
        System.out.println("Nouveau nom: " + nom);
        
        // Récupérer l'employé existant pour garder sa photo
        Employe existing = employeDAO.read(id);
        
        Employe e = new Employe();
        e.setId(id);
        e.setMatricule(matricule);
        e.setNom(nom);
        e.setPrenom(prenom);
        e.setPoste(poste);
        e.setDepartementId(departementId);
        e.setDateEmbauche(dateEmbauche);
        e.setSalaireBase(salaireBase);
        e.setTypeContrat(typeContrat);
        e.setTelephone(telephone);
        e.setEmail(email);
        e.setPhotoFilename(existing.getPhotoFilename());
        e.setSoldeCongesJours(existing.getSoldeCongesJours());
        
        employeDAO.update(e);
        System.out.println("Employé modifié avec succès");
        resp.sendRedirect("employe?action=list");
    }
    
    // ==================== CRUD AVEC UPLOAD PHOTO ====================
    
    private void saveEmployeWithPhoto(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        
        System.out.println("=== SAVE EMPLOYE AVEC PHOTO ===");
        
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(1024 * 1024 * 2);
        
        String matricule = "", nom = "", prenom = "", poste = "", typeContrat = "", telephone = "", email = "";
        Long departementId = null;
        LocalDate dateEmbauche = null;
        double salaireBase = 0;
        String photoFileName = null;
        
        try {
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    switch (item.getFieldName()) {
                        case "matricule": matricule = item.getString(); break;
                        case "nom": nom = item.getString(); break;
                        case "prenom": prenom = item.getString(); break;
                        case "poste": poste = item.getString(); break;
                        case "departementId": departementId = Long.parseLong(item.getString()); break;
                        case "dateEmbauche": dateEmbauche = LocalDate.parse(item.getString()); break;
                        case "salaireBase": salaireBase = Double.parseDouble(item.getString()); break;
                        case "typeContrat": typeContrat = item.getString(); break;
                        case "telephone": telephone = item.getString(); break;
                        case "email": email = item.getString(); break;
                    }
                } else {
                    String fileName = Paths.get(item.getName()).getFileName().toString();
                    if (fileName != null && !fileName.isEmpty()) {
                        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                        if (fileExt.equals("jpg") || fileExt.equals("jpeg") || fileExt.equals("png")) {
                            photoFileName = System.currentTimeMillis() + "_" + fileName;
                            String filePath = uploadPath + File.separator + photoFileName;
                            item.write(new File(filePath));
                            System.out.println("Photo sauvegardée: " + photoFileName);
                        }
                    }
                }
            }
            
            Employe e = new Employe();
            e.setMatricule(matricule);
            e.setNom(nom);
            e.setPrenom(prenom);
            e.setPoste(poste);
            e.setDepartementId(departementId);
            e.setDateEmbauche(dateEmbauche);
            e.setSalaireBase(salaireBase);
            e.setTypeContrat(typeContrat);
            e.setTelephone(telephone);
            e.setEmail(email);
            e.setPhotoFilename(photoFileName);
            e.setSoldeCongesJours(0);
            
            employeDAO.create(e);
            System.out.println("Employé créé avec photo");
            resp.sendRedirect("employe?action=list");
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Erreur lors de l'upload");
            showForm(req, resp);
        }
    }
    
    private void updateEmployeWithPhoto(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        
        System.out.println("=== UPDATE EMPLOYE AVEC PHOTO ===");
        
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(1024 * 1024 * 2);
        
        Long id = null;
        String matricule = "", nom = "", prenom = "", poste = "", typeContrat = "", telephone = "", email = "";
        Long departementId = null;
        LocalDate dateEmbauche = null;
        double salaireBase = 0;
        String photoFileName = null;
        
        try {
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    switch (item.getFieldName()) {
                        case "id": id = Long.parseLong(item.getString()); break;
                        case "matricule": matricule = item.getString(); break;
                        case "nom": nom = item.getString(); break;
                        case "prenom": prenom = item.getString(); break;
                        case "poste": poste = item.getString(); break;
                        case "departementId": departementId = Long.parseLong(item.getString()); break;
                        case "dateEmbauche": dateEmbauche = LocalDate.parse(item.getString()); break;
                        case "salaireBase": salaireBase = Double.parseDouble(item.getString()); break;
                        case "typeContrat": typeContrat = item.getString(); break;
                        case "telephone": telephone = item.getString(); break;
                        case "email": email = item.getString(); break;
                    }
                } else {
                    String fileName = Paths.get(item.getName()).getFileName().toString();
                    if (fileName != null && !fileName.isEmpty()) {
                        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                        if (fileExt.equals("jpg") || fileExt.equals("jpeg") || fileExt.equals("png")) {
                            photoFileName = System.currentTimeMillis() + "_" + fileName;
                            String filePath = uploadPath + File.separator + photoFileName;
                            item.write(new File(filePath));
                            System.out.println("Nouvelle photo sauvegardée: " + photoFileName);
                        }
                    }
                }
            }
            
            System.out.println("ID à modifier: " + id);
            System.out.println("Nouveau nom: " + nom);
            
            // Récupérer l'employé existant pour garder l'ancienne photo si pas de nouvelle
            Employe existing = employeDAO.read(id);
            if (photoFileName == null) {
                photoFileName = existing.getPhotoFilename();
                System.out.println("Pas de nouvelle photo, on garde l'ancienne: " + photoFileName);
            }
            
            Employe e = new Employe();
            e.setId(id);
            e.setMatricule(matricule);
            e.setNom(nom);
            e.setPrenom(prenom);
            e.setPoste(poste);
            e.setDepartementId(departementId);
            e.setDateEmbauche(dateEmbauche);
            e.setSalaireBase(salaireBase);
            e.setTypeContrat(typeContrat);
            e.setTelephone(telephone);
            e.setEmail(email);
            e.setPhotoFilename(photoFileName);
            e.setSoldeCongesJours(existing.getSoldeCongesJours());
            
            employeDAO.update(e);
            System.out.println("Employé modifié avec succès");
            resp.sendRedirect("employe?action=list");
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Erreur lors de la mise à jour");
            showEditForm(req, resp);
        }
    }
    
    private void deleteEmploye(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        employeDAO.delete(id);
        resp.sendRedirect("employe?action=list");
    }
}