package servlets.auth;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import middlewares.JWTMiddleware;
import models.user.*;
import models.request.*;

import java.io.IOException;

/**
 * Servlet implementation class ResendEmail
 */
@WebServlet("/resend")
public class ResendEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResendEmail() {
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
		RequestDispatcher rd = request.getRequestDispatcher("/user/verification.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");

		try {
			UserDAO userDB = new UserDAO();
			JWTMiddleware jwt = new JWTMiddleware();
			Request requestService = new Request();

			// Get user by email
			User user = userDB.getUserByEmail(email);

			if (user == null) {
				request.setAttribute("errorMessage", "If email exists, a link will be sent.");
				RequestDispatcher rd = request.getRequestDispatcher("/user/verification.jsp");
				rd.forward(request, response);
				return;
			}

			// Invalidate the previous tokens to prevent broken access control
			RequestDAO requestDB = new RequestDAO();
			int emailServiceId = 1; // Verfication service has an id of 1.
			requestDB.invalidateToken(false, emailServiceId, user.getId());

			// Create new verification token
			String verifyToken = jwt.createVerifyToken(user.getId());

			// Add a request record into the table
			requestDB.addRequest(verifyToken, user.getId(), emailServiceId);

			// Call Spring Boot API to send email
			try (Client client = ClientBuilder.newClient()) {
				String emailServiceURL = "http://localhost:8081/b2b/api/v1/email/verify";
				WebTarget target = client.target(emailServiceURL);

				requestService.setUser(user);
				requestService.setToken(verifyToken);

				Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
				Response res = invocationBuilder.post(Entity.entity(requestService, MediaType.APPLICATION_JSON));

				if (res.getStatus() == Response.Status.OK.getStatusCode()) {
					request.setAttribute("successMessage", "If email exists, a link will be sent.");
				} else {
					request.setAttribute("errorMessage", "If email exists, a link will be sent.");
				}
			} catch (Exception e) {
				request.setAttribute("errorMessage", "Failed to send verification email: " + e.getMessage());
			}

			RequestDispatcher rd = request.getRequestDispatcher("/user/verification.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/user/verification.jsp");
			rd.forward(request, response);
		}
	}

}
