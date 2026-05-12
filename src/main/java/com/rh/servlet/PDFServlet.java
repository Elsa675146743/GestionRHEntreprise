package com.rh.servlet;

import com.rh.dao.EmployeDao;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.model.Employe;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PDFServlet extends HttpServlet {
    
    private EmployeDao employeDAO = new EmployeDaoImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=\"liste_employes.pdf\"");
        
        try {
            PdfWriter writer = new PdfWriter(resp.getOutputStream());
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);
            
            document.add(new Paragraph("Liste des employés").setBold().setFontSize(18));
            document.add(new Paragraph("Généré le " + new java.util.Date()));
            document.add(new Paragraph(" "));
            
            Table table = new Table(new float[]{1, 2, 2, 2, 3});
            table.addCell("ID");
            table.addCell("Matricule");
            table.addCell("Nom");
            table.addCell("Prénom");
            table.addCell("Email");
            
            List<Employe> employes = employeDAO.findAll();
            for (Employe e : employes) {
                table.addCell(String.valueOf(e.getId()));
                table.addCell(e.getMatricule());
                table.addCell(e.getNom());
                table.addCell(e.getPrenom());
                table.addCell(e.getEmail());
            }
            
            document.add(table);
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}