package servlets.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.service.*;
import models.category.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/admin/create-new-service")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 15)
public class CreateNewService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServiceDAO serviceDAO;

	public CreateNewService() {
		super();
		this.serviceDAO = new ServiceDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Create a map to store validation errors
		Map<String, String> errors = new HashMap<>();

		// Get form data
		String serviceName = request.getParameter("serviceName");
		String description = request.getParameter("description");
		String categoryIdStr = request.getParameter("categoryId");
		String priceStr = request.getParameter("price");
		Part imagePart = request.getPart("serviceImage");

		// Validate service name
		if (serviceName == null || serviceName.trim().isEmpty()) {
			errors.put("serviceName", "Service name is required");
		} else if (serviceName.length() > 100) { // Adjust max length as needed
			errors.put("serviceName", "Service name must be less than 100 characters");
		}

		// Validate description
		if (description.length() > 255) { // Adjust max length as needed
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
				} else if (price > 99999) { // Add reasonable maximum price
					errors.put("price", "Price exceeds maximum allowed value of 99999");
				}
			} catch (NumberFormatException e) {
				errors.put("price", "Invalid price format");
			}
		}

		// If there are any validation errors
		if (!errors.isEmpty()) {
			// Store the errors and form data in session for showing in the form
			HttpSession session = request.getSession();
			session.setAttribute("createFormErrors", errors);

			// Redirect back to the form
			response.sendRedirect(request.getContextPath() + "/admin/createForm");
			return;
		}

		// If validation passes, create the service
		try {
			Service newService = new Service();
			newService.setServiceName(serviceName);
			newService.setServiceDescription(description);
			newService.setCategoryId(categoryId);
			newService.setPrice(price);

			boolean success = serviceDAO.createService(newService, imagePart);

			if (success) {
				// Clear any existing session data
				HttpSession session = request.getSession();
				session.removeAttribute("createFormErrors");

				// Redirect to success page or listing
				response.sendRedirect(request.getContextPath() + "/admin");
			} else {
				throw new SQLException("Failed to create service");
			}

		} catch (SQLException e) {
			request.setAttribute("err", "Database error: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/admin/createForm.jsp");
			rd.forward(request, response);
		}
	}

}
