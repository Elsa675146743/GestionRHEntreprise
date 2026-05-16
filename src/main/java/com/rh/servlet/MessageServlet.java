package com.rh.servlet;

import com.rh.dao.MessageDAO;
import com.rh.dao.EmployeDao;
import com.rh.dao.impl.MessageDAOImpl;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.model.Message;
import com.rh.model.Utilisateur;
import com.rh.model.Employe;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MessageServlet extends HttpServlet {
    
    private MessageDAO messageDAO = new MessageDAOImpl();
    private EmployeDao employeDAO = new EmployeDaoImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        
        if (user == null) {
            resp.sendRedirect("login");
            return;
        }
        
        Employe employe = employeDAO.read(user.getEmployeId());
        String action = req.getParameter("action");
        
        if (action == null) action = "list";
        
        switch (action) {
            case "list":
                listMessages(req, resp, employe);
                break;
            case "sent":
                listSentMessages(req, resp, employe);
                break;
            case "send":
                showSendForm(req, resp, employe);
                break;
            case "view":
                viewMessage(req, resp, employe);
                break;
            case "reply":
                showReplyForm(req, resp, employe);
                break;
            default:
                listMessages(req, resp, employe);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        
        if (user == null) {
            resp.sendRedirect("login");
            return;
        }
        
        Employe employe = employeDAO.read(user.getEmployeId());
        String action = req.getParameter("action");
        
        if ("send".equals(action)) {
            sendMessage(req, resp, employe);
        } else if ("reply".equals(action)) {
            sendReply(req, resp, employe);
        } else {
            resp.sendRedirect("message?action=list");
        }
    }
    
    private void listMessages(HttpServletRequest req, HttpServletResponse resp, Employe employe)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        
        List<Message> messages = messageDAO.findByDestinataire(employe.getId());
        int nonLus = messageDAO.countNonLus(employe.getId());
        
        req.setAttribute("messages", messages);
        req.setAttribute("nonLus", nonLus);
        req.setAttribute("isRH", "RH".equals(user.getRole()));
        req.setAttribute("isDirecteur", "DIRECTEUR".equals(user.getRole()));
        
        req.getRequestDispatcher("/WEB-INF/vues/message/liste.jsp").forward(req, resp);
    }
    
    private void listSentMessages(HttpServletRequest req, HttpServletResponse resp, Employe employe)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        
        List<Message> envoyes = messageDAO.findByExpediteur(employe.getId());
        
        req.setAttribute("messages", envoyes);
        req.setAttribute("isRH", "RH".equals(user.getRole()));
        req.setAttribute("isDirecteur", "DIRECTEUR".equals(user.getRole()));
        
        req.getRequestDispatcher("/WEB-INF/vues/message/liste.jsp").forward(req, resp);
    }
    
    private void showSendForm(HttpServletRequest req, HttpServletResponse resp, Employe employe)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        String role = user.getRole();
        
        req.setAttribute("isRH", "RH".equals(role));
        req.setAttribute("isDirecteur", "DIRECTEUR".equals(role));
        
        System.out.println("=== showSendForm ===");
        System.out.println("Rôle: " + role);
        
        if ("RH".equals(role) || "DIRECTEUR".equals(role)) {
            // Pour RH et DIRECTEUR : charger TOUS les employés
            List<Employe> employes = employeDAO.findAll();
            req.setAttribute("employes", employes);
            System.out.println("Nb employés chargés pour la liste déroulante: " + employes.size());
        } else {
            // Pour EMPLOYÉ : charger uniquement les RH
            List<Employe> rhList = employeDAO.findByRole("RH");
            req.setAttribute("rhList", rhList);
            System.out.println("Nb RH chargés: " + rhList.size());
        }
        
        req.getRequestDispatcher("/WEB-INF/vues/message/form.jsp").forward(req, resp);
    }
    
    private void showReplyForm(HttpServletRequest req, HttpServletResponse resp, Employe employe)
            throws ServletException, IOException {
        
        Long parentId = Long.parseLong(req.getParameter("id"));
        Message parent = messageDAO.read(parentId);
        req.setAttribute("parent", parent);
        req.getRequestDispatcher("/WEB-INF/vues/message/reply.jsp").forward(req, resp);
    }
    
    private void sendMessage(HttpServletRequest req, HttpServletResponse resp, Employe employe)
            throws IOException {
        
        HttpSession session = req.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        String role = user.getRole();
        
        String sujet = req.getParameter("sujet");
        String contenu = req.getParameter("contenu");
        String type = req.getParameter("type");
        
        System.out.println("=== ENVOI MESSAGE ===");
        System.out.println("Expéditeur ID: " + employe.getId());
        System.out.println("Rôle: " + role);
        System.out.println("Type: " + type);
        
        Message message = new Message();
        message.setExpediteurId(employe.getId());
        message.setSujet(sujet);
        message.setContenu(contenu);
        message.setDateEnvoi(LocalDateTime.now());
        message.setLu(false);
        
        if ("GENERAL".equals(type)) {
            // Annonce générale à tous les employés
            message.setType("GENERAL");
            message.setDestinataireId(null);
            System.out.println("Envoi d'une annonce générale à tous les employés");
        } else {
            // Message privé
            message.setType("PRIVE");
            String destinataireIdStr = req.getParameter("destinataireId");
            if (destinataireIdStr != null && !destinataireIdStr.isEmpty()) {
                message.setDestinataireId(Long.parseLong(destinataireIdStr));
                System.out.println("Envoi d'un message privé à l'employé ID: " + destinataireIdStr);
            } else {
                System.out.println("ERREUR: Aucun destinataire sélectionné");
                return;
            }
        }
        
        messageDAO.create(message);
        System.out.println("Message envoyé avec succès, ID: " + message.getId());
        
        resp.sendRedirect("message?action=list");
    }
    private void sendReply(HttpServletRequest req, HttpServletResponse resp, Employe employe)
            throws IOException {
        
        Long parentId = Long.parseLong(req.getParameter("parentId"));
        String contenu = req.getParameter("contenu");
        Message parent = messageDAO.read(parentId);
        
        System.out.println("=== ENVOI REPONSE ===");
        System.out.println("Message parent ID: " + parentId);
        System.out.println("Destinataire: " + parent.getExpediteurId());
        
        Message message = new Message();
        message.setExpediteurId(employe.getId());
        message.setDestinataireId(parent.getExpediteurId());
        message.setSujet("Re: " + parent.getSujet());
        message.setContenu(contenu);
        message.setDateEnvoi(LocalDateTime.now());
        message.setLu(false);
        message.setType("PRIVE");
        
        messageDAO.create(message);
        System.out.println("Réponse envoyée avec succès");
        
        resp.sendRedirect("message?action=list");
    }
    private void viewMessage(HttpServletRequest req, HttpServletResponse resp, Employe employe)
            throws ServletException, IOException {
        
        Long id = Long.parseLong(req.getParameter("id"));
        Message message = messageDAO.read(id);
        
        // Marquer comme lu si le destinataire est l'employé connecté
        if (message.getDestinataireId() != null && message.getDestinataireId().equals(employe.getId())) {
            if (!message.isLu()) {
                messageDAO.marquerCommeLu(id);
                message.setLu(true);
            }
        }
        
        req.setAttribute("message", message);
        req.getRequestDispatcher("/WEB-INF/vues/message/view.jsp").forward(req, resp);
    }
}