package servlets.user;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.organization.OrganizationDAO;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/register-organization")
public class RegisterOrganization extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrganizationDAO organizationDAO;
 
	public RegisterOrganization() {
		super();
		this.organizationDAO = new  OrganizationDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/user/createOrganization.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    HttpSession session = request.getSession();
//	    Integer userId = (Integer) session.getAttribute("userId");
//	    
//	    if (userId == null) {
//	        response.sendRedirect(request.getContextPath() + "/login");
//	        return;
//	    }
	    Integer userId =1;
	    
	    String organizationName = request.getParameter("organizationName");
	    
	    if (organizationName == null || organizationName.trim().isEmpty()) {
	        request.setAttribute("error", "Organization name is required");
	        RequestDispatcher rd = request.getRequestDispatcher("/user/createOrganization.jsp");
	        rd.forward(request, response);
	        return;
	    }
	    
	    try {
	        int rowsAffected = organizationDAO.create(organizationName.trim(), userId);
	        
	        if (rowsAffected > 0) {
	            response.sendRedirect(request.getContextPath() + "/organizations");
	        } else {
	            request.setAttribute("error", "Failed to create organization. Please try again.");
	            RequestDispatcher rd = request.getRequestDispatcher("/user/createOrganization.jsp");
	            rd.forward(request, response);
	        }
	        
	    } catch (SQLException e) {
	        request.setAttribute("error", "Database error occurred. Please try again.");
	        RequestDispatcher rd = request.getRequestDispatcher("/user/createOrganization.jsp");
	        rd.forward(request, response);
	    }
	    catch (Exception e) {
	        request.setAttribute("error", "Internal Server Error.We are trying our best to fix the error.");
	        RequestDispatcher rd = request.getRequestDispatcher("/user/createOrganization.jsp");
	        rd.forward(request, response);
	    }
	}
	
	

}
