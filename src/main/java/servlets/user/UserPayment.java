package servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import util.StripeConfig;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

/**
 * Servlet implementation class UserPayment
 */
@WebServlet("/payment")
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
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

		switch (pathInfo == null ? "/edit" : pathInfo) {
		case "/checkout":
			System.out.println("Running POST request for /payment/checkout");
			handleCheckout(request, response, session);
			break;
		case "/delete":
			System.out.println("Running POST request for /address/delete");

			break;
		default:
			response.sendRedirect(request.getContextPath() + "/address");
		}
	}

	public void handleCheckout(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		try {
			Stripe.apiKey = StripeConfig.getStripeApiKey();

			SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
					.setSuccessUrl(request.getContextPath() + "/success.html")
					.setCancelUrl(request.getContextPath() + "/cancel.html").addLineItem(SessionCreateParams.LineItem
							.builder().setQuantity(1L).setPrice("price_your_actual_price_id").build())
					.build();

			Session stripeSession = Session.create(params);
			response.sendRedirect(stripeSession.getUrl());

		} catch (StripeException e) {
			// Handle the error appropriately
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Payment setup failed");
		}
	}
}
