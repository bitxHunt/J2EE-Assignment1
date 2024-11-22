package servlets.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.bundle.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;

@WebServlet("/admin/create-new-bundle")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 15)
public class CreateNewBundle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BundleDAO bundleDAO;

	public CreateNewBundle() {
		super();
		this.bundleDAO = new BundleDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Create a map to store validation errors
		Map<String, String> errors = new HashMap<>();

		// Get form data
		String bundleName = request.getParameter("bundleName");
		String discountStr = request.getParameter("discount");
		String[] selectedServices = request.getParameterValues("selectedServices");
		Part imagePart = request.getPart("bundleImage");

		// Validate bundle name
		if (bundleName == null || bundleName.trim().isEmpty()) {
			errors.put("bundleName", "Bundle name is required");
		} else if (bundleName.length() > 100) {
			errors.put("bundleName", "Bundle name must be less than 100 characters");
		}

		// Validate discount
		int discount = 0;
		if (discountStr == null || discountStr.trim().isEmpty()) {
			errors.put("discount", "Discount is required");
		} else {
			try {
				discount = Integer.parseInt(discountStr);
				if (discount < 0) {
					errors.put("discount", "Discount cannot be negative");
				} else if (discount > 100) {
					errors.put("discount", "Discount cannot exceed 100%");
				}
			} catch (NumberFormatException e) {
				errors.put("discount", "Invalid discount format");
			}
		}

		// Validate selected services
		if (selectedServices == null || selectedServices.length < 2) {
			errors.put("selectedServices", "Please select at least 2 services");
		}

		// If there are any validation errors
		if (!errors.isEmpty()) {
			HttpSession session = request.getSession();
			session.setAttribute("createFormErrors", errors);

			// Add the tab parameter to keep user on the bundle form
			response.sendRedirect(request.getContextPath() + "/admin/createForm?tab=bundle");
			return;
		}
		// If validation passes, create the bundle
		try {
			Bundle newBundle = new Bundle();
			newBundle.setBundleName(bundleName);
			newBundle.setDiscountPercent(discount);

			// Convert selected services to List<Integer>
			List<Integer> serviceIds = Arrays.stream(selectedServices).map(Integer::parseInt)
					.collect(Collectors.toList());

			boolean success = bundleDAO.createBundle(newBundle, serviceIds,imagePart);
			if (success) {
				// Clear any existing session data
				HttpSession session = request.getSession();
				session.removeAttribute("createFormErrors");

				// Redirect to success page or listing
				response.sendRedirect(request.getContextPath() + "/admin");
			} else {
				throw new SQLException("Failed to create bundle");
			}

		} catch (SQLException e) {
			request.setAttribute("err", "Database error: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/admin/createForm.jsp");
			rd.forward(request, response);
		}
	}
}