	package servlets.auth;

import middlewares.JWTMiddleware;
import models.user.*;
import models.status.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class Verify
 */
@WebServlet("/verify")
public class Verify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Verify() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Initialize objects
		JWTMiddleware jwt = new JWTMiddleware();
		UserDAO userDB = new UserDAO();
		HttpSession session = request.getSession();

		try {
			// Retrieve the JWT token from the URL query parameter
			String token = request.getParameter("token");

			if (token == null || token.isEmpty()) {
				// Set an error message for missing token
				request.setAttribute("errorMessage", "Token is missing or invalid.");
				RequestDispatcher rd = request.getRequestDispatcher("/user/verification.jsp");
				rd.forward(request, response);
				return;
			}

			// Verify and decode the token to extract the userId
			int userId = jwt.verifyToken(token);

			if (userId <= 0) {
				// Set an error message for invalid or unverified token
				request.setAttribute("errorMessage", "Invalid or expired token.");
				RequestDispatcher rd = request.getRequestDispatcher("/user/verification.jsp");
				rd.forward(request, response);
				return;
			}

			// Update the user's status to "verified" (1)
			Status verifiedStatus = new Status();
			verifiedStatus.setId(1); // Assuming 1 = Active/Verified

			int rowsAffected = userDB.updateStatus(userId, verifiedStatus);

			if (rowsAffected > 0) {
				// Redirect to the profile page
				User user = userDB.getUserById(userId);
				session.setAttribute("userId", user.getId());
				session.setAttribute("role", user.getRole());
				session.setAttribute("profileImg", user.getImageURL());
				response.sendRedirect(request.getContextPath() + "/profile");
			} else {
				// Set an error message for update failure
				response.sendRedirect(request.getContextPath() + "/error/500");
			}
		} catch (Exception e) {
			// Handle unexpected errors
			e.printStackTrace(); // Log the exception for debugging purposes
			response.sendRedirect(request.getContextPath() + "/error/500");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
