package com.rh.servlet;

import com.rh.dao.FichePaieDAO;
import com.rh.dao.EmployeDao;
import com.rh.dao.impl.FichePaieDAOImpl;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.model.FichePaie;
import com.rh.model.Employe;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            document.setMargins(50, 50, 50, 50);
            
            double salaireBrut = fiche.getSalaireBase() + fiche.getMontantHeuresSup() + fiche.getPrimes();
            
            // Entête
            Paragraph companyName = new Paragraph("GESTION RH ENTERPRISE");
            companyName.setBold();
            companyName.setFontSize(20);
            companyName.setTextAlignment(TextAlignment.CENTER);
            document.add(companyName);
            
            Paragraph companyInfo = new Paragraph("Douala, Cameroun | Tél: +237 600 000 000 | Email: contact@gestiorh.com");
            companyInfo.setFontSize(9);
            companyInfo.setFontColor(ColorConstants.GRAY);
            companyInfo.setTextAlignment(TextAlignment.CENTER);
            document.add(companyInfo);
            
            document.add(new Paragraph(" "));
            
            Paragraph title = new Paragraph("FICHE DE PAIE");
            title.setBold();
            title.setFontSize(16);
            title.setTextAlignment(TextAlignment.CENTER);
            title.setFontColor(ColorConstants.BLUE);
            document.add(title);
            
            document.add(new Paragraph("_________________________________________________________________________________"));
            document.add(new Paragraph(" "));
            
            // Informations employé
            Table infoTable = new Table(UnitValue.createPercentArray(new float[]{2, 3, 2, 3}));
            infoTable.setWidth(UnitValue.createPercentValue(100));
            infoTable.setMarginBottom(15);
            
            infoTable.addCell("N° Employé:");
            infoTable.addCell(employe.getMatricule());
            infoTable.addCell("Date d'édition:");
            infoTable.addCell(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            
            infoTable.addCell("Nom et prénoms:");
            infoTable.addCell(employe.getNom() + " " + employe.getPrenom());
            infoTable.addCell("Poste:");
            infoTable.addCell(employe.getPoste());
            
            infoTable.addCell("Département:");
            String deptNom = employe.getDepartementNom() != null ? employe.getDepartementNom() : "-";
            infoTable.addCell(deptNom);
            infoTable.addCell("Période:");
            infoTable.addCell(fiche.getMois());
            
            document.add(infoTable);
            document.add(new Paragraph(" "));
            
            // Tableau du salaire
            Table salaryTable = new Table(UnitValue.createPercentArray(new float[]{3, 2}));
            salaryTable.setWidth(UnitValue.createPercentValue(100));
            salaryTable.setMarginTop(10);
            
            Cell header1 = new Cell().add(new Paragraph("Libellé"));
            header1.setBold();
            header1.setBackgroundColor(ColorConstants.LIGHT_GRAY);
            salaryTable.addCell(header1);
            
            Cell header2 = new Cell().add(new Paragraph("Montant (FCFA)"));
            header2.setBold();
            header2.setBackgroundColor(ColorConstants.LIGHT_GRAY);
            salaryTable.addCell(header2);
            
            salaryTable.addCell("Salaire de base");
            salaryTable.addCell(String.format("%,.0f", fiche.getSalaireBase()));
            
            if (fiche.getHeuresSup() > 0) {
                salaryTable.addCell("Heures supplémentaires (" + fiche.getHeuresSup() + "h)");
                salaryTable.addCell(String.format("%,.0f", fiche.getMontantHeuresSup()));
            }
            
            if (fiche.getPrimes() > 0) {
                salaryTable.addCell("Primes et bonus");
                salaryTable.addCell(String.format("%,.0f", fiche.getPrimes()));
            }
            
            Cell brutLabel = new Cell().add(new Paragraph("SALAIRES BRUTS"));
            brutLabel.setBold();
            salaryTable.addCell(brutLabel);
            
            Cell brutValue = new Cell().add(new Paragraph(String.format("%,.0f", salaireBrut)));
            brutValue.setBold();
            salaryTable.addCell(brutValue);
            
            salaryTable.addCell("Retenues (CNPS, Impôts, etc.)");
            salaryTable.addCell(String.format("%,.0f", fiche.getRetenues()));
            
            Cell netLabel = new Cell().add(new Paragraph("NET À PAYER"));
            netLabel.setBold();
            netLabel.setBackgroundColor(ColorConstants.LIGHT_GRAY);
            salaryTable.addCell(netLabel);
            
            Cell netValue = new Cell().add(new Paragraph(String.format("%,.0f", fiche.getSalaireNet())));
            netValue.setBold();
            netValue.setBackgroundColor(ColorConstants.LIGHT_GRAY);
            salaryTable.addCell(netValue);
            
            document.add(salaryTable);
            document.add(new Paragraph(" "));
            
            Paragraph legal = new Paragraph("Montant net à payer arrêté à la somme de " + String.format("%,.0f", fiche.getSalaireNet()) + " francs CFA.");
            legal.setFontSize(9);
            legal.setItalic();
            document.add(legal);
            document.add(new Paragraph(" "));
            
            Table signatureTable = new Table(UnitValue.createPercentArray(new float[]{2, 2}));
            signatureTable.setWidth(UnitValue.createPercentValue(100));
            signatureTable.setMarginTop(30);
            
            signatureTable.addCell("Signature de l'employé\n\n_________________");
            signatureTable.addCell("Cachet et signature de l'entreprise\n\n_________________");
            
            document.add(signatureTable);
            document.add(new Paragraph(" "));
            
            Paragraph footer = new Paragraph("Document généré automatiquement - Fait à Douala, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            footer.setFontSize(8);
            footer.setFontColor(ColorConstants.GRAY);
            footer.setTextAlignment(TextAlignment.CENTER);
            document.add(footer);
            
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}