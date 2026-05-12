package com.rh.servlet;

import com.rh.util.SmsUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SmsServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String phoneNumber = req.getParameter("phoneNumber");
        String message = req.getParameter("message");
        
        if (phoneNumber != null && !phoneNumber.isEmpty() && message != null && !message.isEmpty()) {
            SmsUtil.sendSms(phoneNumber, message);
            req.setAttribute("smsSuccess", "SMS envoyé avec succès !");
        } else {
            req.setAttribute("smsError", "Numéro ou message invalide");
        }
        
        // Rediriger vers la page précédente
        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer != null ? referer : "dashboard");
    }
}