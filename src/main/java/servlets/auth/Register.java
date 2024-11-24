package servlets.auth;

import models.user.User;
import models.user.UserDAO;
import util.org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("userId") != null && session.getAttribute("role") != null) {
			response.sendRedirect(request.getContextPath() + "/profile");
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/user/register.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phoneNo = request.getParameter("phoneNo");

		// Validate input fields
		if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()
				|| email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()
				|| phoneNo == null || phoneNo.trim().isEmpty()) {

			request.getSession().setAttribute("errMsg", "All fields are required.");
			response.sendRedirect(request.getContextPath() + "/register");
			return;
		}

		// Hash password
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));

		UserDAO userDB = new UserDAO();
		try {
			Integer rowsAffected = userDB.registerUser(firstName, lastName, email, hashedPassword, phoneNo);
			if (rowsAffected > 0) {
				request.getSession().setAttribute("message", "Registration successful. Please log in.");
				response.sendRedirect(request.getContextPath() + "/login");
			} else {
				request.getSession().setAttribute("errMsg", "An error occurred during registration. Please try again.");
				response.sendRedirect(request.getContextPath() + "/register");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("errMsg", "An unexpected error occurred: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/register");
		}
	}

}
