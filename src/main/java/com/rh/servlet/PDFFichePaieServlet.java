package com.rh.servlet;

import com.rh.dao.FichePaieDAO;
import com.rh.dao.EmployeDao;
import com.rh.dao.impl.FichePaieDAOImpl;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.model.FichePaie;
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

public class PDFFichePaieServlet extends HttpServlet {
    
    private FichePaieDAO fichePaieDAO = new FichePaieDAOImpl();
    private EmployeDao employeDAO = new EmployeDaoImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        Long ficheId = Long.parseLong(req.getParameter("id"));
        FichePaie fiche = fichePaieDAO.read(ficheId);
        Employe employe = employeDAO.read(fiche.getEmployeId());
        
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=\"fiche_paie_" + employe.getMatricule() + "_" + fiche.getMois() + ".pdf\"");
        
        try {
            PdfWriter writer = new PdfWriter(resp.getOutputStream());
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);
            
            document.add(new Paragraph("FICHE DE PAIE").setBold().setFontSize(18));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Mois : " + fiche.getMois()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Employé : " + employe.getNom() + " " + employe.getPrenom()));
            document.add(new Paragraph("Matricule : " + employe.getMatricule()));
            document.add(new Paragraph("Poste : " + employe.getPoste()));
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("DÉTAIL DU SALAIRE").setBold().setFontSize(14));
            
            Table table = new Table(new float[]{2, 2});
            table.addCell("Salaire brut");
            table.addCell(fiche.getSalaireBrut() + " FCFA");
            table.addCell("Heures supplémentaires");
            table.addCell(fiche.getHeuresSup() + " h (" + fiche.getMontantHeuresSup() + " FCFA)");
            table.addCell("Primes");
            table.addCell(fiche.getPrimes() + " FCFA");
            table.addCell("Retenues (CNPS, Impôts, etc.)");
            table.addCell(fiche.getRetenues() + " FCFA");
            table.addCell("Salaire net à payer");
            table.addCell(fiche.getSalaireNet() + " FCFA");
            
            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total net à payer : " + fiche.getSalaireNet() + " FCFA").setBold());
            
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}