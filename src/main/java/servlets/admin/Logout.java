/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 ************************************************************/
package servlets.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/log-out")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get the current session
		HttpSession session = request.getSession(false);

		// Check if session exists
		if (session != null) {
			// Invalidate the session
			session.invalidate();
		}

		// Redirect to login page 
		response.sendRedirect(request.getContextPath() + "/login");
	}
}