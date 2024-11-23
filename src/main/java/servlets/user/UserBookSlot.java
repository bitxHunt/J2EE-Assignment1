package servlets.user;

import models.booking.Booking;
import models.booking.BookingDAO;
import models.address.Address;
import models.address.AddressDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class UserBooking
 */
@WebServlet("/book/*")
public class UserBookSlot extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserBookSlot() {
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
		System.out.println(pathInfo);

		switch (pathInfo == null ? "/slots" : pathInfo) {
		case "/slots":
			handleSlots(request, response, session);
			break;
		case "/address":
			handleAddress(request, response, session);
			break;
		case "/confirmation":
			handleConfirmation(request, response, session);
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/book/slots");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost called");
		String pathInfo = request.getPathInfo();

		HttpSession session = request.getSession();

		switch (pathInfo == null ? "/slots" : pathInfo) {  
	    case "/address":
	        handleAddress(request, response, session);
	        break;
	    case "/confirmation":
	        handleConfirmation(request, response, session);
	        break;
	    default:
	        doGet(request, response);
	}
	}

	private void handleSlots(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			BookingDAO bookingDB = new BookingDAO();
			List<Booking> availableSlots = bookingDB.getAvailableSlots();
			request.setAttribute("timeslots", availableSlots);

			String selectedSlot = (String) session.getAttribute("selectedSlot");
			if (selectedSlot != null) {
				request.setAttribute("selectedSlot", selectedSlot);
			}

			request.getRequestDispatcher("/user/bookSlot.jsp").forward(request, response);
		} catch (Exception e) {
			System.out.println("Error handling slots: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "Unable to load available slots. Please try again later.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	private void handleAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			String selectedSlot = request.getParameter("selected_slot");
			if (selectedSlot != null) {
				session.setAttribute("selectedSlot", selectedSlot);
			}

			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			AddressDAO addDB = new AddressDAO();
			Address homeAddress = addDB.getAddressByUserId(userId, 1);
			Address officeAddress = addDB.getAddressByUserId(userId, 2);

			request.setAttribute("homeAddress", homeAddress);
			request.setAttribute("officeAddress", officeAddress);
			request.getRequestDispatcher("/user/bookAddress.jsp").forward(request, response);
		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "Please log in to continue booking.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			System.out.println("Error handling address: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "Unable to load address information. Please try again.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	private void handleConfirmation(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			String selectedSlot = (String) session.getAttribute("selectedSlot");
			Address selectedAddress = (Address) session.getAttribute("selectedAddress");

			if (selectedSlot == null || selectedAddress == null) {
				throw new IllegalStateException("Missing booking information");
			}

			request.setAttribute("selectedSlot", selectedSlot);
			request.setAttribute("selectedAddress", selectedAddress);
			request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
		} catch (IllegalStateException e) {
			System.out.println("Incomplete booking data: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "Please complete all booking steps.");
			response.sendRedirect(request.getContextPath() + "/booking/slots");
		} catch (Exception e) {
			System.out.println("Error handling confirmation: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "Unable to process booking confirmation. Please try again.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
