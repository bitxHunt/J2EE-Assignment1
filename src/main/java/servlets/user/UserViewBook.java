package servlets.user;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.transaction.TransactionDAO;
import models.transaction.Transaction;
import java.util.ArrayList;

import java.io.IOException;

/**
 * Servlet implementation class UserViewBook
 */
@WebServlet("/booking")
public class UserViewBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserViewBook() {
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

		try {
			// Check if user is logged in
			// Check session exists for user
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			TransactionDAO bookingDAO = new TransactionDAO();

			ArrayList<Transaction> transactions = bookingDAO.getAllBookingsByUserID(userId);
			request.setAttribute("transactions", transactions);

			RequestDispatcher rd = request.getRequestDispatcher("/user/viewBooking.jsp");
			rd.forward(request, response);

		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "Please log in to continue.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/error");
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
