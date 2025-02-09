/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package servlets.user;

import models.booking.Booking;
import models.booking.BookingDAO;
import models.address.Address;
import models.address.AddressDAO;
import models.service.Service;
import models.service.ServiceDAO;
import models.bundle.Bundle;
import models.bundle.BundleDAO;
import models.timeSlot.TimeSlot;
import models.timeSlot.TimeSlotDAO;
import models.transaction.Transaction;
import models.transaction.TransactionDAO;
import models.user.UserDAO;
import models.user.User;
import models.request.Request;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import util.SecretsConfig;
import util.StripeConnection;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Servlet implementation class UserBooking
 */
@WebServlet("/book/*")
public class UserBooking extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserBooking() {
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
			System.out.println("Running GET request for /");
			getTimeSlot(request, response, session);
			break;
		case "/address":
			System.out.println("Running GET request for /address");
			getAddress(request, response, session);
			break;
		case "/services":
			System.out.println("Running GET request for /services");
			getBundleService(request, response, session);
			break;
		case "/review":
			System.out.println("Running GET request for /review");
			getReview(request, response, session);
			break;
		case "/view":
			System.out.println("Running GET request for /view");
			getViewBookings(request, response, session);
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
		String pathInfo = request.getPathInfo();
		HttpSession session = request.getSession();

		switch (pathInfo == null ? "/" : pathInfo) {
		case "/":
			System.out.println("Running POST request for /book");
			postTimeSlot(request, response, session);
			break;
		case "/address":
			System.out.println("Running POST request for /address");
			postAddress(request, response, session);
			break;
		case "/services":
			System.out.println("Running POST request for /services");
			postBundleService(request, response, session);
			break;
		case "/payment":
			System.out.println("Running POST request for /payment");
			postPayment(request, response, session);
			break;
		case "/complete":
			System.out.println("Running POST request for /complete");
			postCompleteBooking(request, response, session);
			break;
		default:
			doGet(request, response);
		}
	}

	// =========================================================================================================
	// Time Slot (GET/POST routes)
	// =========================================================================================================

	// GET Request for loading all available time slots and display on frontend
	private void getTimeSlot(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		try {

			// Initialize the time slot object
			TimeSlotDAO timeSlotDB = new TimeSlotDAO();

			// Get All Week Dates
			ArrayList<LocalDate> weekDates = timeSlotDB.getWeekDates();

			// Get the chosen date from the frontend
			LocalDate chosenDate = LocalDate.now();

			if (request.getParameter("date") != null) {
				chosenDate = LocalDate.parse(request.getParameter("date"));
			}

			// Display All Timeslots associated with the chosen date
			ArrayList<TimeSlot> timeSlots = timeSlotDB.getSlotsByDate(chosenDate);

			request.setAttribute("weekDates", weekDates);
			request.setAttribute("timeSlots", timeSlots);

			// Forward the request to the front end
			request.getRequestDispatcher("/user/bookSlot.jsp").forward(request, response);

		} catch (Exception e) {
			System.out.println("Error handling slots: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/error/500");
		}
	}

	// POST request for submission of chosen time slot
	private void postTimeSlot(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		try {

			// Check session exists for user
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			// Get the selected time slot from the front end
			String selectedDate = request.getParameter("date");
			String selectedSlot = request.getParameter("selected_slot");

			if (selectedSlot != null) {

				LocalDate date = selectedDate.equals("null") ? LocalDate.now() : LocalDate.parse(selectedDate);
				int slotId = Integer.parseInt(selectedSlot);

				// Set session attributes to pass on till the confirmation and insert to DB
				session.setAttribute("selectedDate", date);
				session.setAttribute("selectedSlot", slotId);

				response.sendRedirect(request.getContextPath() + "/book/address");
			}

			// Error Handling if user did not select any slots
			else if (selectedSlot == null) {
				throw new NullPointerException("No slot selected");
			}

		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (NullPointerException e) {
			System.out.println("No slot selected: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/book");
		} catch (Exception e) {
			System.out.println("Error handling time slots: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/error/500");
		}
	}

	// =========================================================================================================
	// Address (GET/POST routes)
	// =========================================================================================================

	// GET Request for loading all addresses of the user and display on frontend
	private void getAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {

			// Check session exists for user
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			// Get the user's address from the database for the GET request
			AddressDAO addressDB = new AddressDAO();

			ArrayList<Address> addresses = addressDB.getAddressByUserId(userId, true);

			// Set attributes to pass to the front end
			request.setAttribute("addresses", addresses);

			// Forward the request to the front end
			request.getRequestDispatcher("/user/bookAddress.jsp").forward(request, response);
		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			System.out.println("Error handling address: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/error/500");
		}
	}

	// POST request for submission of chosen address
	private void postAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {

			// Check session exists for user
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			// Get the address from the user.
			// If there is no selection, it will throw a NumberFormatException which is
			// catched under.
			Integer selectedAddressId = Integer.parseInt(request.getParameter("selected_address"));
			System.out.println("Selected address id: " + selectedAddressId);

			// If the address is external, get the address details from the form
			if (selectedAddressId != null) {

				// ID 0 is external address
				if (selectedAddressId == 0) {
					String street = request.getParameter("street");
					String unit = request.getParameter("unit");
					String postal = request.getParameter("postal");

					Address externalAddress = new Address();
					AddressDAO addressDB = new AddressDAO();
					externalAddress.setAddress(street);
					externalAddress.setUnit(unit);
					externalAddress.setPostalCode(Integer.parseInt(postal));
					
					int selectedExternalAddressId = addressDB.createAddressByUserId(externalAddress, userId, 3, false);

					session.setAttribute("selectedAddress", selectedExternalAddressId);
				}

				// Get the selected address from the database if home or office is selected.
				else {
					session.setAttribute("selectedAddress", selectedAddressId);

					System.out.println("Selected Address: " + selectedAddressId);
				}

				response.sendRedirect(request.getContextPath() + "/book/services");
			}
		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (NumberFormatException e) {
			System.out.println("Invalid address type: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/book/address");
		} catch (Exception e) {
			System.out.println("Error handling address: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/error/500");
		}
	}

	// =========================================================================================================
	// Bundles and Services (GET/POST routes)
	// =========================================================================================================

	// GET Request for loading all available time slots and display on frontend
	private void getBundleService(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		try {

			// Check session exists for user
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			// Get all services and bundles from the database to pass on to the frontend for
			// the get request
			ServiceDAO serviceDB = new ServiceDAO();
			BundleDAO bundleDB = new BundleDAO();
			ArrayList<Service> services = serviceDB.getAllActiveServices();
			ArrayList<Bundle> bundles = bundleDB.getAllActiveBundlesWithServices();

			// Set attributes to pass to the front end
			request.setAttribute("services", services);
			request.setAttribute("bundles", bundles);

			// Forward the request to the front end
			request.getRequestDispatcher("/user/bookService.jsp").forward(request, response);

		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			System.out.println("Error handling slots: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/error/500");
		}
	}

	// POST request for submission of chosen services and bundles
	private void postBundleService(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			String selectedBundle = request.getParameter("selected_bundle");
			String[] selectedServices = request.getParameterValues("selected_services");

			// Check user selects either one of the two services
			if ((selectedBundle == null || selectedBundle.isEmpty())
					&& (selectedServices == null || selectedServices.length == 0)) {
				throw new NullPointerException("Please Select At Least One Service.");
			}

			// Store bundle to session if user selects bundle
			if (selectedBundle != null) {
				int bundleId = Integer.parseInt(selectedBundle);
				session.setAttribute("selectedBundle", bundleId);
			}

			if (selectedServices != null) {
				// Create an int[] of the same size
				int[] serviceIds = new int[selectedServices.length];

				// Convert each string to an integer
				for (int i = 0; i < selectedServices.length; i++) {
					serviceIds[i] = Integer.parseInt(selectedServices[i]);
				}

				// If user selects both service and bundle
				if (selectedBundle != null) {

					int bundleId = Integer.parseInt(selectedBundle);

					// Initialise BundleDAO to get all services inside the bundle
					BundleDAO bundleDB = new BundleDAO();

					// Call checkBundleService to get service IDs from the database
					ArrayList<Integer> bundleServiceIds = bundleDB.checkBundleService(bundleId);

					// Call isServiceIdInSelection to check if there's a match
					boolean matchFound = isServiceIdInSelection(bundleServiceIds, serviceIds);

					// Check if service selection is already inside the bundle.
					if (matchFound) {
						System.out.println("Selected Services Belong to the Selected Bundle");
						session.removeAttribute("selectedBundle");
						response.sendRedirect(request.getContextPath() + "/book/services");
						return;
					}
				}
				session.setAttribute("selectedServices", serviceIds);
			}

			response.sendRedirect(request.getContextPath() + "/book/review");

		} catch (NullPointerException e) {
			System.out.println("Error handling services: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/book/services");
		} catch (NumberFormatException e) {
			System.out.println("Error converting string to integer: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/error/500");
		} catch (Exception e) {
			System.out.println("Error handling services: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/error/500");
		}
	}

	// Helper Function to check if services belong to the chosen bundle
	public boolean isServiceIdInSelection(ArrayList<Integer> serviceIds, int[] selectedServices) {
		// Iterate through each service ID from the database
		for (int serviceId : serviceIds) {
			// Check if it matches any ID in the selectedServices array
			for (int selectedId : selectedServices) {
				if (serviceId == selectedId) {
					return true; // Match found
				}
			}
		}
		return false; // No match found
	}

	// =========================================================================================================
	// Add to Cart (GET/POST)
	// =========================================================================================================

	// GET Request for a preview of selected services and booking details

	private void getReview(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			// Check session exists for user
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			// Get all session attributes
			LocalDate selectedDate = (LocalDate) session.getAttribute("selectedDate");
			Integer selectedSlotId = (Integer) session.getAttribute("selectedSlot");
			Integer selectedAddressId = (Integer) session.getAttribute("selectedAddress");
			Integer selectedBundleId = (Integer) session.getAttribute("selectedBundle");
			int[] selectedServiceIds = (int[]) session.getAttribute("selectedServices");

			System.out.println("Selected Date: " + selectedDate);
			System.out.println("Selected Slot ID: " + selectedSlotId);
			System.out.println("Selected Address ID: " + selectedAddressId);
			System.out.println("Selected Bundle ID: " + selectedBundleId);

			// Validate required booking information
			if (selectedDate == null || selectedSlotId == null || selectedAddressId == null) {
				throw new IllegalStateException("Required booking information missing");
			}

			// Initialize DAOs
			TimeSlotDAO timeSlotDB = new TimeSlotDAO();
			AddressDAO addressDB = new AddressDAO();
			BundleDAO bundleDB = new BundleDAO();
			ServiceDAO serviceDB = new ServiceDAO();

			// Get selected address and time slot
			Address selectedAddress = addressDB.getAddressById(selectedAddressId);
			TimeSlot selectedSlot = timeSlotDB.getTimeSlotById(selectedSlotId);

			// Get services information
			ArrayList<Service> selectedServices = new ArrayList<>();

			// Handle bundle selection if exists
			if (selectedBundleId != null) {
				Bundle selectedBundle = bundleDB.getBundleById(selectedBundleId);
				if (selectedBundle != null) {
					request.setAttribute("selectedBundle", selectedBundle);
				}
			}

			// Handle individual services if selected
			if (selectedServiceIds != null && selectedServiceIds.length > 0) {
				for (int serviceId : selectedServiceIds) {
					Service service = serviceDB.getServiceById(serviceId);
					System.out.println("Service Name for Review: " + service.getServiceDescription());
					if (service != null) {
						selectedServices.add(service);
					}
				}
				request.setAttribute("selectedServices", selectedServices);
			}

			// Set all attributes for the review page
			request.setAttribute("selectedDate", selectedDate);
			request.setAttribute("selectedSlot", selectedSlot.getStartTime());
			request.setAttribute("selectedAddress", selectedAddress);

			// Forward to review page
			request.getRequestDispatcher("/user/bookReview.jsp").forward(request, response);

		} catch (IllegalStateException e) {
			System.out.println("Session error: " + e.getMessage());
			request.setAttribute("error", "Please complete all booking steps first.");
			response.sendRedirect(request.getContextPath() + "/book");
		} catch (Exception e) {
			System.out.println("Error processing review: " + e.getMessage());
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/error/500");
		}
	}

	private void postPayment(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			// Check if user is logged in
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			// Get user info and check customer_id
			UserDAO userDB = new UserDAO();
			User user = userDB.getUserById(userId);

			System.out.println("Customer ID: " + user.getCustomerId());

			if (user.getCustomerId() == null || user.getCustomerId().isEmpty()) {
				// If no customer_id, redirect to credit card form page
				response.sendRedirect(request.getContextPath() + "/payment/setup");
			}

			else {
				// Get all session attributes
				LocalDate selectedDate = (LocalDate) session.getAttribute("selectedDate");
				Integer selectedSlotId = (Integer) session.getAttribute("selectedSlot");
				Integer selectedAddressId = (Integer) session.getAttribute("selectedAddress");
				Integer selectedBundleId = (Integer) session.getAttribute("selectedBundle");
				int[] selectedServiceIds = (int[]) session.getAttribute("selectedServices");

				// Validate required booking information
				if (selectedDate == null || selectedSlotId == null || selectedAddressId == null) {
					throw new IllegalStateException("Required booking information missing");
				}

				// Initialize DAOs
				ServiceDAO serviceDB = new ServiceDAO();
				BundleDAO bundleDB = new BundleDAO();

				// Get selected bundle and services
				Bundle selectedBundle = null;
				ArrayList<Service> selectedServices = new ArrayList<Service>();

				// Handle bundle selection if exists
				if (selectedBundleId != null) {
					selectedBundle = bundleDB.getBundleById(selectedBundleId);
				}

				// Handle individual services if selected
				if (selectedServiceIds != null && selectedServiceIds.length > 0) {
					for (int serviceId : selectedServiceIds) {
						Service service = serviceDB.getServiceById(serviceId);
						if (service != null) {
							selectedServices.add(service);
						}
					}
				}

				// Pass the values to create the booking
				BookingDAO bookingDB = new BookingDAO();
				bookingDB.createBooking(userId, selectedAddressId, selectedSlotId, selectedDate, selectedServices,
						selectedBundle);

				response.sendRedirect(request.getContextPath() + "/profile");
			}

		} catch (IllegalStateException e) {
			System.out.println("Session error: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			System.out.println("Error processing review: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/error/500");
		}
	}

	// =========================================================================================================
	// Booking History (GET routes)
	// =========================================================================================================

	protected void getViewBookings(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		try {
			Integer userId = (Integer) session.getAttribute("userId");

			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			BookingDAO bookingDB = new BookingDAO();

			// Get all bookings ordered by date DESC
			ArrayList<Booking> bookings = bookingDB.getAllBookings(userId);

			// Set attributes for JSP
			request.setAttribute("bookings", bookings);

			request.getRequestDispatcher("/user/bookingHistory.jsp").forward(request, response);

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

	// =========================================================================================================
	// Mark Booking as Complete and receive the paynow QR code (POST routes)
	// =========================================================================================================
	private void postCompleteBooking(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			int bookingId = Integer.parseInt(request.getParameter("bookingId"));
			BookingDAO bookingDB = new BookingDAO();
			UserDAO userDB = new UserDAO();

			// Get booking and user details
			Booking booking = bookingDB.getBookingById(bookingId);
			User user = userDB.getUserById(userId);

			if (booking == null) {
				session.setAttribute("errorMsg", "Booking not found.");
				response.sendRedirect(request.getContextPath() + "/book/view");
				return;
			}

			boolean success = bookingDB.completeBooking(bookingId, userId);

			if (success) {
				float totalAmount = booking.getPayment().getAmount();
				StripeConnection stripe = new StripeConnection();
				String bookingReference = "BOOKING-" + bookingId;
				String qrCodeUrl = stripe.createPayNowQR(totalAmount, bookingReference);

				if (qrCodeUrl != null) {
					// Send email with QR code
					try (Client client = ClientBuilder.newClient()) {
						String emailServiceURL = "http://localhost:8081/b2b/api/v1/email/invoice";
						WebTarget target = client.target(emailServiceURL);

						Request requestService = new Request();
						requestService.setUser(user);
						requestService.setBooking(booking);
						requestService.setQrCodeUrl(qrCodeUrl);

						Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
						Response res = invocationBuilder
								.post(Entity.entity(requestService, MediaType.APPLICATION_JSON));

						if (res.getStatus() != Response.Status.OK.getStatusCode()) {
							System.out.println("Failed to send payment email");
						}
					} catch (Exception e) {
						System.out.println("Error sending payment email: " + e.getMessage());
						e.printStackTrace();
					}
				} else {
					session.setAttribute("errorMsg", "Error generating PayNow QR code.");
				}
			}

			response.sendRedirect(request.getContextPath() + "/book/view");

		} catch (Exception e) {
			session.setAttribute("errorMsg", "An error occurred: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/error/500");
		}
	}
}
