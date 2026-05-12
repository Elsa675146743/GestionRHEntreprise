package com.rh.servlet;

import com.rh.dao.ContratDAO;
import com.rh.dao.EmployeDao;
import com.rh.dao.impl.ContratDAOImpl;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.model.Contrat;
import com.rh.model.Employe;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PDFContratServlet extends HttpServlet {
    
    private ContratDAO contratDAO = new ContratDAOImpl();
    private EmployeDao employeDAO = new EmployeDaoImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        Long contratId = Long.parseLong(req.getParameter("id"));
        Contrat contrat = contratDAO.read(contratId);
        Employe employe = employeDAO.read(contrat.getEmployeId());
        
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=\"contrat_" + employe.getMatricule() + ".pdf\"");
        
        try {
            PdfWriter writer = new PdfWriter(resp.getOutputStream());
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);
            
            document.add(new Paragraph("CONTRAT DE TRAVAIL").setBold().setFontSize(18));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Entre les soussignés :").setBold());
            document.add(new Paragraph(" "));
            document.add(new Paragraph("L'entreprise : GESTION RH ENTREPRISE"));
            document.add(new Paragraph("Représentée par : Le Directeur Général"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("ET"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("L'employé : " + employe.getNom() + " " + employe.getPrenom()));
            document.add(new Paragraph("Matricule : " + employe.getMatricule()));
            document.add(new Paragraph("Adresse : " + employe.getEmail()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("ARTICLE 1 : OBJET").setBold());
            document.add(new Paragraph("Le présent contrat a pour objet de définir les conditions d'emploi de l'employé au sein de l'entreprise."));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("ARTICLE 2 : TYPE DE CONTRAT").setBold());
            document.add(new Paragraph("Type de contrat : " + contrat.getTypeContrat()));
            document.add(new Paragraph("Date de début : " + contrat.getDateDebut()));
            if (contrat.getDateFin() != null) {
                document.add(new Paragraph("Date de fin : " + contrat.getDateFin()));
            }
            document.add(new Paragraph(" "));
            document.add(new Paragraph("ARTICLE 3 : REMUNERATION").setBold());
            document.add(new Paragraph("Salaire mensuel brut : " + contrat.getSalaire() + " FCFA"));
            if (contrat.getAvantages() != null && !contrat.getAvantages().isEmpty()) {
                document.add(new Paragraph("Avantages : " + contrat.getAvantages()));
            }
            document.add(new Paragraph(" "));
            document.add(new Paragraph("ARTICLE 4 : DUREE").setBold());
            if (contrat.getDateFin() != null) {
                document.add(new Paragraph("Ce contrat est à durée déterminée jusqu'au " + contrat.getDateFin()));
            } else {
                document.add(new Paragraph("Ce contrat est à durée indéterminée (CDI)"));
            }
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Fait à Douala, le " + new java.util.Date()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Signature de l'employé").setBold());
            document.add(new Paragraph("............................."));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Signature de l'entreprise").setBold());
            document.add(new Paragraph("............................."));
            
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}