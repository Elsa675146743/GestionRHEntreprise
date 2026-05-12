package com.rh.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        HttpSession session = req.getSession();
        String uri = req.getRequestURI();
        
        // Pages et ressources accessibles sans connexion
        if (uri.endsWith("/login") || 
            uri.contains("/css/") || 
            uri.contains("/fonts/") ||
            uri.contains("/uploads/") ||
            uri.contains(".css") ||
            uri.contains(".png") ||
            uri.contains(".jpg") ||
            uri.contains(".jpeg") ||
            uri.contains(".gif") ||
            uri.contains(".ico") ||
            uri.contains("material-icons") ||
            uri.contains("fonts.googleapis.com") ||
            uri.contains("fonts.gstatic.com")) {
            chain.doFilter(request, response);
            return;
        }
        
        // Vérifier si l'utilisateur est connecté
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {}
}