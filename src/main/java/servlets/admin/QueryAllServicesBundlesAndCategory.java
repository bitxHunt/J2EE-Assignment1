package servlets.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.service.*;
import models.bundle.*;
import models.category.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/admin")
public class QueryAllServicesBundlesAndCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServiceDAO serviceDAO;
	private BundleDAO bundleDAO;
	private CategoryDAO categoryDAO;

	public QueryAllServicesBundlesAndCategory() {
		super();
		this.serviceDAO = new ServiceDAO();
		this.bundleDAO = new BundleDAO();
		this.categoryDAO = new CategoryDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ArrayList<Service> services = serviceDAO.getAllServices();
			ArrayList<Bundle> bundles = bundleDAO.getAllBundlesWithServices();
			ArrayList<Category> categories = categoryDAO.getAllCategories();

			request.setAttribute("services", services);
			request.setAttribute("bundles", bundles);
			request.setAttribute("categories", categories);

			RequestDispatcher rd = request.getRequestDispatcher("/admin/home.jsp");
			rd.forward(request, response);

		} catch (SQLException e) {
			request.setAttribute("err", "Database error: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/admin/createForm.jsp");
			rd.forward(request, response);
		}
	}
}