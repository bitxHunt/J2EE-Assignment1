package servlets.user;

import models.address.Address;
import models.address.AddressDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class UserAddress
 */
@WebServlet("/address")
public class UserAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserAddress() {
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
			System.out.println("Running GET request for /address");
			handleGetAddress(request, response, session);
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/address");
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

		switch (pathInfo == null ? "/edit" : pathInfo) {
		case "/edit":
			System.out.println("Running POST request for /address/edit");
			handleEditAddress(request, response, session);
			break;
		case "/delete":
			System.out.println("Running POST request for /address/delete");
			handleDeleteAddress(request, response, session);
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/address");
		}
	}

	private void handleGetAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			String strAddId = request.getParameter("address_type");
			Integer addressId = Integer.parseInt(strAddId);

			AddressDAO addressDB = new AddressDAO();
			Address address = addressDB.getAddressByUserId(userId, addressId);

			request.setAttribute("address", address);
			RequestDispatcher rd = request.getRequestDispatcher("/user/booking.jsp");
			rd.forward(request, response);

		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "Please log in to continue booking.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void handleEditAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			String strAddId = request.getParameter("addressId");
			Integer addressId = Integer.parseInt(strAddId);
			String address = request.getParameter("address");
			String postalCode = request.getParameter("postal_code");
			String unit = request.getParameter("unit");

			AddressDAO addressDB = new AddressDAO();
			addressDB.updateAddressById(userId, addressId, address, Integer.parseInt(postalCode), unit);

			response.sendRedirect(request.getContextPath() + "/profile");
		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "Please log in to continue booking.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleDeleteAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			String strAddId = request.getParameter("addressId");
			Integer addressId = Integer.parseInt(strAddId);

			AddressDAO addressDB = new AddressDAO();
			addressDB.deleteAddressById(userId, addressId);

			response.sendRedirect(request.getContextPath() + "/profile");
		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "Please log in to continue booking.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
