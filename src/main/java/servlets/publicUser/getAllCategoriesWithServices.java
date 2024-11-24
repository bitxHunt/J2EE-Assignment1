package servlets.publicUser;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.bundle.BundleDAO;
import models.category.Category;
import models.category.CategoryDAO;
import models.service.Service;
import models.service.ServiceDAO;

@WebServlet("/categories")
public class getAllCategoriesWithServices extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public getAllCategoriesWithServices() {

		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			CategoryDAO categoryDAO = new CategoryDAO();
			ServiceDAO serviceDAO = new ServiceDAO();

			// Get all categories with their services count
			ArrayList<Category> categories = categoryDAO.getAllCategoriesWithServiceCount();

			request.setAttribute("categories", categories);
			request.getRequestDispatcher("/public/components/displayCategories.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}