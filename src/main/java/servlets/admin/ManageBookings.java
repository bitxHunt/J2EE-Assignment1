/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 ************************************************************/
package servlets.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.BookingDetails.BookingDetails;
import models.BookingDetails.BookingDetailsDAO;
import models.booking.*;
import models.user.User;
import models.user.UserDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet("/admin/manage-bookings")
public class ManageBookings extends HttpServlet {
	private static final int PAGE_SIZE = 10;
	private BookingDetailsDAO bookingDetailsDAO;
	private UserDAO userDAO; // For staff list

	public ManageBookings() {
		super();
		this.bookingDetailsDAO = new BookingDetailsDAO();
		this.userDAO = new UserDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String action = request.getParameter("action");

			if ("cancel".equals(action)) {
				String bookingIdStr = request.getParameter("bookingId");
				if (bookingIdStr != null && !bookingIdStr.trim().isEmpty()) {
					int bookingId = Integer.parseInt(bookingIdStr);
					boolean success = bookingDetailsDAO.cancelBooking(bookingId);
					if (success) {
						response.sendRedirect(request.getContextPath() + "/admin/manage-bookings?success=true");
						return;
					}
				}
			}

			// Get filter parameters
			String postalCodeStr = request.getParameter("postalCode");
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");

			Integer postalCode = null;
			LocalDate startDate = null;
			LocalDate endDate = null;

			// Parse postal code if provided
			if (postalCodeStr != null && !postalCodeStr.trim().isEmpty()) {
				try {
					postalCode = Integer.parseInt(postalCodeStr.trim());
					if (postalCode < 0 || postalCode > 999999) {
						request.setAttribute("error", "Invalid postal code range");
						postalCode = null;
					}
				} catch (NumberFormatException e) {
					request.setAttribute("error", "Invalid postal code format");
				}
			}

			// Parse dates if provided
			if (startDateStr != null && !startDateStr.trim().isEmpty() && endDateStr != null
					&& !endDateStr.trim().isEmpty()) {
				try {
					startDate = LocalDate.parse(startDateStr);
					endDate = LocalDate.parse(endDateStr);

					// Validate date range
					if (endDate.isBefore(startDate)) {
						request.setAttribute("error", "End date cannot be before start date");
						startDate = null;
						endDate = null;
					}
				} catch (Exception e) {
					request.setAttribute("error", "Invalid date format");
				}
			}

			// Get page parameter, default to 1 if not present
			int page = 1;
			String pageParam = request.getParameter("page");
			if (pageParam != null && !pageParam.trim().isEmpty()) {
				try {
					page = Integer.parseInt(pageParam);
					if (page < 1)
						page = 1;
				} catch (NumberFormatException e) {
					// Invalid page parameter, default to 1
					page = 1;
				}
			}

			// Get bookings for current page with filters
			ArrayList<BookingDetails> bookings = bookingDetailsDAO.getAllBookings(page, postalCode, startDate, endDate);

			// Get total bookings for pagination with filters
			int totalBookings = bookingDetailsDAO.getTotalBookings(postalCode, startDate, endDate);
			int totalPages = (int) Math.ceil((double) totalBookings / PAGE_SIZE);

			// Check if requested page exists
			if (page > totalPages && totalPages > 0) {
				response.sendRedirect(request.getContextPath() + "/error/404ErrorPage.jsp");
				return;
			}

			// Get staff list for modal
			ArrayList<User> staffList = userDAO.getStaffMembers();

			// Store filter values in request for form persistence
			request.setAttribute("postalCode", postalCodeStr);
			request.setAttribute("startDate", startDateStr);
			request.setAttribute("endDate", endDateStr);

			// Set attributes for JSP
			request.setAttribute("bookings", bookings);
			request.setAttribute("currentPage", page);
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("staffList", staffList);

			RequestDispatcher rd = request.getRequestDispatcher("/admin/manageBookings.jsp");
			rd.forward(request, response);

		} catch (SQLException e) {
			request.setAttribute("err", "Database error: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/error/500");
			rd.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String bookingIdStr = request.getParameter("bookingId");
			String staffIdStr = request.getParameter("staffId");

			// Validate both parameters exist
			if (bookingIdStr == null || staffIdStr == null || bookingIdStr.trim().isEmpty()
					|| staffIdStr.trim().isEmpty()) {
				throw new IllegalArgumentException("Please select both booking and staff");
			}

			System.out.println("booking id str is " + bookingIdStr);
			System.out.println("booking id str is " + staffIdStr);

			// Parse values
			int bookingId = Integer.parseInt(bookingIdStr.trim());
			int staffId = Integer.parseInt(staffIdStr.trim());
			System.out.println("booking id is " + bookingId);
			System.out.println("booking id is " + staffId);

			// Validate values
			if (bookingId <= 0 || staffId <= 0) {
				throw new IllegalArgumentException("Invalid booking or staff selection");
			}

			boolean success = bookingDetailsDAO.assignStaffToBooking(bookingId, staffId);

			if (success) {
				response.sendRedirect(request.getContextPath() + "/admin/manage-bookings?success=true");
			} else {
				throw new SQLException("Failed to assign staff");
			}

		} catch (NumberFormatException e) {
			request.setAttribute("error", "Invalid number format");
			doGet(request, response);
		} catch (IllegalArgumentException e) {
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			request.setAttribute("err", "Database error: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/error/500");
			rd.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("err", "Database error: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/error/500");
			rd.forward(request, response);
		}
	}
}