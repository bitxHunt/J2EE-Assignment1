/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 ************************************************************/
package servlets.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.category.Category;
import models.category.CategoryDAO;

@WebServlet("/admin/edit-category/*")
public class EditCategoryById extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;

	public EditCategoryById() {
		super();
		categoryDAO = new CategoryDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int categoryId = Integer.parseInt(request.getPathInfo().substring(1));
			Category category = categoryDAO.getCategoryById(categoryId);

			if (category != null) {
				boolean isDeleteable = categoryDAO.getCategoryDeleteableOrNot(categoryId);
				request.setAttribute("category", category);
				request.setAttribute("deleteable", isDeleteable);
				request.getRequestDispatcher("/admin/editCategory.jsp").forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/error/404ErrorPage.jsp");
			}
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/error/400ErrorPage.jsp");
		} catch (SQLException e) {
			request.setAttribute("err", "Database error: " + e.getMessage());
			request.getRequestDispatcher("/error/500").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Map<String, String> errors = new HashMap<>();
			int categoryId = Integer.parseInt(request.getPathInfo().substring(1));
			String categoryName = request.getParameter("categoryName");

			if (categoryName == null || categoryName.trim().isEmpty()) {
				errors.put("categoryName", "Category name is required");
			} else if (categoryName.length() > 100) {
				errors.put("categoryName", "Category name must be less than 100 characters");
			}

			if (!errors.isEmpty()) {
				HttpSession session = request.getSession();
				session.setAttribute("editCategoryErrors", errors);
				response.sendRedirect(request.getContextPath() + "/admin/edit-category/" + categoryId);
				return;
			}

			Category category = new Category();
			category.setCategoryId(categoryId);
			category.setCategoryName(categoryName);

			boolean success = categoryDAO.updateCategory(category);

			if (success) {
				HttpSession session = request.getSession();
				session.removeAttribute("editCategoryErrors");
				response.sendRedirect(request.getContextPath() + "/admin");
			} else {
				request.setAttribute("err", "Failed to update category");
				request.getRequestDispatcher("/error/500").forward(request, response);
			}
		} catch (SQLException e) {
			request.setAttribute("err", "Database error: " + e.getMessage());
			request.getRequestDispatcher("/error/500").forward(request, response);
		}
	}
}