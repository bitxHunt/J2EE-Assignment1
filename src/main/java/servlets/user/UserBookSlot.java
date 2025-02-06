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
import java.util.List;
import java.util.ArrayList;

import util.SecretsConfig;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

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

		switch (pathInfo == null ? "/slots" : pathInfo) {
		case "/slots":
			System.out.println("Running GET request for /slots");
			handleSlots(request, response, session);
			break;
		case "/address":
			System.out.println("Running GET request for /address");
			handleAddress(request, response, session);
			break;
		case "/services":
			System.out.println("Running GET request for /services");
			handleServices(request, response, session);
			break;
		case "/review":
			System.out.println("Running POST request for /review");
			handleReview(request, response, session);
			break;
		case "/view":
			System.out.println("Running GET request for /view");
			handleView(request, response, session);
			break;
		case "/payment":
			System.out.println("Running GET request for /payment");
			handleGetPayment(request, response, session);
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
		String pathInfo = request.getPathInfo();
		HttpSession session = request.getSession();

		switch (pathInfo == null ? "/slots" : pathInfo) {
		case "/address":
			System.out.println("Running POST request for /address");
			handleAddress(request, response, session);
			break;
		case "/services":
			System.out.println("Running POST request for /services");
			handleServices(request, response, session);
			break;
		case "/review":
			System.out.println("Running POST request for /review");
			handleReview(request, response, session);
			break;
		case "/confirm":
			System.out.println("Running POST request for /confirm");
			handleBooking(request, response, session);
			break;
		case "/cancel":
			System.out.println("Running POST request for /cancel");
			handleCancel(request, response, session);
			break;
		case "/payment":
			System.out.println("Running GET request for /payment");
			handlePostPayment(request, response, session);
			break;
		default:
			doGet(request, response);
		}
	}

	private void handleSlots(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {

//			// Get all available slots from the database
//			BookingDAO bookingDB = new BookingDAO();
//			List<Booking> availableSlots = bookingDB.getAvailableSlots();
//			// Set attribute to pass to the front end
//			request.setAttribute("timeslots", availableSlots);
//
//			// Forward the request to the front end
//			request.getRequestDispatcher("/user/bookSlot.jsp").forward(request, response);
			
			// Initialize the time slot object
			TimeSlotDAO timeSlotDB = new TimeSlotDAO();
			
			// Get All Week Dates
			ArrayList<LocalDate> weekDates = timeSlotDB.getWeekDates();
			
			// Get the chosen date from the frontend
			LocalDate chosenDate = LocalDate.now();
			
			if(request.getParameter("chosen_date") != null) {
				chosenDate = LocalDate.parse(request.getParameter("chosen_date"));
			}

			// Display All Timeslots associated with the chosen date
			ArrayList<TimeSlot> timeSlots = timeSlotDB.getSlotsByDate(chosenDate);
			
			request.setAttribute("weekDates", weekDates);
			request.setAttribute("timeSlots", timeSlots);
			
			// Forward the request to the front end
			request.getRequestDispatcher("/user/bookSlot.jsp").forward(request, response);
			
		} catch (Exception e) {
			System.out.println("Error handling slots: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("err", "Unable to load available slots. Please try again later.");
			request.getRequestDispatcher("/error/500").forward(request, response);
		}
	}

	private void handleAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {

			// Check session exists for user
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			// Get the selected time slot from the front end
			String selectedSlot = request.getParameter("selected_slot");
			if (selectedSlot != null) {

				// Split the string since it is in the format "yyyy-MM-dd_HH:mm"
				String[] dateTime = selectedSlot.split("_");
				String date = dateTime[0];
				String time = dateTime[1];

				LocalDate selectedDate = LocalDate.parse(date);
				LocalTime selectedTime = LocalTime.parse(time);

				// Set session attributes to pass on till the confirmation and insert to DB
				session.setAttribute("selectedDate", selectedDate);
				session.setAttribute("selectedTime", selectedTime);
			}

			else if (selectedSlot == null) {
				throw new NullPointerException("No slot selected");
			}

			// Get the user's address from the database for the GET request
			AddressDAO addDB = new AddressDAO();

			Address homeAddress = addDB.getAddressByUserId(userId, 1);
			Address officeAddress = addDB.getAddressByUserId(userId, 2);

			// Set attributes to pass to the front end
			request.setAttribute("homeAddress", homeAddress);
			request.setAttribute("officeAddress", officeAddress);

			// Forward the request to the front end
			request.getRequestDispatcher("/user/bookAddress.jsp").forward(request, response);
		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("err", "Please log in to continue booking.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (NullPointerException e) {
			System.out.println("No slot selected: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("err", "Please select a time slot to continue booking.");
			request.getRequestDispatcher("/404ErrorPage.jsp").forward(request, response);
		} catch (Exception e) {
			System.out.println("Error handling address: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("err", "Unable to load address information. Please try again.");
			request.getRequestDispatcher("/error/500").forward(request, response);
		}
	}

	private void handleServices(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {

			// Check session exists for user
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			Address selectedAddress = new Address();

			// Get the address from the user.
			// If there is no selection, it will throw a NumberFormatException which is
			// catched under.
			Integer selectedAddressId = Integer.parseInt(request.getParameter("address_type"));
			System.out.println("Selected address id: " + selectedAddressId);

			// If the address is external, get the address details from the form
			if (selectedAddressId != null) {

				// ID 3 is external address
				if (selectedAddressId == 3) {
					String address = request.getParameter("external_address");
					String unit = request.getParameter("external_unit");
					String postal = request.getParameter("external_postal");

					selectedAddress.setAddress(address);
					selectedAddress.setUnit(unit);
					selectedAddress.setPostalCode(Integer.parseInt(postal));
				}

				// Get the selected address from the database if home or office is selected.
				else {
					AddressDAO addDB = new AddressDAO();
					selectedAddress = addDB.getAddressByUserId(userId, selectedAddressId);
				}

				session.setAttribute("selectedAddress", selectedAddress);
				session.setAttribute("selectedAddressId", selectedAddressId);
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
			e.printStackTrace();
			request.setAttribute("err", "Please log in to continue booking.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (NumberFormatException e) {
			System.out.println("Invalid address type: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("err", "Invalid address type. Please try again.");
			response.sendRedirect(request.getContextPath() + "/book/address");
		} catch (Exception e) {
			System.out.println("Error handling address: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("err", "Unable to load address information. Please try again.");
			request.getRequestDispatcher("/error/500").forward(request, response);
		}
	}

	private void handleReview(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			// Check session exists for user
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			LocalDate selectedDate = (LocalDate) session.getAttribute("selectedDate");
			LocalTime selectedTime = (LocalTime) session.getAttribute("selectedTime");
			Address selectedAddress = (Address) session.getAttribute("selectedAddress");
			Integer selectedAddressId = (Integer) session.getAttribute("selectedAddressId");

			// Basic validation for required booking information
			if (selectedDate == null || selectedTime == null || selectedAddressId == null) {
				throw new NullPointerException("Required booking information missing");
			}

			ArrayList<Service> selectedServices = new ArrayList<Service>();
			Bundle selectedBundle = null;

			// Get selected bundle and services
			String strBundleId = request.getParameter("selected_bundle");
			String[] serviceIds = request.getParameterValues("selected_services");

			// Validate that at least one option is selected
			if ((strBundleId == null || strBundleId.equals("0")) && (serviceIds == null || serviceIds.length == 0)) {
				throw new IllegalArgumentException("Please select at least one service or bundle");
			}

			System.out.println("Selected bundle ID: " + strBundleId);

			// Handle bundle selection if exists
			if (strBundleId != null && !strBundleId.trim().isEmpty() && !strBundleId.equals("0")) {
				try {
					BundleDAO bundleDB = new BundleDAO();
					selectedBundle = bundleDB.getBundleById(Integer.parseInt(strBundleId));
					if (selectedBundle != null) {
						System.out.println("Selected bundle: " + selectedBundle.getBundleName());
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid bundle ID: " + strBundleId);
				}
			}

			// Handle individual services if selected
			if (serviceIds != null && serviceIds.length > 0) {
				ServiceDAO serviceDB = new ServiceDAO();
				for (String serviceId : serviceIds) {
					try {
						Service service = serviceDB.getServiceById(Integer.parseInt(serviceId));
						if (service != null) {
							System.out.println("Selected service: " + service.getServiceName());
							selectedServices.add(service);
						}
					} catch (NumberFormatException e) {
						System.out.println("Invalid service ID: " + serviceId);
					}
				}
			}

			// Calculate total price
			double totalPrice = 0.0;

			// Add bundle price if selected
			if (selectedBundle != null) {
				totalPrice += selectedBundle.getDiscountedPrice();
			}

			// Add individual services price
			for (Service service : selectedServices) {
				totalPrice += service.getPrice();
			}

			// Set all attributes for the review page
			request.setAttribute("selectedBundle", selectedBundle);
			request.setAttribute("selectedServices", selectedServices);
			request.setAttribute("selectedDate", selectedDate);
			request.setAttribute("selectedTime", selectedTime);
			request.setAttribute("selectedAddress", selectedAddress);
			request.setAttribute("totalPrice", totalPrice);

			request.getRequestDispatcher("/user/bookReview.jsp").forward(request, response);

		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			request.setAttribute("err", "Please log in to continue booking.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (IllegalArgumentException e) {
			System.out.println("No services selected: " + e.getMessage());
			request.setAttribute("err", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/book/services");
		} catch (NullPointerException e) {
			System.out.println("Incomplete booking data: " + e.getMessage());
			request.setAttribute("err", "Please complete all booking steps.");
			response.sendRedirect(request.getContextPath() + "/book/slots");
		} catch (Exception e) {
			System.out.println("Error handling confirmation: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("err", "Unable to process booking confirmation. Please try again.");
			request.getRequestDispatcher("/error/500").forward(request, response);
		}
	}

	private void handleBooking(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		// try {
		// Address selectedAddress = new Address();
		// TimeSlotDAO timeSlotDB = new TimeSlotDAO();
		// BundleDAO bundleDB = new BundleDAO();
		// ServiceDAO serviceDB = new ServiceDAO();

		// // Check session exists for user
		// Integer userId = (Integer) session.getAttribute("userId");
		// if (userId == null) {
		// throw new IllegalStateException("User not logged in");
		// }

		// Integer buttonPressed = Integer.parseInt(request.getParameter("btnSubmit"));
		// String status = "PENDING";
		// LocalDateTime paidAt = null;

		// // Set status and paidAt based on button press
		// if (buttonPressed == 2) {
		// status = "IN_PROGRESS";
		// paidAt = LocalDateTime.now();
		// }

		// selectedAddress = (Address) session.getAttribute("selectedAddress");

		// LocalDate selectedDate = (LocalDate) session.getAttribute("selectedDate");
		// Integer selectedSlotId = timeSlotDB.getTimeSlotByTime((LocalTime)
		// session.getAttribute("selectedTime"))
		// .getId();

		// // Handle services and bundles
		// String bundleIdStr = request.getParameter("bundleId");
		// String[] serviceIds = request.getParameterValues("selectedServices");

		// Double subtotal = 0.0;
		// String bundleName = null;
		// String bundleImg = null;
		// Integer discountPercent = 0;
		// String servicesJson = null;
		// String bundleServicesJson = null;

		// // Handle bundle selection
		// if (bundleIdStr != null && !bundleIdStr.isEmpty() &&
		// !bundleIdStr.equals("0")) {
		// Integer bundleId = Integer.parseInt(bundleIdStr);
		// Bundle bundle = bundleDB.getBundleById(bundleId);

		// if (bundle != null) {
		// bundleName = bundle.getBundleName();
		// bundleImg = bundle.getImageUrl();
		// discountPercent = bundle.getDiscountPercent();

		// // Calculate total price of services in bundle
		// double originalPrice = 0.0;
		// JsonArrayBuilder bundleServiceArray = Json.createArrayBuilder();

		// for (Service service : bundle.getServices()) {

		// System.out.println("Service Image for Review: " + service.getImageUrl());
		// JsonObjectBuilder serviceObj = Json.createObjectBuilder()
		// .add("service", service.getServiceName()).add("price", service.getPrice())
		// .add("img_url", service.getImageUrl());
		// bundleServiceArray.add(serviceObj);
		// originalPrice += service.getPrice();
		// }

		// // Calculate discounted price
		// subtotal = originalPrice * (1 - (discountPercent / 100.0));
		// bundleServicesJson = bundleServiceArray.build().toString();
		// }
		// }
		// // Handle individual services
		// if (serviceIds != null && serviceIds.length > 0) {
		// JsonArrayBuilder servicesArray = Json.createArrayBuilder();

		// for (String serviceId : serviceIds) {

		// Service service = serviceDB.getServiceById(Integer.parseInt(serviceId));
		// if (service != null) {

		// System.out.println("Service Image for Review: " + service.getImageUrl());
		// JsonObjectBuilder serviceObj = Json.createObjectBuilder()
		// .add("service", service.getServiceName()).add("price", service.getPrice())
		// .add("img_url", service.getImageUrl());
		// servicesArray.add(serviceObj);
		// subtotal += service.getPrice();
		// }
		// }
		// servicesJson = servicesArray.build().toString();
		// }

		// // Insert transaction into database
		// TransactionDAO transactionDB = new TransactionDAO();
		// transactionDB.insertTransaction(userId, selectedAddress.getAddress(),
		// selectedAddress.getPostalCode(),
		// selectedAddress.getUnit(), selectedSlotId, selectedDate, servicesJson,
		// bundleName, bundleImg,
		// bundleServicesJson, discountPercent, status, subtotal, paidAt);

		// // Redirect to user dashboard
		// response.sendRedirect(request.getContextPath() + "/profile");

		// } catch (IllegalStateException e) {
		// System.out.println("Session expired: " + e.getMessage());
		// e.printStackTrace();
		// request.setAttribute("err", "Please log in to continue booking.");
		// response.sendRedirect(request.getContextPath() + "/login");
		// } catch (NullPointerException e) {
		// System.out.println("Invalid Data: " + e.getMessage());
		// e.printStackTrace();
		// request.setAttribute("err", "Please try again booking.");
		// response.sendRedirect(request.getContextPath() + "/slots");
		// } catch (Exception e) {
		// System.out.println("Error handling booking: " + e.getMessage());
		// e.printStackTrace();
		// request.setAttribute("err", "Unable to process booking confirmation. Please
		// try again.");
		// request.getRequestDispatcher("/error/500").forward(request, response);
		// }

		try {
			Stripe.apiKey = SecretsConfig.getStripeApiKey();

			// Build the complete URLs
			String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			String successUrl = baseUrl + request.getContextPath() + "/profile";
			String cancelUrl = baseUrl + request.getContextPath() + "/book";

			SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
					.setSuccessUrl(successUrl) // Complete URL like "http://localhost:8080/yourapp/profile"
					.setCancelUrl(cancelUrl) // Complete URL like "http://localhost:8080/yourapp/book"
					.addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L)
							.setPrice("price_1QZ4muRpRtGdj3Co0gvNeWrq").build())
					.addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L)
							.setPrice("price_1QZ4muRpRtGdj3Co0gvNeWrq").build())
					.build();

			Session stripeSession = Session.create(params);
			System.out.println(stripeSession.getUrl());
			response.sendRedirect(stripeSession.getUrl());

		} catch (StripeException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Payment setup failed");
		}
	}

	private void handleView(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
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

			request.getRequestDispatcher("/user/bookView.jsp").forward(request, response);

		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("err", "Please log in to continue.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", "Unable to load the booking history");
			response.sendRedirect(request.getContextPath() + "/error");
		}
	}

	private void handleCancel(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			// Check if user is logged in
			// Check session exists for user
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			Integer transId = Integer.parseInt(request.getParameter("id"));
			String status = "CANCELLED";
			TransactionDAO transDAO = new TransactionDAO();

			int rowsAffected = transDAO.updateTransactionStatus(transId, status);
			if (rowsAffected < 0) {
				request.setAttribute("err", "Error Cancelling Booking");
			}

			response.sendRedirect(request.getContextPath() + "/book/view");

		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("err", "Please log in to continue.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/error");
		}
	}

	private void handleGetPayment(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			// Check if user is logged in
			// Check session exists for user
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			Integer transId = Integer.parseInt(request.getParameter("id"));

			System.out.println("Transaction ID: " + transId);
			TransactionDAO transDAO = new TransactionDAO();

			Transaction transaction = transDAO.getTransactionById(transId);

			request.setAttribute("transaction", transaction);
			request.getRequestDispatcher("/user/payment.jsp").forward(request, response);
		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("err", "Please log in to continue.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/error");
		}
	}

	private void handlePostPayment(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		try {
			// Check if user is logged in
			// Check session exists for user
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			Integer transId = Integer.parseInt(request.getParameter("transactionId"));

			System.out.println("Transaction ID: " + transId);
			String status = "IN_PROGRESS";
			TransactionDAO transDAO = new TransactionDAO();

			int rowsAffected = transDAO.updateTransactionStatus(transId, status);
			if (rowsAffected < 0) {
				request.setAttribute("err", "Error Making Payment");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}

			response.sendRedirect(request.getContextPath() + "/profile");

		} catch (IllegalStateException e) {
			System.out.println("Session expired: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("err", "Please log in to continue.");
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/error");
		}
	}
}
