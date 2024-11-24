package servlets.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.bundle.Bundle;
import models.bundle.BundleDAO;
import models.service.Service;
import models.service.ServiceDAO;

@WebServlet("/admin/edit-bundle/*")
@MultipartConfig
public class EditBundleById extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BundleDAO bundleDAO;
	private ServiceDAO serviceDAO;

	public EditBundleById() {
		super();
		bundleDAO = new BundleDAO();
		serviceDAO = new ServiceDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int bundleId = Integer.parseInt(request.getPathInfo().substring(1));
			Bundle bundle = bundleDAO.getBundleById(bundleId);
			ArrayList<Service> allServices = serviceDAO.getAllServices();

			if (bundle != null) {
				request.setAttribute("bundle", bundle);
				request.setAttribute("allServices", allServices);
				request.getRequestDispatcher("/admin/editBundle.jsp").forward(request, response);
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
			int bundleId = Integer.parseInt(request.getPathInfo().substring(1));

			// Get and validate form data
			String bundleName = request.getParameter("bundleName");
			String discountPercentStr = request.getParameter("discountPercent");
			String[] serviceIdsArr = request.getParameterValues("serviceIds");
			Part imagePart = request.getPart("bundleImage");
			boolean isActive = request.getParameter("isActive") != null;

			// Validate bundle name
			if (bundleName == null || bundleName.trim().isEmpty()) {
				errors.put("bundleName", "Bundle name is required");
			} else if (bundleName.length() > 100) {
				errors.put("bundleName", "Bundle name must be less than 100 characters");
			}

			// Validate discount percent
			int discountPercent = 0;
			if (discountPercentStr == null || discountPercentStr.trim().isEmpty()) {
				errors.put("discountPercent", "Discount percentage is required");
			} else {
				try {
					discountPercent = Integer.parseInt(discountPercentStr);
					if (discountPercent < 0 || discountPercent > 100) {
						errors.put("discountPercent", "Discount percentage must be between 0 and 100");
					}
				} catch (NumberFormatException e) {
					errors.put("discountPercent", "Invalid discount percentage format");
				}
			}

			// Validate services
			List<Integer> serviceIds = new ArrayList<>();
			if (serviceIdsArr == null || serviceIdsArr.length < 2) {
				errors.put("serviceIds", "At least two service must be selected");
			} else {
				for (String serviceId : serviceIdsArr) {
					serviceIds.add(Integer.parseInt(serviceId));
				}
			}

			if (!errors.isEmpty()) {
				HttpSession session = request.getSession();
				session.setAttribute("editBundleFormErrors", errors);
				response.sendRedirect(request.getContextPath() + "/admin/edit-bundle/" + bundleId);
				return;
			}

			// Create and update bundle
			Bundle bundle = new Bundle();
			bundle.setBundleId(bundleId);
			bundle.setBundleName(bundleName);
			bundle.setDiscountPercent(discountPercent);
			bundle.setIsActive(isActive);

			boolean success = bundleDAO.updateBundle(bundle, serviceIds, imagePart);

			if (success) {
				HttpSession session = request.getSession();
				session.removeAttribute("editBundleFormErrors");
				response.sendRedirect(request.getContextPath() + "/admin");
			} else {
				throw new SQLException("Failed to update bundle");
			}

		} catch (SQLException e) {
			request.setAttribute("err", "Database error: " + e.getMessage());
			request.getRequestDispatcher("/error/500").forward(request, response);
		}
	}
}