/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 ************************************************************/
package servlets.user;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.organization.Organization;
import models.organization.OrganizationDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/organizations")
public class ViewOrganization extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrganizationDAO organizationDAO;

	public void init() {
		organizationDAO = new OrganizationDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");

		if (userId == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		try {
			ArrayList<Organization> organizations = organizationDAO.getOrganizationsByUserId(userId);
			request.setAttribute("organizations", organizations);

		} catch (SQLException e) {
			request.setAttribute("error", "Failed to retrieve organizations.");
		}

		RequestDispatcher rd = request.getRequestDispatcher("/user/organizationView.jsp");
		rd.forward(request, response);
	
	}
}