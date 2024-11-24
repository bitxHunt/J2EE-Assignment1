package servlets.user;

import models.user.User;
import models.user.UserDAO;
import models.address.Address;
import models.address.AddressDAO;
import models.transaction.Transaction;
import models.transaction.TransactionDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class UserProfile
 */
@WebServlet("/profile")
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
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("userId") != null && session.getAttribute("role") != null) {
			Integer userId = (Integer) session.getAttribute("userId");

			UserDAO userDB = new UserDAO();
			AddressDAO addDB = new AddressDAO();
			TransactionDAO transDB = new TransactionDAO();
			Double totalAmount = 0.0;
			Double originalTotalAmount = 0.0;
			Double savings = 0.0;
			try {
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
				RequestDispatcher rd = request.getRequestDispatcher("/user/profile.jsp");
				rd.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/user/login.jsp");
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
