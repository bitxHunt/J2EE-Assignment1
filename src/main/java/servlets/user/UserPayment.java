package servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.user.User;
import models.user.UserDAO;

import java.io.IOException;

import util.SecretsConfig;
import util.StripeConnection;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

/**
 * Servlet implementation class UserPayment
 */
@WebServlet("/payment/*")
public class UserPayment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserPayment() {
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

		switch (pathInfo == null ? "/setup" : pathInfo) {
		case "/setup":
			System.out.println("Running GET request for /setup");
			getPaymentSetup(request, response, session);
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/book");
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

		switch (pathInfo == null ? "/setup" : pathInfo) {
		case "/setup":
			System.out.println("Running POST request for /setup");
			postPaymentComplete(request, response, session);
			break;
//		case "/checkout":
//			System.out.println("Running POST request for /payment/checkout");
//			handleCheckout(request, response, session);
//			break;
		case "/delete":
			System.out.println("Running POST request for /address/delete");
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/address");
		}
	}

	private void getPaymentSetup(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			// Get user email for Stripe
			UserDAO userDB = new UserDAO();
			User user = userDB.getUserById(userId);

			// Create a SetupIntent
			StripeConnection stripe = new StripeConnection();
			String clientSecret = stripe.createSetupIntent();
			
			System.out.println("Client Secret: " + clientSecret);

			// Pass necessary data to the JSP
			request.setAttribute("clientSecret", clientSecret);
			request.setAttribute("userEmail", user.getEmail());

			request.getRequestDispatcher("/user/setupPayment.jsp").forward(request, response);

		} catch (Exception e) {
			System.out.println("Error setting up payment: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/error/500");
		}
	}

	private void postPaymentComplete(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			String paymentMethodId = request.getParameter("paymentMethodId");
			System.out.println("Received payment method ID: " + paymentMethodId);

			// Create customer in Stripe and attach payment method
			UserDAO userDB = new UserDAO();
			User user = userDB.getUserById(userId);

			StripeConnection stripe = new StripeConnection();
			String customerId = stripe.createCustomerWithPaymentMethod(user.getEmail(), paymentMethodId);

			// Update user with customer_id
			userDB.updateCustomerId(userId, customerId);

			response.setStatus(HttpServletResponse.SC_OK);

		} catch (Exception e) {
			System.out.println("Error completing payment setup: " + e.getMessage());
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

//	public void handleCheckout(HttpServletRequest request, HttpServletResponse response, HttpSession session)
//			throws IOException {
//		try {
//			Stripe.apiKey = SecretsConfig.getStripeApiKey();
//
//			SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
//					.setSuccessUrl(request.getContextPath() + "/success.html")
//					.setCancelUrl(request.getContextPath() + "/cancel.html").addLineItem(SessionCreateParams.LineItem
//							.builder().setQuantity(1L).setPrice("price_your_actual_price_id").build())
//					.build();
//
//			Session stripeSession = Session.create(params);
//			response.sendRedirect(stripeSession.getUrl());
//
//		} catch (StripeException e) {
//			// Handle the error appropriately
//			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Payment setup failed");
//		}
//	}
}
