/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 ************************************************************/

package servlets.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.category.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/admin/create-new-category")
public class CreateNewCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;

	public CreateNewCategory() {
		super();
		this.categoryDAO = new CategoryDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String> errors = new HashMap<>();

		String categoryName = request.getParameter("categoryName");

		if (categoryName == null || categoryName.trim().isEmpty()) {
			errors.put("categoryName", "Category name is required");
		} else if (categoryName.length() > 100) {
			errors.put("categoryName", "Category name must be less than 100 characters");
		}

		if (!errors.isEmpty()) {
			HttpSession session = request.getSession();
			session.setAttribute("createFormErrors", errors);
			response.sendRedirect(request.getContextPath() + "/admin/createForm?tab=category");
			return;
		}

		try {
			boolean success = categoryDAO.createCategory(categoryName);

			if (success) {
				HttpSession session = request.getSession();
				session.removeAttribute("createFormErrors");
				response.sendRedirect(request.getContextPath() + "/admin");
			} else {
				throw new SQLException("Failed to create category");
			}

		} catch (SQLException e) {
			request.setAttribute("err", "Database error: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/error/500");
			rd.forward(request, response);
		}
	}
}