/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 ************************************************************/
package servlets.admin;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/admin/*")
public class AuthenticationFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// No initialization required
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);

		try {
			// Check if the user is authenticated and has the correct role
			if (session != null && session.getAttribute("userId") != null && session.getAttribute("role") != null) {
				int userId = (int) session.getAttribute("userId");
				int userRole = (int) session.getAttribute("role");

				if (userRole == 1) {
					// User is authenticated and has the admin role, allow access to the admin pages
					chain.doFilter(request, response);
				} else {
					// User is authenticated but does not have the admin role, redirect to the user
					// page
					httpResponse.sendRedirect(httpRequest.getContextPath() + "/profile");
				}
			} else {
				// User is not authenticated, redirect to the login page
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
			}
		} catch (NumberFormatException e) {
			// Handle the case where the session attributes are not in the expected format
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
		} catch (Exception e) {
			// Handle any other unexpected exceptions
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"An error occurred. Please try again later.");
		}
	}

	@Override
	public void destroy() {
		// No cleanup required
	}
}