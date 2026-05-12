package com.rh.servlet;

import com.rh.dao.EmployeDao;
import com.rh.dao.DepartementDAO;
import com.rh.dao.UtilisateurDAO;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.dao.impl.DepartementDAOImpl;
import com.rh.dao.impl.UtilisateurDAOImpl;
import com.rh.model.Employe;
import com.rh.model.Departement;
import com.rh.model.Utilisateur;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.mindrot.jbcrypt.BCrypt;

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
    
    private static final long serialVersionUID = 1L;
    private EmployeDao employeDAO = new EmployeDaoImpl();
    private DepartementDAO departementDAO = new DepartementDAOImpl();
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl();  // AJOUTÉ
    
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
        
        String queryString = req.getQueryString();
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        
        System.out.println("=== DO POST ===");
        System.out.println("QueryString: " + queryString);
        
        if (isMultipart) {
            if (queryString != null && queryString.contains("update")) {
                updateEmployeWithPhoto(req, resp);
            } else {
                saveEmployeWithPhoto(req, resp);
            }
        } else {
            String action = req.getParameter("action");
            if ("update".equals(action)) {
                updateEmploye(req, resp);
            } else {
                saveEmploye(req, resp);
            }
        }
    }
    
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
    
    private void viewEmploye(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Employe employe = employeDAO.read(id);
        req.setAttribute("employe", employe);
        req.getRequestDispatcher("/WEB-INF/vues/employe/view.jsp").forward(req, resp);
    }
    
    // ==================== CRÉATION COMPTE UTILISATEUR ====================
    
    private void createUserAccount(Employe employe) {  // AJOUTÉ
        String prenomClean = employe.getPrenom().toLowerCase().trim().replaceAll("\\s+", "");
        String nomClean = employe.getNom().toLowerCase().trim().replaceAll("\\s+", "");
        String login = prenomClean + "." + nomClean;
        
        String defaultPassword = "password123";
        
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setLogin(login);
        utilisateur.setMdpHash(defaultPassword);  
        
        utilisateur.setRole("EMPLOYE");
        utilisateur.setEmployeId(employe.getId());
        utilisateur.setActif(true);
        
        utilisateurDAO.create(utilisateur);
        System.out.println("Compte utilisateur créé pour: " + login + " / " + defaultPassword);
    }
    
    private void saveEmploye(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
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
        createUserAccount(e);  // AJOUTÉ
        resp.sendRedirect("employe?action=list");
    }
    
    private void updateEmploye(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
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
        resp.sendRedirect("employe?action=list");
    }
    
    private void saveEmployeWithPhoto(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
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
                            System.out.println("Photo sauvegardée: " + filePath);
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
            createUserAccount(e);  // AJOUTÉ
            resp.sendRedirect("employe?action=list");
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Erreur lors de l'upload");
            showForm(req, resp);
        }
    }
    
    private void updateEmployeWithPhoto(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        
        // Récupérer l'ID depuis l'URL
        Long id = null;
        String queryString = req.getQueryString();
        if (queryString != null && queryString.contains("id=")) {
            String idStr = queryString.split("id=")[1].split("&")[0];
            id = Long.parseLong(idStr);
        }
        
        System.out.println("=== updateEmployeWithPhoto ===");
        System.out.println("ID récupéré depuis URL: " + id);
        
        if (id == null) {
            System.out.println("ERREUR: ID non trouvé dans l'URL");
            resp.sendRedirect("employe?action=list");
            return;
        }
        
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
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
                            System.out.println("Nouvelle photo sauvegardée: " + filePath);
                        }
                    }
                }
            }
            
            System.out.println("Tentative de mise à jour ID: " + id);
            System.out.println("Nouveau départementId: " + departementId);
            
            Employe existing = employeDAO.read(id);
            if (existing == null) {
                System.out.println("Employé non trouvé avec ID: " + id);
                resp.sendRedirect("employe?action=list");
                return;
            }
            
            if (photoFileName == null) {
                photoFileName = existing.getPhotoFilename();
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
            System.out.println("Employé modifié avec succès !");
            resp.sendRedirect("employe?action=list");
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Erreur lors de la mise à jour: " + e.getMessage());
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