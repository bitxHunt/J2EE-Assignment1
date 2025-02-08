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
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/admin/manage-bookings")
public class ManageBookings extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BookingDetailsDAO bookingDetailsDAO;
    
    public ManageBookings() {
        super();
        this.bookingDetailsDAO = new BookingDetailsDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ArrayList<BookingDetails> bookings = bookingDetailsDAO.getAllBookingsWithAssignment();
            request.setAttribute("bookings", bookings);
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
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            int staffId = Integer.parseInt(request.getParameter("staffId"));
            int slotId = Integer.parseInt(request.getParameter("slotId"));
            
            boolean success = bookingDetailsDAO.assignStaffToBooking(bookingId, staffId, slotId);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/manage-bookings");
            } else {
                throw new SQLException("Failed to assign staff");
            }
        } catch (SQLException e) {
            request.setAttribute("err", "Database error: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/error/500");
            rd.forward(request, response);
        }
    }
}