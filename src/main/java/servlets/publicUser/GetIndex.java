/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package servlets.publicUser;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.category.Category;
import models.category.CategoryDAO;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class GetIndex
 */
@WebServlet("/")
public class GetIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetIndex() {
		super();
		// TODO Auto-generated constructor stub
		this.categoryDAO = new CategoryDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pathInfo = request.getServletPath();

		switch (pathInfo) {
		case "/":
			System.out.println("Running GET request for /");
			handleIndex(request, response);
			break;
		case "/about":
			System.out.println("Running GET request for /about");
			request.getRequestDispatcher("/public/aboutUs.jsp").forward(request, response); // Added leading slash
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/");
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void handleIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ArrayList<Category> categories = categoryDAO.getAllCategoriesWithServiceCount();
			request.setAttribute("categories", categories);
			request.getRequestDispatcher("public/index.jsp").forward(request, response);
		} catch (NullPointerException e) {
			System.out.println("Invalid Page: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("err", "Invalid Page. Please try again.");
			request.getRequestDispatcher("404ErrorPage.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("err/500").forward(request, response);
		}
	}

}
