/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package servlets.auth;

import models.user.*;
import models.address.Address;
import models.address.AddressType;
import models.request.*;
import middlewares.JWTMiddleware;
import util.org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phoneNo = request.getParameter("phoneNo");

		String street = request.getParameter("street");
		String unit = request.getParameter("unit");
		String postalCode = request.getParameter("postalCode");
		String imageUrl = "https://res.cloudinary.com/dnaulhgz8/image/upload/v1732446530/bizbynfxadhthnoymbdo.webp";

		int addTypeId = Integer.parseInt(request.getParameter("addressTypeId"));

		// Validate input fields
		if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()
				|| email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()
				|| phoneNo == null || phoneNo.trim().isEmpty() || street == null || street.trim().isEmpty()
				|| unit == null || unit.trim().isEmpty() || postalCode == null || postalCode.trim().isEmpty()) {

			request.getSession().setAttribute("errMsg", "Please Enter Values For All The Required Fields.");
			response.sendRedirect(request.getContextPath() + "/register");
			return;
		}

		// Hash password
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));

		// Initialise the objects of user, jwtmiddleware and the request service
		User user = new User();
		Address address = new Address();
		Request requestService = new Request();
		AddressType addType = new AddressType();
		
		UserDAO userDB = new UserDAO();
		JWTMiddleware jwt = new JWTMiddleware();
		

		try {
			
			// Set User Object
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setPassword(hashedPassword);
			user.setPhoneNo(phoneNo);
			user.setImageURL(imageUrl);
			
			// Set Address Object
			address.setAddress(street);
			address.setUnit(unit);
			address.setPostalCode(Integer.parseInt(postalCode));
			addType.setId(addTypeId);
			address.setAddType(addType);
			
			User createdUser = userDB.registerUser(user, address);

			// Log out the user id
			System.out.println("Created User Id: " + user.getId());

			if (user.getId() > 0) {
				String verifyToken = jwt.createVerifyToken(user.getId());

				try (Client client = ClientBuilder.newClient()) {
					String emailServiceURL = "http://localhost:8081/b2b/api/v1/email/verify";
					WebTarget target = client.target(emailServiceURL);

					requestService.setUser(createdUser);
					requestService.setToken(verifyToken);

					Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
					Response res = invocationBuilder
							.post(Entity.entity(requestService, MediaType.APPLICATION_JSON));

					if (res.getStatus() == Response.Status.OK.getStatusCode()) {
						response.sendRedirect(request.getContextPath() + "/login");
					} else {
						request.getSession().setAttribute("errMsg", "Failed to send verification email");
						response.sendRedirect(request.getContextPath() + "/register");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					request.getSession().setAttribute("errMsg", "Failed to send verification email: " + e.getMessage());
					response.sendRedirect(request.getContextPath() + "/register");
				}
				
			} else {
				request.getSession().setAttribute("errMsg", "Registration failed. Please try again.");
				response.sendRedirect(request.getContextPath() + "/register");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("errMsg", "An unexpected error occurred: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/register");
		}
	}

}
