/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package servlets.user;

import models.address.Address;
import models.address.AddressDAO;
import models.address.AddressType;
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
@WebServlet("/address/*")
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
		doPost(request, response);
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
		case "/add":
			System.out.println("Running POST request for /add");
			addNewAddress(request, response, session);
		case "/edit":
			System.out.println("Running POST request for /edit");
			editAddress(request, response, session);
			break;
		case "/delete":
			System.out.println("Running POST request for /delete");
			deleteAddress(request, response, session);
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/address");
		}
	}

	public void addNewAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		try {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			String address = request.getParameter("address");
			String unit = request.getParameter("unit");
			Integer postalCode = Integer.parseInt(request.getParameter("postalCode"));

			String addType = request.getParameter("addressType");

			// Get the address type id value from the frontend and change accordingly.
			Integer addTypeId = addType.equals("HOME") ? 1 : 2;

			// Validate input fields
			if (address == null || address.trim().isEmpty() || unit == null || unit.trim().isEmpty()
					|| postalCode == null || postalCode <= 0) {

				request.getSession().setAttribute("errMsg", "Please Enter Values For All The Required Fields.");
				response.sendRedirect(request.getContextPath() + "/profile/edit");
				return;
			}

			Address newAddress = new Address();
			newAddress.setAddress(address);
			newAddress.setUnit(unit);
			newAddress.setPostalCode(postalCode);

			AddressDAO addressDB = new AddressDAO();
			addressDB.createAddressByUserId(newAddress, userId, addTypeId);

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

	// Soft Edit Address
	public void editAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			// Get data from the frontend
			Integer addId = Integer.parseInt(request.getParameter("addressId"));
			String address = request.getParameter("address");
			String unit = request.getParameter("unit");
			Integer postal = Integer.parseInt(request.getParameter("postalCode"));
			String addType = request.getParameter("addressType");

			// Get the address type id value from the frontend and change accordingly.
			Integer addTypeId = addType.equals("HOME") ? 1 : 2;

			AddressDAO addressDB = new AddressDAO();

			// First update the status of the address to false
			// This is to maintain data integrity with the booking history
			addressDB.updateAddressStatus(userId, addId, false);

			// Create an address object to pass to address creation
			AddressType selectedAddType = new AddressType();
			Address newAddress = new Address();
			
			newAddress.setAddress(address);
			newAddress.setUnit(unit);
			newAddress.setPostalCode(postal);
			selectedAddType.setId(addTypeId);
			newAddress.setAddType(selectedAddType);
			
			// Create record for the updated address
			addressDB.createAddressByUserId(newAddress, userId, addTypeId);

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

	// Soft Delete Address
	public void deleteAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			Integer addId = Integer.parseInt(request.getParameter("addressId"));
			System.out.println("Address ID: " + addId);

			AddressDAO addressDB = new AddressDAO();
			addressDB.updateAddressStatus(userId, addId, false);

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
