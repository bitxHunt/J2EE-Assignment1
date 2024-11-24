package servlets.user;

import models.user.User;
import models.user.UserDAO;
import models.address.Address;
import models.address.AddressDAO;
import models.transaction.Transaction;
import models.transaction.TransactionDAO;

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
			AddressDAO addDB = new AddressDAO();
			TransactionDAO transDB = new TransactionDAO();
			Double totalAmount = 0.0;
			Double originalTotalAmount = 0.0;
			Double savings = 0.0;

			User user = userDB.getUserById(userId);
			Address homeAddress = addDB.getAddressByUserId(userId, 1);
			Address officeAddress = addDB.getAddressByUserId(userId, 2);
			ArrayList<Transaction> transactions = transDB.getActiveBookingsByUserID(userId);

			for (Transaction transaction : transactions) {
				double subTotal = transaction.getSubTotal();
				double discount = transaction.getDiscount();

				totalAmount += subTotal;
				System.out.println(totalAmount);

				// Check for division by zero
				if (discount < 100) {
					originalTotalAmount += subTotal * (100 / (100 - discount));
					System.out.println(originalTotalAmount);
				}
			}

			savings = originalTotalAmount - totalAmount;
			String formatTotal = String.format("%.2f%n", totalAmount);
			String formatSavings = String.format("%.2f%n", savings);

			System.out.println("Total Amount: " + totalAmount);
			System.out.println("Savings: " + savings);

			request.setAttribute("user", user);
			request.setAttribute("homeAddress", homeAddress);
			request.setAttribute("officeAddress", officeAddress);
			request.setAttribute("transactions", transactions);
			request.setAttribute("totalAmount", formatTotal);
			request.setAttribute("totalSaving", formatSavings);

			request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "Please log in to continue.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			e.printStackTrace();
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
			Address homeAddress = addDB.getAddressByUserId(userId, 1);
			Address officeAddress = addDB.getAddressByUserId(userId, 2);

			request.setAttribute("user", user);
			request.setAttribute("homeAddress", homeAddress);
			request.setAttribute("officeAddress", officeAddress);

			System.out.println("Office Address: " + officeAddress.getId());

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
			AddressDAO addressDB = new AddressDAO();

			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String phoneNo = request.getParameter("phoneNo");
			Part imagePart = request.getPart("profileImage");

			String strHomeAddId = request.getParameter("homeAddressId");
			String strOfficeAddId = request.getParameter("officeAddressId");

			userDB.updateUserProfile(userId, firstName, lastName, imagePart, phoneNo);
			System.out.println("User Profile Updated");
			System.out.println("Home Address ID: " + strHomeAddId);
			System.out.println("Office Address ID: " + strOfficeAddId);

			String homeAddress = request.getParameter("homeAddress");
			String homePostal = request.getParameter("homePostal");
			String homeUnit = request.getParameter("homeUnit");
			Integer homeAddId = Integer.parseInt(strHomeAddId);

			String officeAddress = request.getParameter("officeAddress");
			String officePostal = request.getParameter("officePostal");
			String officeUnit = request.getParameter("officeUnit");
			Integer officeAddId = Integer.parseInt(strOfficeAddId);

			addressDB.updateAddressById(userId, homeAddId, homeAddress, Integer.parseInt(homePostal), homeUnit);
			addressDB.updateAddressById(userId, officeAddId, officeAddress, Integer.parseInt(officePostal), officeUnit);

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
