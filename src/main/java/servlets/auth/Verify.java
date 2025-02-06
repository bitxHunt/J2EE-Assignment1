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
				// Retrieve the updated user details
				User user = userDB.getUserById(userId);

				if (user == null) {
					request.setAttribute("errorMessage", "User not found.");
					RequestDispatcher rd = request.getRequestDispatcher("/error/404ErrorPage.jsp");
					rd.forward(request, response);
					return;
				}

				// Store user details in session
				session.setAttribute("userId", userId);
				session.setAttribute("role", user.getRole());

				// Forward the request to the profile page with user details
				request.setAttribute("user", user);
				RequestDispatcher rd = request.getRequestDispatcher("/user/profile.jsp");
				rd.forward(request, response);
			} else {
				// Set an error message for update failure
				RequestDispatcher rd = request.getRequestDispatcher("/error/500ErrorPage.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			// Handle unexpected errors
			e.printStackTrace(); // Log the exception for debugging purposes
			RequestDispatcher rd = request.getRequestDispatcher("/error/500ErrorPage.jsp");
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
		doGet(request, response);
	}

}
