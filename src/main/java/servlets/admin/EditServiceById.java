/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 ************************************************************/
package servlets.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.category.Category;
import models.category.CategoryDAO;
import models.service.Service;
import models.service.ServiceDAO;

@WebServlet("/admin/edit-service/*")
@MultipartConfig
public class EditServiceById extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServiceDAO serviceDAO;
	private CategoryDAO categoryDAO;

	public EditServiceById() {
		super();
		serviceDAO = new ServiceDAO();
		categoryDAO = new CategoryDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int serviceId = Integer.parseInt(request.getPathInfo().substring(1));
			Service service = serviceDAO.getServiceById(serviceId);
			ArrayList<Category> categories = categoryDAO.getAllCategories();

			if (service != null) {
				request.setAttribute("service", service);
				request.setAttribute("categories", categories);
				request.getRequestDispatcher("/admin/editService.jsp").forward(request, response);
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
			int serviceId = Integer.parseInt(request.getPathInfo().substring(1));

			// Get and validate form data

			String serviceName = request.getParameter("serviceName");
			String description = request.getParameter("description");
			String categoryIdStr = request.getParameter("categoryId");
			String priceStr = request.getParameter("price");
			Part imagePart = request.getPart("serviceImage");
			boolean isActive = request.getParameter("isActive") != null;

			// Validate service name
			if (serviceName == null || serviceName.trim().isEmpty()) {
				errors.put("serviceName", "Service name is required");
			} else if (serviceName.length() > 100) {
				errors.put("serviceName", "Service name must be less than 100 characters");
			}

			// Validate description
			if (description != null && description.length() > 255) {
				errors.put("description", "Description must be less than 500 characters");
			}

			// Validate category
			int categoryId = 0;
			if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
				errors.put("categoryId", "Category is required");
			} else {
				try {
					categoryId = Integer.parseInt(categoryIdStr);
					if (categoryId <= 0) {
						errors.put("categoryId", "Invalid category selected");
					}
				} catch (NumberFormatException e) {
					errors.put("categoryId", "Invalid category format");
				}
			}

			// Validate price
			float price = 0.0f;
			if (priceStr == null || priceStr.trim().isEmpty()) {
				errors.put("price", "Price is required");
			} else {
				try {
					price = Float.parseFloat(priceStr);
					if (price < 0) {
						errors.put("price", "Price cannot be negative");
					} else if (price > 99999) {
						errors.put("price", "Price exceeds maximum allowed value of 99999");
					}
				} catch (NumberFormatException e) {
					errors.put("price", "Invalid price format");
				}
			}

			if (!errors.isEmpty()) {
				HttpSession session = request.getSession();
				session.setAttribute("editServiceFormErrors", errors);
				response.sendRedirect(request.getContextPath() + "/admin/edit-service/" + serviceId);
				return;
			}

			// Create and update service
			Service service = new Service();
			service.setServiceId(serviceId);
			service.setServiceName(serviceName);
			service.setServiceDescription(description);
			service.setCategoryId(categoryId);
			service.setPrice(price);
			service.setIsActive(isActive);

			boolean success = serviceDAO.updateService(service, imagePart);

			if (success) {
				HttpSession session = request.getSession();
				session.removeAttribute("editServiceFormErrors");
				response.sendRedirect(request.getContextPath() + "/admin");
			} else {
				throw new SQLException("Failed to update service");
			}

		} catch (SQLException e) {
			request.setAttribute("err", "Database error: " + e.getMessage());
			request.getRequestDispatcher("/error/500").forward(request, response);
		}
	}
}