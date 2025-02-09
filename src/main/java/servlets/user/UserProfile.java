/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package servlets.user;

import models.user.User;
import models.user.UserDAO;
import models.address.Address;
import models.address.AddressDAO;
import models.booking.Booking;
import models.booking.BookingDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class UserProfile
 */
@WebServlet("/profile/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 15)
public class UserProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserProfile() {
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

		HttpSession session = request.getSession();
		String pathInfo = request.getPathInfo();

		switch (pathInfo == null ? "/" : pathInfo) {
		case "/":
			System.out.println("Running GET request for /profile");
			handleGetProfile(request, response, session);
			break;
		case "/edit":
			System.out.println("Running GET request for /profile");
			handleGetEditProfile(request, response, session);
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/profile");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String pathInfo = request.getPathInfo();

		switch (pathInfo) {
		case "/edit":
			System.out.println("Running POST request for /profile");
			handlePostEditProfile(request, response, session);
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/profile");
		}
	}

	protected void handleGetProfile(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		try {
			Integer userId = (Integer) session.getAttribute("userId");

			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			UserDAO userDB = new UserDAO();
			AddressDAO addressDB = new AddressDAO();
			BookingDAO bookingDB = new BookingDAO();

			// Get user data
			User user = userDB.getUserById(userId);
			
			// Get User Addresses
			ArrayList<Address> addresses = addressDB.getAddressByUserId(userId, true);

			// Get all bookings ordered by date DESC
			ArrayList<Booking> bookings = bookingDB.getAllBookings(userId);

			// Set attributes for JSP
			request.setAttribute("user", user);
			request.setAttribute("addresses", addresses);
			request.setAttribute("bookings", bookings);

			request.getRequestDispatcher("/user/profile.jsp").forward(request, response);

		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "Please log in to continue.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			System.out.println("Error in handleGetProfile: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/error/500");
		}
	}

	protected void handleGetEditProfile(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		try {
			Integer userId = (Integer) session.getAttribute("userId");

			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			UserDAO userDB = new UserDAO();
			AddressDAO addDB = new AddressDAO();

			User user = userDB.getUserById(userId);
			ArrayList<Address> addresses = addDB.getAddressByUserId(userId, true);

			request.setAttribute("user", user);
			request.setAttribute("addresses", addresses);

			request.getRequestDispatcher("/user/profileEdit.jsp").forward(request, response);
		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "Please log in to continue.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void handlePostEditProfile(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		try {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			UserDAO userDB = new UserDAO();

			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String phoneNo = request.getParameter("phoneNo");
			Part imagePart = request.getPart("profileImage");

			userDB.updateUserProfile(userId, firstName, lastName, imagePart, phoneNo);
			System.out.println("User Profile Updated");

			response.sendRedirect(request.getContextPath() + "/profile");
		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "Please log in to continue.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
