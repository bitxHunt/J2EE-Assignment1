/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 ************************************************************/
package servlets.publicUser;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.category.Category;
import models.category.CategoryDAO;
import models.service.Service;
import models.service.ServiceDAO;

@WebServlet("/services")
public class GetServicesByCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetServicesByCategory() {

		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String categoryIdStr = request.getParameter("category");

			if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
				int categoryId = Integer.parseInt(categoryIdStr);

				CategoryDAO categoryDAO = new CategoryDAO();
				ServiceDAO serviceDAO = new ServiceDAO();

				Category category = categoryDAO.getCategoryById(categoryId);

				// Check for null category before setting attributes
				if (category == null) {
					response.sendRedirect(request.getContextPath() + "/error/404ErrorPage.jsp");
					return;
				}

				ArrayList<Service> services = serviceDAO.getServicesByCategory(categoryId);
				request.setAttribute("category", category);
				request.setAttribute("services", services);
				request.getRequestDispatcher("/public/servicePage.jsp").forward(request, response);

			} else {
				response.sendRedirect(request.getContextPath() + "/categories");
			}

		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/error/400ErrorPage.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			// Use forward instead of sendError for error handling
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("/error/500ErrorPage.jsp").forward(request, response);
		}
	}
}