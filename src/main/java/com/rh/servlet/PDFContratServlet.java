package com.rh.servlet;

import com.rh.dao.ContratDAO;
import com.rh.dao.EmployeDao;
import com.rh.dao.impl.ContratDAOImpl;
import com.rh.dao.impl.EmployeDaoImpl;
import com.rh.model.Contrat;
import com.rh.model.Employe;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            document.setMargins(50, 50, 50, 50);
            
            // ========== ENTÊTE ==========
            Paragraph companyName = new Paragraph("GESTION RH ENTERPRISE")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(companyName);
            
            Paragraph companyAddress = new Paragraph("Siège social : Douala, Cameroun | Tél: +237 600 000 000")
                    .setFontSize(9)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(companyAddress);
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph("_________________________________________________________________________________"));
            document.add(new Paragraph(" "));
            
            // Titre
            String typeContratTexte = "";
            if ("CDI".equals(contrat.getTypeContrat())) {
                typeContratTexte = "CONTRAT DE TRAVAIL À DURÉE INDÉTERMINÉE (CDI)";
            } else if ("CDD".equals(contrat.getTypeContrat())) {
                typeContratTexte = "CONTRAT DE TRAVAIL À DURÉE DÉTERMINÉE (CDD)";
            } else if ("STAGE".equals(contrat.getTypeContrat())) {
                typeContratTexte = "CONVENTION DE STAGE";
            }
            
            Paragraph title = new Paragraph(typeContratTexte)
                    .setBold()
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            document.add(new Paragraph(" "));
            
            // ========== PRÉAMBULE ==========
            document.add(new Paragraph("ENTRE LES SOUSSIGNÉS :").setBold().setFontSize(11));
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("La société GESTION RH ENTERPRISE, société de droit camerounais, dont le siège social est situé à Douala, représentée par son Directeur Général,")
                    .setFontSize(10));
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("Ci-après dénommée \"L'ENTREPRISE\"")
                    .setItalic()
                    .setFontSize(10));
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("ET").setBold().setFontSize(11).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("Monsieur/Madame " + employe.getNom() + " " + employe.getPrenom() + 
                    ", demeurant à Douala, de nationalité camerounaise,")
                    .setFontSize(10));
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("Ci-après dénommé(e) \"L'EMPLOYÉ(E)\"")
                    .setItalic()
                    .setFontSize(10));
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("IL A ÉTÉ CONVENU ET ARRÊTÉ CE QUI SUIT :").setBold().setFontSize(11));
            document.add(new Paragraph(" "));
            
            // ========== ARTICLE 1 : ENGAGEMENT ==========
            document.add(new Paragraph("ARTICLE 1 : ENGAGEMENT").setBold().setFontSize(11));
            document.add(new Paragraph("L'ENTREPRISE engage l'EMPLOYÉ(E) à compter du " + contrat.getDateDebut() + 
                    ", en qualité de " + employe.getPoste() + ". L'EMPLOYÉ(E) s'engage à consacrer toute son activité professionnelle " +
                    "à l'ENTREPRISE et à respecter les directives qui lui seront données.").setFontSize(10));
            document.add(new Paragraph(" "));
            
            // ========== ARTICLE 2 : DURÉE ==========
            document.add(new Paragraph("ARTICLE 2 : DURÉE DU CONTRAT").setBold().setFontSize(11));
            if ("CDI".equals(contrat.getTypeContrat())) {
                document.add(new Paragraph("Le présent contrat est conclu pour une durée INDÉTERMINÉE (CDI). Il prend effet à compter du " + 
                        contrat.getDateDebut() + ". Il pourra être résilié par l'une ou l'autre des parties moyennant le respect d'un préavis " +
                        "conformément à la législation en vigueur au Cameroun.").setFontSize(10));
            } else if ("CDD".equals(contrat.getTypeContrat())) {
                document.add(new Paragraph("Le présent contrat est conclu pour une durée DÉTERMINÉE (CDD) allant du " + 
                        contrat.getDateDebut() + " au " + contrat.getDateFin() + ". Il prendra fin automatiquement à cette date.").setFontSize(10));
            } else {
                document.add(new Paragraph("Le présent document constitue une CONVENTION DE STAGE d'une durée allant du " + 
                        contrat.getDateDebut() + (contrat.getDateFin() != null ? " au " + contrat.getDateFin() : "") + 
                        ". Cette convention a pour objet de permettre à l'étudiant de compléter sa formation théorique.").setFontSize(10));
            }
            document.add(new Paragraph(" "));
            
            // ========== ARTICLE 3 : PÉRIODE D'ESSAI ==========
            document.add(new Paragraph("ARTICLE 3 : PÉRIODE D'ESSAI").setBold().setFontSize(11));
            document.add(new Paragraph("L'EMPLOYÉ(E) bénéficiera d'une période d'essai de trois (3) mois renouvelable une fois, " +
                    "conformément à la législation camerounaise.").setFontSize(10));
            document.add(new Paragraph(" "));
            
            // ========== ARTICLE 4 : RÉMUNÉRATION ==========
            document.add(new Paragraph("ARTICLE 4 : RÉMUNÉRATION").setBold().setFontSize(11));
            document.add(new Paragraph("En contrepartie de son travail, l'EMPLOYÉ(E) percevra un salaire mensuel brut de " + 
                    String.format("%,.0f", contrat.getSalaire()) + " francs CFA.").setFontSize(10));
            if (contrat.getAvantages() != null && !contrat.getAvantages().isEmpty()) {
                document.add(new Paragraph("Avantages annexes : " + contrat.getAvantages()).setFontSize(10).setItalic());
            }
            document.add(new Paragraph(" "));
            
            // ========== ARTICLE 5 : TEMPS DE TRAVAIL ==========
            document.add(new Paragraph("ARTICLE 5 : TEMPS DE TRAVAIL").setBold().setFontSize(11));
            document.add(new Paragraph("La durée hebdomadaire de travail est fixée à 40 heures, conformément à la législation " +
                    "camerounaise. Les heures supplémentaires seront majorées conformément à la réglementation en vigueur.")
                    .setFontSize(10));
            document.add(new Paragraph(" "));
            
            // ========== ARTICLE 6 : CONGÉS PAYÉS ==========
            document.add(new Paragraph("ARTICLE 6 : CONGÉS PAYÉS").setBold().setFontSize(11));
            document.add(new Paragraph("L'EMPLOYÉ(E) bénéficie de 2,5 jours ouvrables de congés payés par mois de travail effectif, " +
                    "soit 30 jours ouvrables par an.").setFontSize(10));
            document.add(new Paragraph(" "));
            
            // ========== ARTICLE 7 : OBLIGATIONS ==========
            document.add(new Paragraph("ARTICLE 7 : OBLIGATIONS DE L'EMPLOYÉ").setBold().setFontSize(11));
            document.add(new Paragraph("L'EMPLOYÉ(E) s'engage à exécuter les tâches confiées avec diligence, loyauté et discrétion, " +
                    "à respecter le règlement intérieur de l'ENTREPRISE et à observer les règles d'hygiène et de sécurité.")
                    .setFontSize(10));
            document.add(new Paragraph(" "));
            
            // ========== ARTICLE 8 : CONFIDENTIALITÉ ==========
            document.add(new Paragraph("ARTICLE 8 : CONFIDENTIALITÉ").setBold().setFontSize(11));
            document.add(new Paragraph("L'EMPLOYÉ(E) s'engage à ne pas divulguer, pendant la durée du contrat et après sa rupture, " +
                    "les informations confidentielles dont il/elle aurait eu connaissance dans le cadre de ses fonctions.")
                    .setFontSize(10));
            document.add(new Paragraph(" "));
            
            // ========== ARTICLE 9 : LITIGES ==========
            document.add(new Paragraph("ARTICLE 9 : RÈGLEMENT DES LITIGES").setBold().setFontSize(11));
            document.add(new Paragraph("Tout litige relatif à l'exécution ou à l'interprétation du présent contrat sera soumis " +
                    "à la juridiction compétente de Douala.").setFontSize(10));
            document.add(new Paragraph(" "));
            
            // ========== SIGNATURES ==========
            document.add(new Paragraph("_________________________________________________________________________________"));
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("Fait à Douala, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()))
                    .setFontSize(10));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("Signature de l'EMPLOYÉ(E)").setBold().setFontSize(10));
            document.add(new Paragraph("_________________________").setFontSize(10));
            document.add(new Paragraph("Précédé de la mention manuscrite \"Lu et approuvé\"").setFontSize(8).setItalic());
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("Signature de L'ENTREPRISE").setBold().setFontSize(10));
            document.add(new Paragraph("_________________________").setFontSize(10));
            document.add(new Paragraph("Le Directeur Général").setFontSize(8).setItalic());
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            
            // Pied de page
            Paragraph footer = new Paragraph("Original à conserver par chaque partie - Fait en deux exemplaires")
                    .setFontSize(8)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(footer);
            
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}