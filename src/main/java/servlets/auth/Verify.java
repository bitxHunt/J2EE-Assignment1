/***********************************************************
* Name: Thiha Swan Htet, Harry
* Class: DIT/FT/2B/01
* Admin No: P2336671
************************************************************/

package servlets.auth;

import middlewares.JWTMiddleware;
import models.user.*;
import models.status.*;
import models.request.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
		RequestDAO requestDB = new RequestDAO();
		HttpSession session = request.getSession();

		try {
			// Retrieve the JWT token from the URL query parameter
			String token = request.getParameter("token");
			Request requestService = requestDB.getStatusByToken(token);

			if (token == null || token.isEmpty() || requestService.getStatus() == false) {
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
				// Set the token back to false
				requestDB.setRequestStatus(false, token);

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
