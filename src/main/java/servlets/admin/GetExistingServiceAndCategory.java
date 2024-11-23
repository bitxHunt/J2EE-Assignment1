package servlets.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.service.*;
import models.category.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/admin/createForm")
public class GetExistingServiceAndCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServiceDAO serviceDAO;
	private CategoryDAO categoryDAO;
	

	public GetExistingServiceAndCategory() {
		super();
		this.serviceDAO= new ServiceDAO();
		this.categoryDAO= new CategoryDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			ArrayList<Service> services = serviceDAO.getAllServices();
			ArrayList<Category> categories = categoryDAO.getAllCategories();

			request.setAttribute("services", services);
			request.setAttribute("categories", categories);

			RequestDispatcher rd = request.getRequestDispatcher("/admin/createForm.jsp");
			rd.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
			return;
		}
	}

}
