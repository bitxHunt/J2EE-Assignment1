package servlets.publicUser;


import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.bundle.Bundle;
import models.bundle.BundleDAO;

@WebServlet("/bundles")
public class GetAllBundles extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            BundleDAO bundleDAO = new BundleDAO();
            ArrayList<Bundle> bundles = bundleDAO.getAllActiveBundlesWithServices();
            
            request.setAttribute("bundles", bundles);
            request.getRequestDispatcher("/public/bundlePage.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/error/500ErrorPage.jsp").forward(request, response);
        }
    }
}