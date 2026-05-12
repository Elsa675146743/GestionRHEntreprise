package com.rh.servlet;

import com.rh.dao.FichePaieDAO;
import com.rh.dao.impl.FichePaieDAOImpl;
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

public class PDFRapportServlet extends HttpServlet {
    
    private FichePaieDAO fichePaieDAO = new FichePaieDAOImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=\"rapport_masse_salariale.pdf\"");
        
        try {
            PdfWriter writer = new PdfWriter(resp.getOutputStream());
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4.rotate());
            
            document.add(new Paragraph("RAPPORT MENSUEL - MASSE SALARIALE PAR DÉPARTEMENT").setBold().setFontSize(18));
            document.add(new Paragraph("Généré le " + new java.util.Date()));
            document.add(new Paragraph(" "));
            
            List<Object[]> stats = fichePaieDAO.getStatsParDepartement();
            
            Table table = new Table(new float[]{3, 2, 3});
            table.addCell("Département");
            table.addCell("Nombre d'employés");
            table.addCell("Masse salariale (FCFA)");
            
            double totalGeneral = 0;
            for (Object[] stat : stats) {
                String nom = (String) stat[0];
                int nbEmployes = (int) stat[1];
                double total = (double) stat[2];
                totalGeneral += total;
                table.addCell(nom);
                table.addCell(String.valueOf(nbEmployes));
                table.addCell(String.format("%,.0f", total));
            }
            
            table.addCell("TOTAL GÉNÉRAL");
            table.addCell("");
            table.addCell(String.format("%,.0f", totalGeneral));
            
            document.add(table);
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}